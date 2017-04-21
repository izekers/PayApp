package payapps.zoker.com.payapp.control.contract;

import com.zoker.base.BasePresenter;
import com.zoker.base.BaseView;

import java.util.List;

import payapps.zoker.com.payapp.model.ContactMan;
import payapps.zoker.com.payapp.model.FirstList;
import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/9.
 */

public interface AddBookContract {
    interface View extends BaseView {
        void loading();

        void success(List<ContactMan> data);

        void fail(String msg);

    }

    interface Presenter extends BasePresenter<View> {
        void GetAddressBook(boolean isLoad);
    }
}
