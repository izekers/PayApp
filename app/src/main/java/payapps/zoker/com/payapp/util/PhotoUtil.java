package payapps.zoker.com.payapp.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.fragment.ResultCode;
import com.zekers.pjutils.BaseApplication;
import com.zekers.utils.CommonUtils;
import com.zekers.utils.permission.PermissionCallback;

import java.util.ArrayList;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.util.imageLoader.GlideImageLoader;

/**
 * Created by Administrator on 2017/4/21.
 */

public class PhotoUtil {
    public static void init(Context context) {
        Album.initialize(new AlbumConfig.Build()
                .setImageLoader(new GlideImageLoader()) // Use glide loader.
                .build());
    }

    private Activity activity;

    private PhotoUtil(Activity activity) {
        this.activity = activity;
    }

    public static PhotoUtil with(Activity activity) {
        return new PhotoUtil(activity);
    }

    public void showWindows(final int requestCode, String title) {
        final CharSequence[] items = {"从手机相册中选取", "拍摄照片"};
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("从手机相册中选取")) {
                            selectSinglePhoto(requestCode);
                        } else if (items[which].equals("拍摄照片")) {
                            takePic(requestCode);
                        }
                    }
                })
                .create().show();
    }

    //拍照
    public void takePic(int requestCode) {
        Album.camera(activity)
                .requestCode(requestCode)
                // .imagePath() // 指定相机拍照的路径，建议非特殊情况不要指定.
                .start();
    }

    //选择一张图片
    public void selectSinglePhoto(int requestCode) {
        Album.album(activity)
                .requestCode(requestCode) // 请求码，返回时onActivityResult()的第一个参数。
                .toolBarColor(activity.getResources().getColor(R.color.main)) // Toolbar 颜色，默认蓝色。
                .statusBarColor(activity.getResources().getColor(R.color.main)) // StatusBar 颜色，默认蓝色。
//                .navigationBarColor(navigationBarColor) // NavigationBar 颜色，默认黑色，建议使用默认。
                .title("相册") // 配置title。
                .selectCount(1)
//                .selectCount(9) // 最多选择几张图片。
                .columnCount(4) // 相册展示列数，默认是2列。
                .camera(true) // 是否有拍照功能。
//                .checkedList(mImageList) // 已经选择过得图片，相册会自动选中选过的图片，并计数。
                .start();
    }

    //选择多张图片
    public void selectSinglePhoto(int requestCode, int number) {
        Album.album(activity)
                .requestCode(requestCode) // 请求码，返回时onActivityResult()的第一个参数。
                .toolBarColor(activity.getResources().getColor(R.color.main)) // Toolbar 颜色，默认蓝色。
                .statusBarColor(activity.getResources().getColor(R.color.main)) // StatusBar 颜色，默认蓝色。
//                .navigationBarColor(navigationBarColor) // NavigationBar 颜色，默认黑色，建议使用默认。
                .title("相册") // 配置title。
                .selectCount(number)
//                .selectCount(9) // 最多选择几张图片。
                .columnCount(4) // 相册展示列数，默认是2列。
                .camera(true) // 是否有拍照功能。
//                .checkedList(mImageList) // 已经选择过得图片，相册会自动选中选过的图片，并计数。
                .start();
    }

    //一张图片结果
    public static String onSingleResult(int request, int requestCode, int resultCode, Intent data) {
        if (request == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                // 不要质疑你的眼睛，就是这么简单。
                ArrayList<String> pathList = Album.parseResult(data);
                if (!pathList.isEmpty()) {
                    return pathList.get(0);
                } else
                    return null;
            }
        }
        return null;
    }

    //多张图片
    public static ArrayList<String> onColumnResult(int request, int requestCode, int resultCode, Intent data) {
        if (request == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                // 不要质疑你的眼睛，就是这么简单。
                return Album.parseResult(data);
            }
        }
        return null;
    }
}
