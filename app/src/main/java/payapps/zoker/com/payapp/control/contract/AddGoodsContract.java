package payapps.zoker.com.payapp.control.contract;

import android.net.Uri;

import com.zoker.base.BasePresenter;
import com.zoker.base.BaseView;

import java.io.File;
import java.util.List;

import payapps.zoker.com.payapp.model.FirstList;
import payapps.zoker.com.payapp.model.SecondList;

/**
 * Created by Administrator on 2017/3/9.
 */

public interface AddGoodsContract {
    interface View extends BaseView {
        void loading();
        void getFirstSucc(List<FirstList.Data> datas);
        void getSecondSucc(List<SecondList.Data> datas);
        void addProductSucc();
        void fail(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void GetFirstList();
        void GetSecondList(String firstCateID);
        void AddProduct(String productName, String firstCateID, String secondCateID, String threeCateID, String price, String unit, String productCover, String productImage);
    }
}
