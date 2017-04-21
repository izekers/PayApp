package payapps.zoker.com.payapp.control.presenter;

import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.pjutils.BaseApplication;
import com.zekers.utils.logger.Logger;

import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.contract.BankCardAddContract;
import payapps.zoker.com.payapp.control.contract.LoginContract;
import payapps.zoker.com.payapp.control.record.TokenRecord;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.BankCard;
import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/9.
 */

public class BankCardAddPresenter implements BankCardAddContract.Presenter {
    private BankCardAddContract.View view;
    private PayAction payAction;

    @Override
    public void AddBankCard(String BankName, String CardNumber, String BankMobilephone, String Captcha) {
        if (BankName==null || BankName.equals("")||
                CardNumber==null || CardNumber.equals("")||
                BankMobilephone==null || BankMobilephone.equals("")||
                Captcha==null || Captcha.equals("")){
            BaseApplication.Instance.showToast("信息填写不完整，请补充完全");
            return;
        }
        view.loading();
        payAction.AddBankCard(BankName,CardNumber,BankMobilephone,Captcha)
                .subscribe(new RxSubscribe<BankCard>() {
                    @Override
                    public void onError(String message) {
                        view.fail(message);
                    }

                    @Override
                    public void onNext(BankCard s) {
                        view.success();
                    }
                });
    }

    @Override
    public void sendMobileCaptcha(String phone) {
        Logger.d("LoginPresenter", "发送验证码");
        payAction.sendMobileCaptcha(phone, PayAction.vercode_AddBankCard)
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
    public void attachView(BankCardAddContract.View view) {
        this.view = view;
        payAction = new PayAction();
    }
}
