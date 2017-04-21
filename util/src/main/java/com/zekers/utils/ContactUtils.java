package com.zekers.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.ImageFormat;
import android.provider.ContactsContract;

import com.zekers.pjutils.BaseApplication;
import com.zekers.utils.permission.PermissionCallback;
import com.zekers.utils.permission.PermissionManager;

/**
 * Created by Administrator on 2017/3/28.
 */

public class ContactUtils {

//    获取联系人和电话号码
    private void queryContactPhoneNumber(Context context) {
        String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols, null, null, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            // 取得联系人名字
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            int numberFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String name = cursor.getString(nameFieldColumnIndex);
            String number = cursor.getString(numberFieldColumnIndex);
//            Toast.makeText(this, name + " " + number, Toast.LENGTH_SHORT).show();
        }
    }


    public static void startActivityForResult(final Activity activity){
        if (CommonUtils.isMarshmallow()){
            checkPermisstionAndrThenLoad(activity, Manifest.permission.READ_CONTACTS, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    activity.startActivityForResult(new Intent(
                            Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI), 0);
                }

                @Override
                public void permissionRefused() {
                    BaseApplication.Instance.showToast("无权限读取通讯录");
                }
            });
        }else {
            activity.startActivityForResult(new Intent(
                    Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI), 0);
        }
    }

    public static String getPhoneNumber(String phone){
        StringBuilder stringBuilder=new StringBuilder();
        if (phone!=null && phone.length()>0){
            for (int i=0;i<phone.length();i++){
                String tmp=""+phone.charAt(i);
                if (tmp.matches("[0-9]")){
                    stringBuilder.append(tmp);
                }
            }
        }else
            return "";
        return stringBuilder.toString();
    }

    protected static void checkPermisstionAndrThenLoad(Activity activity,final String permission, final PermissionCallback PermisstionCallBack) {
        if (PermissionManager.checkPermission(permission)) {
            if (PermisstionCallBack!=null)
                PermisstionCallBack.permissionGranted();
        } else {
            if (PermissionManager.shouldShowRequestPermissionRationale(activity, permission)) {
                PermissionManager.askForPermission(activity, permission, PermisstionCallBack);
            }else {
                PermissionManager.askForPermission(activity, permission, PermisstionCallBack);
            }
        }
    }
}
