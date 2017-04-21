package payapps.zoker.com.payapp.control.contract;

import com.zoker.base.BasePresenter;
import com.zoker.base.BaseView;

/**
 * Created by Administrator on 2017/3/9.
 */

public interface ForgetContract {
    interface View extends BaseView {
        void loging();

        void success();

        void fail(String msg);

    }

    interface Presenter extends BasePresenter<View> {
        void ForgotPassword(String id, String password, String Captcha);
        void sendMobileCaptcha(String phone);
    }
}
