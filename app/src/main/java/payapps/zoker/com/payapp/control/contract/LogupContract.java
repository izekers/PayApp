package payapps.zoker.com.payapp.control.contract;

import com.zoker.base.BasePresenter;
import com.zoker.base.BaseView;

/**
 * Created by Administrator on 2017/3/9.
 */

public interface LogupContract {
    interface View extends BaseView {
        void success();
        void loging();
        void fail(String msg);

    }

    interface Presenter extends BasePresenter<LogupContract.View> {
        void logup(String id, String password ,String Captcha);
        void sendMobileCaptcha(String phone);
    }
}
