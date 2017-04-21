package payapps.zoker.com.payapp.control.contract;

import com.zoker.base.BasePresenter;
import com.zoker.base.BaseView;

/**
 * Created by Administrator on 2017/3/9.
 */

public interface NewPhoneContract {
    interface View extends BaseView {
        void uploading();

        void success();

        void fail(String msg);

    }

    interface Presenter extends BasePresenter<View> {
        void UpdateMobilephone(String newMobilephone, String captcha);
        void sendMobileCaptcha(String phone);
    }
}
