package com.zekers.pjutils;

import android.app.Application;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zekers.utils.permission.PermissionManager;


/**
 *
 * Created by Zoker on 2017/1/22.
 */

public class BaseApplication extends Application {
    public static BaseApplication Instance;
    public void onCreate() {
        Instance = this;
        super.onCreate();
    }
    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
