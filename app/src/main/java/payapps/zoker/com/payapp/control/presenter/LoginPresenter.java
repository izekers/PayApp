package payapps.zoker.com.payapp.control.presenter;

import android.widget.Toast;

import com.google.gson.Gson;
import com.zekers.network.base.RxNetHelper;
import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;

import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.contract.LoginContract;
import payapps.zoker.com.payapp.control.record.TokenRecord;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/9.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private PayAction payAction;

    @Override
    public void login(String phone, final String password, String captcha) {
        view.loging();
        payAction.userLogin(phone, password, captcha).subscribe(new RxSubscribe<User>() {
            @Override
            public void onError(String message) {
                Logger.d("LoginPresenter", "error");
                view.fail(message);
            }

            @Override
            public void onNext(User user) {
                try {
                    TokenRecord.getInstance().save(PayAction.getRsaPassword(password + user.getTimeSignature()));
                    UserRecord.getInstance().save(user);
                    Logger.d("LoginPresenter", "success=" + new Gson().toJson(user));
                    view.success();
                } catch (Exception e) {
                    e.printStackTrace();
                    view.fail("未知错误");
                }
            }
        });
    }

    @Override
    public void sendMobileCaptcha(String phone) {
        Logger.d("LoginPresenter", "发送验证码");
        payAction.sendMobileCaptcha(phone, PayAction.vercode_login)
                .subscribe(new RxSubscribe<DataWrapper>() {
                    @Override
                    public void onError(String message) {
                        Logger.d("LoginPresenter", "error");
                    }

                    @Override
                    public void onNext(DataWrapper s) {
                        Logger.d("LoginPresenter", "success");
                    }
                });
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
        payAction = new PayAction();
    }
}
