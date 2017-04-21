package payapps.zoker.com.payapp.control.contract;

import com.zoker.base.BasePresenter;
import com.zoker.base.BaseView;

import java.io.File;

import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/9.
 */

public interface MyDetailContract {
    interface View extends BaseView {
        void uploading();
        void upImgSuccess(String url);
        void upSuccess();
        void upfail(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void UploadHeadImage(File file);
        void UpdateUserBasicInfo(User user);
    }
}
