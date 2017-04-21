package com.zekers.utils.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.zekers.utils.encryption.MD5Util;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.network.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * 文件缓存工具
 * Created by zekers on 2016/9/20.
 */
public class CacheManager {
    private static final String TAG = CacheManager.class.getName();

    /**
     * 1秒超时时间
     */
    public static final int CONFIG_CACHE_SHORT_TIMEOUT = 1000 * 60 * 5; // 5 分钟

    /**
     * 5分钟超时时间
     */
    public static final int CONFIG_CACHE_MEDIUM_TIMEOUT = 1000 * 3600 * 2; // 2小时

    /**
     * 中长缓存时间
     */
    public static final int CONFIG_CACHE_ML_TIMEOUT = 1000 * 60 * 60 * 24 * 1; // 1天

    /**
     * 最大缓存时间
     */
    public static final int CONFIG_CACHE_MAX_TIMEOUT = 1000 * 60 * 60 * 24 * 7; // 7天

    public static final int CONFIG_CACHE_TEST_TIMEOUT = 1000 * 30; // 0.5分钟
    /**
     * CONFIG_CACHE_MODEL_LONG : 长时间(7天)缓存模式 <br>
     * CONFIG_CACHE_MODEL_ML : 中长时间(12小时)缓存模式<br>
     * CONFIG_CACHE_MODEL_MEDIUM: 中等时间(2小时)缓存模式 <br>
     * CONFIG_CACHE_MODEL_SHORT : 短时间(5分钟)缓存模式
     */
    public enum CacheModel {
        CONFIG_CACHE_MODEL_SHORT, CONFIG_CACHE_MODEL_MEDIUM, CONFIG_CACHE_MODEL_ML, CONFIG_CACHE_MODEL_LONG, CONFIG_CACHE_TEST
    }

    //缓存策略
    public interface CacheStrategy {
        boolean isCache(String path);
    }

