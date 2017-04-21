package payapps.zoker.com.payapp.control.presenter;

import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.utils.logger.Logger;

import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.contract.AddBookContract;
import payapps.zoker.com.payapp.control.contract.LoginContract;
import payapps.zoker.com.payapp.control.record.TokenRecord;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.ContactMan;
import payapps.zoker.com.payapp.model.ContactMans;
import payapps.zoker.com.payapp.model.FirstList;
import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/9.
 */

public class AddBookPresenter implements AddBookContract.Presenter {
    private AddBookContract.View view;
    private PayAction payAction;

    @Override
    public void GetAddressBook(boolean isLoad) {
        Logger.d("LoginPresenter", "获取通讯录");
        if (isLoad)
            view.loading();
        payAction.GetAddressBook()
                .subscribe(new RxSubscribe<ContactMans>() {
                    @Override
                    public void onError(String message) {
                        view.fail(message);
                    }

                    @Override
                    public void onNext(ContactMans s) {
                        if (s!=null)
                            view.success(s.getContactMan());
                        else
                            view.success(null);
                    }
                });
    }

    @Override
    public void attachView(AddBookContract.View view) {
        this.view = view;
        payAction = new PayAction();
    }
}
