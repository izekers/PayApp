package com.zekers.ui.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/3/6.
 */

public class PhotoUtils {
    private static final int SELECTPIC_CODE = 290;
    private static final int SELECTCAM_CODE = 291;
    private static final int SELECTCROP_CODE = 293;

    private static final String CamPicName = "tempImage.jpg";

    private static File getCamPicPath(Context context) {
        return Environment.getExternalStorageDirectory();
    }

    public static void showPhotoSelect(final Activity context) {
        CharSequence[] items = {"相册", "相机"};
        new AlertDialog.Builder(context)
                .setTitle("选择图片来源")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
//                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                            intent.addCategory(Intent.CATEGORY_OPENABLE);
//                            intent.setType("image/*");
//                            context.startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECTPIC_CODE);
                            startWithPicDou(context);
                        } else {
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            context.startActivityForResult(intent, SELECTCAM_CODE);
                            startWithCam(context);
                        }
                    }
                })
                .create().show();
    }

    private static void startWithCam(Activity context) {
        // new一个File用来存放拍摄到的照片
        // 通过getExternalStorageDirectory方法获得手机系统的外部存储地址
        File imageFile = new File(getCamPicPath(context), CamPicName);
        // 将存储地址转化成uri对象
        Uri imageUri = Uri.fromFile(imageFile);
        Log.d("test","imageUri="+imageUri.toString());
        // 设置打开照相的Intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 设置相片的输出uri为刚才转化的imageUri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        // 开启相机程序，设置requestCode为TOKE_PHOTO
        context.startActivityForResult(intent, SELECTCAM_CODE);
    }
    private static void startWithPicDou(Activity context){
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {//因为Android SDK在4.4版本后图片action变化了 所以在这里先判断一下
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        context.startActivityForResult(intent, SELECTPIC_CODE);
    }
    public static Uri camResult(Activity context, int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 从拍照界面返回
            case SELECTCAM_CODE:
                Log.d("test","这里这里 resultCode="+resultCode);
                if (resultCode == RESULT_OK) {
                    Log.d("test","进到裁剪");
                    File imageFile = new File(getCamPicPath(context), CamPicName);
                    Log.d("test","isexists="+imageFile.exists());
                    Uri imageUri = Uri.fromFile(imageFile);
                    Log.d("test","imageUri="+imageUri.toString());
                    // 设置intent为启动裁剪程序
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    // 设置Data为刚才的imageUri和Type为图片类型
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("crop", "true");
                    intent.putExtra("aspectX", 1);// 裁剪框比例
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("outputX", 300);// 输出图片大小
                    intent.putExtra("outputY", 300);
                    intent.putExtra("return-data", true);
                    // 设置可缩放
                    intent.putExtra("scale", true);
                    // 设置输出地址为imageUri
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    // 开启裁剪,设置requestCode为CROP_PHOTO
                    context.startActivityForResult(intent, SELECTCROP_CODE);
                }
                break;
            // 从裁剪界面返回
            case SELECTCROP_CODE:
                if (resultCode == RESULT_OK) {
                    File imageFile = new File(getCamPicPath(context), CamPicName);
                    Uri imageUri = Uri.fromFile(imageFile);
                    Log.d("test","裁剪返回");
                    Log.d("test","imageUri="+imageUri.toString());
                    return imageUri;
                }
                break;
            case SELECTPIC_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                }
                break;
            default:
                break;
        }
        return null;
    }
}
