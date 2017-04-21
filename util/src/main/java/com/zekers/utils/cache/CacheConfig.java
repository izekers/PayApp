package com.zekers.utils.cache;

import android.content.Context;
import android.os.Environment;

import com.zekers.pjutils.BaseApplication;

/**
 * 缓存地址工具类
 * Created by Administrator on 2016/9/20.
 */
public class CacheConfig {
    public static String ENVIROMENT_DIR_CACHE;      //暂缓地址
    public static String ENVIROMENT_DIR_FILE;       //文件缓存地址
    public static String ENVIROMENT_DIR_PICTURE;    //图片缓存地址
    public static String DEFAULT_DIR = "/default";    //默认缓存地址

    static {
        initDirPath(BaseApplication.Instance);
    }

    //初始化配置地址
    public static void initDirPath(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {
            ENVIROMENT_DIR_FILE = Environment.getExternalStorageDirectory().getPath();
            ENVIROMENT_DIR_CACHE = context.getExternalCacheDir().getPath();
        } else {
            ENVIROMENT_DIR_FILE = context.getFilesDir().getPath();
            ENVIROMENT_DIR_FILE = context.getCacheDir().getPath();
        }
        ENVIROMENT_DIR_PICTURE = ENVIROMENT_DIR_FILE + "/PICTURE";
    }
}
