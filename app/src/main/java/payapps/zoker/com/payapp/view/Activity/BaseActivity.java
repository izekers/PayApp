package payapps.zoker.com.payapp.view.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.zekers.network.base.RxSubscribe;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.permission.PermissionCallback;
import com.zekers.utils.permission.PermissionManager;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;

import java.util.ArrayList;
import java.util.List;

import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.record.TokenRecord;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.Goods;
import payapps.zoker.com.payapp.model.User;
import rx.functions.Action1;

/**
 * Created by Zoker on 2017/3/1.
 */

public class BaseActivity extends AppCompatActivity {
    private static boolean isRefresh = false;
    protected static List<Goods> beSelectGoods = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.getInstance().addSubscription(getName(), RxBus.getSubscriber().setEvent(Events.LOGIN_MISS).onNext(new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                TokenRecord.getInstance().clearInfo();
                UserRecord.getInstance().clearInfo();
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }).create());

        RxBus.getInstance().addSubscription(getName(), RxBus.getSubscriber().setEvent(Events.FRESH_USERINFO).onNext(new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                synchronized (BaseActivity.class) {
                    if (!isRefresh) {
                        isRefresh = true;
                        RxBus.getInstance().addSubscription(getName(), new PayAction().GetUserInfo().subscribe(new RxSubscribe<User>() {
                            @Override
                            public void onError(String message) {
                                Logger.d("PayAction", message);
                                isRefresh = false;
                            }

                            @Override
                            public void onNext(User user) {
                                UserRecord.getInstance().save(user);
                                isRefresh = false;
                            }
                        }));
                    }
                }
            }
        }).create());
    }

    protected String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubscribe(getName());
    }

    protected void showSelectMsg(String title, final SelectCallBack callBack) {
        new AlertDialog.Builder(this).setTitle(title)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callBack.ok();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callBack.cancel();
                    }
                }).create().show();
    }

    protected void showDialogMsg(String title,String msg) {
        new AlertDialog.Builder(this).setTitle(title).setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();
    }

    public interface SelectCallBack {
        void cancel();

        void ok();
    }

    protected void checkPermisstionAndrThenLoad(final String permission, final PermissionCallback PermisstionCallBack) {
        if (PermissionManager.checkPermission(permission)) {
            if(PermisstionCallBack!=null)
                PermisstionCallBack.permissionGranted();
        } else {
            if (PermissionManager.shouldShowRequestPermissionRationale(this, permission)) {
                PermissionManager.askForPermission(BaseActivity.this, permission, PermisstionCallBack);
            }else {
                PermissionManager.askForPermission(BaseActivity.this, permission, PermisstionCallBack);
            }
        }
    }

    @Override
    public void finish() {
        closeSoftKeybord(((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0),this);
        super.finish();
    }

    public static void closeSoftKeybord(View mEditText, Context mContext) {
        Log.e("BaseActivity","#closeSoftKeybord=");
        if (mEditText==null)
            return;
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        Log.e("BaseActivity","#closeSoftKeybord="+imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0));
    }

    //此方法只是关闭软键盘
    private void hintKbTwo() {

        if(getCurrentFocus()!=null)
        {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus()
                            .getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
