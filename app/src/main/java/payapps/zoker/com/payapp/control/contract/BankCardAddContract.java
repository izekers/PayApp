package payapps.zoker.com.payapp.control.contract;

import com.zoker.base.BasePresenter;
import com.zoker.base.BaseView;

/**
 * Created by Administrator on 2017/3/9.
 */

public interface BankCardAddContract {
    interface View extends BaseView {
        void loading();

        void success();

        void fail(String msg);

    }

    interface Presenter extends BasePresenter<View> {
        void AddBankCard(String BankName,String CardNumber,String BankMobilephone,String Captcha);
        void sendMobileCaptcha(String phone);
    }
}
