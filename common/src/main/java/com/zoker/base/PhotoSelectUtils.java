package com.zoker.base;

import android.Manifest;
import android.annotation.TargetApi;
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
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.zekers.pjutils.BaseApplication;
import com.zekers.utils.CommonUtils;
import com.zekers.utils.cache.FileUtils;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.permission.PermissionCallback;
import com.zekers.utils.permission.PermissionManager;

import java.io.File;

import cn.finalteam.galleryfinal.GalleryFinal;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * 图片选择dialog
 * Created by Administrator on 2017/3/6.
 */

public class PhotoSelectUtils {
    public static final int TAKE_A_PICTURE = 10;  //拍照
    public static final int SET_PICTURE = 30;     //拍照的裁剪相片
    public static final int SELECT_A_PICTURE = 20;//4.4 以下相册选择并裁剪
    public static final int SELECET_A_PICTURE_AFTER_KIKAT = 50;// 4.4 以上相册选择回调
    public static final int SET_ALBUM_PICTURE_KITKAT = 40;  //4.4 以上相册裁剪

    private static final String CamPicName = "tempImage.jpg";
    //版本比较：是否是4.4及以上版本
    final static boolean mIsKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    private static File getCamPicPath(Context context) {
        return Environment.getExternalStorageDirectory();
    }