    /**
     * 获取缓存
     * @return 缓存数据
     * 策略：1.当时间不正确或超过缓存时间，读取缓存
     * 2.当没有网络时，读取缓存
     */
    public static String load(Context context,String path, CacheModel model) {
        Logger.d(TAG, "#load path=" + path);
        String result = null;
        File file = new File(CacheConfig.ENVIROMENT_DIR_CACHE +getDefaultDir()+ path);
        if (file.exists() && file.isFile()) {
            long expiredTime = System.currentTimeMillis() - file.lastModified();
            Logger.d(TAG, file.getAbsolutePath() + " expiredTime:" + expiredTime / 60000 + "min");
            // 1。如果系统时间是不正确的
            // 2。当网络是无效的,你只能读缓存
            if (NetworkUtil.getNetworkState(context) != NetworkUtil.NetworkState.UNABLE) {
                if (expiredTime < 0) {
                    return null;
                }
                if (model == CacheModel.CONFIG_CACHE_TEST){
                    if (expiredTime > CONFIG_CACHE_TEST_TIMEOUT) {
                        return null;
                    }
                } else if (model == CacheModel.CONFIG_CACHE_MODEL_SHORT) {
                    if (expiredTime > CONFIG_CACHE_SHORT_TIMEOUT) {
                        return null;
                    }
                } else if (model == CacheModel.CONFIG_CACHE_MODEL_MEDIUM) {
                    if (expiredTime > CONFIG_CACHE_MEDIUM_TIMEOUT) {
                        return null;
                    }
                } else if (model == CacheModel.CONFIG_CACHE_MODEL_ML) {
                    if (expiredTime > CONFIG_CACHE_ML_TIMEOUT) {
                        return null;
                    }
                } else if (model == CacheModel.CONFIG_CACHE_MODEL_LONG) {
                    if (expiredTime > CONFIG_CACHE_MEDIUM_TIMEOUT) {
                        return null;
                    }
                } else {
                    if (expiredTime > CONFIG_CACHE_MAX_TIMEOUT) {
                        return null;
                    }
                }
            }

            try {
                result = FileUtils.readTextFile(file);
                Logger.i(TAG, "#load result=" + result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取缓存，自定义缓存策略
     * @param path          缓存地址
     * @param cacheStrategy 缓存策略
     * @return JSON数据
     */
    public static String load(Context context,String path, CacheStrategy cacheStrategy) {
        Logger.d(TAG, "#load path=" + path);
        String result = null;
        File file = new File(CacheConfig.ENVIROMENT_DIR_CACHE +getDefaultDir()+ path);
        if (file.exists() && file.isFile()){
            if (cacheStrategy!=null && cacheStrategy.isCache(path)) {
                try {
                    result = FileUtils.readTextFile(file);
                    Log.i(TAG, "#load result=" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    /**
     * 获取url缓存，自定义缓存策略
     * @param url           url链接
     * @param cacheStrategy 缓存策略
     * @return JSON数据
     */
    public static String urlLoad(String url ,Context context,CacheStrategy cacheStrategy){
        return  load(context,uriPath(url),cacheStrategy);
    }


    /**
     * 写入缓存
     */
    public static void save(String path,Object o){
        String data = new Gson().toJson(o);
        save(path,data);
    }

    /**
     * 写入缓存
     * @param data 策略：只缓存在外部sd中
     */
    public static void save(String path, String data) {
        Logger.d(TAG, "#save path=" + path + ",data=" + data);
        if (CacheConfig.ENVIROMENT_DIR_CACHE == null) {
            return;
        }
        File dir = new File(CacheConfig.ENVIROMENT_DIR_CACHE + getDefaultDir());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(CacheConfig.ENVIROMENT_DIR_CACHE + getDefaultDir() + path);
        try {
            // 创建缓存数据到磁盘，就是创建文件
            FileUtils.writeTextFile(file, data);
        } catch (IOException e) {
            Logger.d(TAG, "write " + file.getAbsolutePath() + " data failed!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立MD5地址
     * @param url
     */
    public static String uriPath(String url) {
        return "/" + MD5Util.strToMd5(url);
    }

    /**
     * 设置URL缓存
     * @param data        数据
     * @param url         访问链接
     * @param isOnlyExter 是否仅当有SD卡时缓存
     */
    public static void setUrlCache(String data, String url, boolean isOnlyExter) {
        if (CacheConfig.ENVIROMENT_DIR_CACHE == null) {
            return;
        }
        File dir = new File(CacheConfig.ENVIROMENT_DIR_CACHE);
        //如果文件夹不存在同时外部SD卡存在
        if (!dir.exists()) {
            if (isOnlyExter && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                dir.mkdirs();
            } else if (!isOnlyExter) {
                dir.mkdirs();
            }
        }
        //添加上md5
        File file = new File(CacheConfig.ENVIROMENT_DIR_CACHE + uriPath(url));
        try {
            // 创建缓存数据到磁盘，就是创建文件
            FileUtils.writeTextFile(file, data);
        } catch (IOException e) {
            Logger.i(TAG, "write " + file.getAbsolutePath() + " data failed!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除历史缓存文件直接通过File
     *
     * @param cacheFile
     */
    public static void clearCache(File cacheFile) {
        if (cacheFile == null) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                try {
                    //删除默认路径
                    File cacheDir = new File(Environment.getExternalStorageDirectory().getPath() + CacheConfig.DEFAULT_DIR);
                    if (cacheDir.exists()) {
                        clearCache(cacheDir);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Logger.i(TAG, "#clearCache cacheFile=" + cacheFile.getAbsolutePath());
            if (cacheFile.isFile()) {
                cacheFile.delete();
            } else if (cacheFile.isDirectory()) {
                File[] childFiles = cacheFile.listFiles();
                for (int i = 0; i < childFiles.length; i++) {
                    clearCache(childFiles[i]);
                }
            }
        }
    }

    /**
     * 删除历史缓存文件通过地址
     */
    public static void clearCache(String cachePath) {
        Logger.i(TAG, "#clear path=" + cachePath);
        File cacheFile = new File(CacheConfig.ENVIROMENT_DIR_CACHE + cachePath);
        if (cacheFile.isFile()) {
            cacheFile.delete();
        } else if (cacheFile.isDirectory()) {
            File[] childFiles = cacheFile.listFiles();
            for (int i = 0; i < childFiles.length; i++) {
                clearCache(childFiles[i]);
            }
        }
    }

    public static String getDefaultDir(){
        return CacheConfig.DEFAULT_DIR;
    }
}
