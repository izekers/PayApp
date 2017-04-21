package payapps.zoker.com.payapp;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zekers.pjutils.BaseApplication;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.permission.PermissionManager;

import payapps.zoker.com.payapp.util.PhotoUtil;
import payapps.zoker.com.payapp.view.GlideUtils;

/**
 * Created by Zoker on 2017/3/1.
 */
public class PayApplication extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        PermissionManager.init(this);
        ZXingLibrary.initDisplayOpinion(this);
        GlideUtils.init(this);
        PhotoUtil.init(this);
    }
}
