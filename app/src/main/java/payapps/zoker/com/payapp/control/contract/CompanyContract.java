package payapps.zoker.com.payapp.control.contract;

import android.net.Uri;

import com.zoker.base.BasePresenter;
import com.zoker.base.BaseView;

import java.io.File;

/**
 * Created by Administrator on 2017/3/9.
 */

public interface CompanyContract {
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
        void UploadBusinessLicenseImage(File file);
        void UpdateUserBusinessLicenseAuth(String companyName, String businessLicenseImage, String businessLicenseNumber);
    }
}
