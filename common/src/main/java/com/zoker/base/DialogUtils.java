package com.zoker.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.zekers.pjutils.BaseApplication;

/**
 * Created by Administrator on 2017/3/9.
 */

public class DialogUtils {
    public static Dialog getLoadingDialog(Context context, String msg) {
        return ProgressDialog.show(context, null, msg, false, false);
    }
    public static Dialog getLoadingDialog(Context context, String msg,boolean cancelable) {
        return ProgressDialog.show(context, null, msg, false, cancelable);
    }


    public static void success(Dialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
    public static void success(Dialog dialog, String msg) {
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
            Toast.makeText(BaseApplication.Instance, msg, Toast.LENGTH_SHORT).show();
        }
    }
    public static void fail(Dialog dialog, String msg) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            Toast.makeText(BaseApplication.Instance, msg, Toast.LENGTH_SHORT).show();
        }
    }
    public static void destroy(Dialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}


