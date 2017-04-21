package payapps.zoker.com.payapp.control.contract;

import android.net.Uri;

import com.zoker.base.BasePresenter;
import com.zoker.base.BaseView;

import java.io.File;

/**
 * Created by Administrator on 2017/3/9.
 */

public interface RealNameContract {
    interface View extends BaseView {
        void uploading();

        void upSuccess(String uri);

        void upfail(String msg);

        void uploadAuthing();
        void updateAuthSucc();
        void updateAuthFail(String msg);
        void updateAuthToast(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void updateImg(File file);
        void UpdateUserIdCardAuth(String userName, String idCardImage, String idCardNumber);
    }
}