    public static void showPhotoSelect(final String title, final Activity activity, final int aspectX, final int aspectY, final GalleryFinal.OnHanlderResultCallback callback) {
        Logger.d("PhotoSelectUtils", "#showPhotoSelect");
        if (CommonUtils.isMarshmallow()) {
            checkPermisstionAndrThenLoad(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    showPhotoSelect(title, activity, aspectX, aspectY, true,callback);
                }

                @Override
                public void permissionRefused() {
                    BaseApplication.Instance.showToast("无权限读取相册信息或照相");
                }
            });
        } else {
            showPhotoSelect(title, activity, aspectX, aspectY, true,callback);
        }
    }

    //20040421
    public static void showPhotoSelect(String title, final Activity activity, final int aspectX, final int aspectY, boolean open, final GalleryFinal.OnHanlderResultCallback callback) {
        final CharSequence[] items = {"从手机相册中选取", "拍摄照片"};
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("从手机相册中选取")) {
//                            if (mIsKitKat) {
//                                selectImageUriAfterKikat(activity);
//                            } else {
//                                cropImageUri(activity, aspectX, aspectY, 640, 640);
//                            }
                            selectPicture(callback);
                        } else if (items[which].equals("拍摄照片")) {

                            if (CommonUtils.isMarshmallow()) {
                                checkPermisstionAndrThenLoad(activity, Manifest.permission.CAMERA, new PermissionCallback() {
                                    @Override
                                    public void permissionGranted() {
                                        takePicture(activity,callback);
                                    }

                                    @Override
                                    public void permissionRefused() {
                                        BaseApplication.Instance.showToast("无权限照相");
                                    }
                                });
                            } else {
                                takePicture(activity,callback);
                            }
                        }
                    }
                })
                .create().show();
    }

    public static void takePicture(Activity activity,GalleryFinal.OnHanlderResultCallback callback){
        GalleryFinal.openCamera(TAKE_A_PICTURE,callback);
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                Uri.fromFile(new File(getCamPicPath(activity), CamPicName)));
//        activity.startActivityForResult(intent, TAKE_A_PICTURE);
    }
    public static void selectPicture(GalleryFinal.OnHanlderResultCallback callback){
        GalleryFinal.openGallerySingle(SELECT_A_PICTURE, callback);
    }

    public static void showPhotoSelect(final String title, final Activity activity , final GalleryFinal.OnHanlderResultCallback callback) {
        if (CommonUtils.isMarshmallow()) {
            checkPermisstionAndrThenLoad(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    showPhotoSelect(title, activity, 1, 1,callback);
                }

                @Override
                public void permissionRefused() {
                    BaseApplication.Instance.showToast("无权限读取相册信息或照相");
                }
            });
        } else {
            showPhotoSelect(title, activity, 1, 1,callback);
        }
    }

    public static File onResultWithoutCrop(Activity activity, int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case TAKE_A_PICTURE://拍照
                    File file=new File(getCamPicPath(activity), CamPicName);
                    Logger.d("picture",""+file.exists()+"dir:"+file.getAbsolutePath());
                    return file;
                case SET_PICTURE:   //拍照的裁剪相片
                    File file2=new File(getCamPicPath(activity), CamPicName);
                    Logger.d("picture",""+file2.exists()+"dir:"+file2.getAbsolutePath());
                    return file2;

                case SELECT_A_PICTURE://4.4以下相册选择并裁剪
                    File file4=new File(getCamPicPath(activity), CamPicName);
                    Logger.d("picture",""+file4.exists()+"dir:"+file4.getAbsolutePath());
                    return file4;
                case SELECET_A_PICTURE_AFTER_KIKAT://4.4以上相册选择并裁剪
                    String mAlbumPicturePath = FileUtils.getPath(activity, data.getData());
                    if (mAlbumPicturePath != null){
                        File file5=new File(getCamPicPath(activity), CamPicName);
                        Logger.d("picture",""+file5.exists()+"dir:"+file5.getAbsolutePath());
                        return file5;
                    }
                case SET_ALBUM_PICTURE_KITKAT:
                    File file6=new File(getCamPicPath(activity), CamPicName);
                    Logger.d("picture",""+file6.exists()+"dir:"+file6.getAbsolutePath());
                    return file6;
                default:
                    break;
            }
        }
        return null;
    }

    public static File onResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_A_PICTURE) {//4.4以下相册选择并裁剪
            if (resultCode == RESULT_OK && null != data) {
//              "4.4以下的"
                return new File(getCamPicPath(activity), CamPicName);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(activity, "取消图片选择", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == SELECET_A_PICTURE_AFTER_KIKAT) {//4.4以上相册选择
            if (resultCode == RESULT_OK && null != data) {
                String mAlbumPicturePath = FileUtils.getPath(activity, data.getData());
                if (mAlbumPicturePath != null)
                    cropImageUriAfterKikat(activity, Uri.fromFile(new File(mAlbumPicturePath)));
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(activity, "取消图片选择", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == SET_ALBUM_PICTURE_KITKAT) { //4.4以上相册裁剪
            return new File(getCamPicPath(activity), CamPicName);

        } else if (requestCode == TAKE_A_PICTURE) { //拍一张照片
            if (resultCode == RESULT_OK) {
                cameraCropImageUri(activity, Uri.fromFile(new File(getCamPicPath(activity), CamPicName)));
            } else {
                Toast.makeText(activity, "取消图片选择", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == SET_PICTURE) { //裁剪一张照片
            //拍照的设置头像  不考虑版本
            if (resultCode == RESULT_OK && null != data) {
                return new File(getCamPicPath(activity), CamPicName);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(activity, "取消图片选择", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "图片选择失败", Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    /**
     * <br>功能简述:裁剪图片方法实现---------------------- 相册
     * <br>功能详细描述:
     * <br>注意:
     */
    private static void cropImageUri(Activity activity, int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(getCamPicPath(activity), CamPicName)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, SELECT_A_PICTURE);
    }


    /**
     * <br>功能简述:4.4以上裁剪图片方法实现---------------------- 相册
     * <br>功能详细描述: 选择图片
     * <br>注意:
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void selectImageUriAfterKikat(Activity context) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        context.startActivityForResult(intent, SELECET_A_PICTURE_AFTER_KIKAT);
    }

    /**
     * <br>功能简述:裁剪图片方法实现----------------------相机
     * <br>功能详细描述:
     * <br>注意:
     *
     * @param uri
     */
    private static void cameraCropImageUri(Activity activity, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 640);
        intent.putExtra("outputY", 640);
        intent.putExtra("scale", true);
        //		if (mIsKitKat) {
        //			intent.putExtra("return-data", true);
        //			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //		} else {
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //		}
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, SET_PICTURE);
    }

    /**
     * <br>功能简述: 4.4及以上改动版裁剪图片方法实现 --------------------相机
     * <br>功能详细描述:
     * <br>注意:
     *
     * @param uri
     */
    private static void cropImageUriAfterKikat(Activity activity, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 640);
        intent.putExtra("outputY", 640);
        intent.putExtra("scale", true);
        //		intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(getCamPicPath(activity), CamPicName)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, SET_ALBUM_PICTURE_KITKAT);
    }


    protected static void checkPermisstionAndrThenLoad(Activity activity, final String permission, final PermissionCallback PermisstionCallBack) {
        Logger.d("PhotoSelectUtils", "#checkPermisstionAndrThenLoad");
        if (PermissionManager.checkPermission(permission)) {
            Logger.d("permissionManager", "#checkPermisstionAndrThenLoad");
            PermisstionCallBack.permissionGranted();
        } else {
            if (PermissionManager.shouldShowRequestPermissionRationale(activity, permission)) {
                Logger.d("permissionManager", "#shouldShowRequestPermissionRationale");
                PermissionManager.askForPermission(activity, permission, PermisstionCallBack);
            } else {
                PermissionManager.askForPermission(activity, permission, PermisstionCallBack);
            }
        }
    }
}
