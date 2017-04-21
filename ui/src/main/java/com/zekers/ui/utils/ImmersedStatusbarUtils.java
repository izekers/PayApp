package com.zekers.ui.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zekers.ui.R;

/**
 * 用于设置浸入式
 * Created by Administrator on 2016/8/8.
 */
public class ImmersedStatusbarUtils {
    private Activity activity;
    public ImmersedStatusbarUtils(Activity activity){
        this.activity=activity;
    };
    public void setTintColor(int colorId){
        //为状态栏着色
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(colorId);
    }

    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static void setImmersionStatus(Activity activity,boolean on){
        if (!on){
            //浸入式
            ImmersedStatusbarUtils utils = new ImmersedStatusbarUtils(activity);
            //只对api19以上版本有效
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                utils.setTranslucentStatus(false);
                ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0).setFitsSystemWindows(false);
                utils.setTintColor(R.color.transparent);
            }
            return;
        }
        //浸入式
        ImmersedStatusbarUtils utils = new ImmersedStatusbarUtils(activity);
        //只对api19以上版本有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            utils.setTranslucentStatus(true);
            ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0).setFitsSystemWindows(true);
            utils.setTintColor(R.color.main);
        }
    }
}
