package payapps.zoker.com.payapp.control.presenter;

import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;

import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.contract.LogupContract;
import payapps.zoker.com.payapp.control.record.TokenRecord;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/9.
 */

public class LogupPresenter implements LogupContract.Presenter {
    private LogupContract.View view;
    private PayAction payAction;

    @Override
    public void logup(String phone, final String password, String captcha) {
        view.loging();

        payAction.userRegister(phone, password, captcha)
                .subscribe(new RxSubscribe<User>() {
                    @Override
                    public void onError(String message) {
                        Logger.d("LoginPresenter", "error");
                        view.fail(message);
                    }
//                    4405  8523

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
        payAction.sendMobileCaptcha(phone, PayAction.vercode_logup)
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
    public void attachView(LogupContract.View view) {
        this.view = view;
        payAction = new PayAction();
    }
}
