package payapps.zoker.com.payapp.control.presenter;

import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;

import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.contract.LoginContract;
import payapps.zoker.com.payapp.control.contract.NewPhoneContract;
import payapps.zoker.com.payapp.control.record.TokenRecord;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/9.
 */
public class NewPhonePresenter implements NewPhoneContract.Presenter {
    private NewPhoneContract.View view;
    private PayAction payAction;

    @Override
    public void UpdateMobilephone(String newMobilephone, String captcha) {
        view.uploading();
        payAction.UpdateMobilephone(newMobilephone, captcha).subscribe(new RxSubscribe<DataWrapper>() {
            @Override
            public void onError(String message) {
                Logger.d("LoginPresenter", "error");
                view.fail(message);
            }

            @Override
            public void onNext(DataWrapper user) {
                RxBus.getInstance().send(Events.FRESH_USERINFO,null);
                    view.success();
            }
        });
    }

    @Override
    public void sendMobileCaptcha(String phone) {
        Logger.d("LoginPresenter", "发送验证码");
        payAction.sendMobileCaptcha(phone, PayAction.vercode_phone_change)
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
    public void attachView(NewPhoneContract.View view) {
        this.view = view;
        payAction = new PayAction();
    }
}
