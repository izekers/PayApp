package payapps.zoker.com.payapp.control.presenter;

import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;

import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.contract.AddBookContract;
import payapps.zoker.com.payapp.control.contract.AddGoodsContract;
import payapps.zoker.com.payapp.model.FirstList;
import payapps.zoker.com.payapp.model.SecondList;

/**
 * Created by Administrator on 2017/3/9.
 */

public class AddGoodsPresenter implements AddGoodsContract.Presenter {
    private AddGoodsContract.View view;
    private PayAction payAction;


    @Override
    public void GetFirstList() {
        view.loading();
        payAction.GetFirstList()
                .subscribe(new RxSubscribe<FirstList>() {
                    @Override
                    public void onError(String message) {
                        view.fail(message);
                    }

                    @Override
                    public void onNext(FirstList s) {
                        if (s==null)
                            view.getFirstSucc(null);
                        else
                            view.getFirstSucc(s.getFirstList());
                    }
                });
    }

    @Override
    public void GetSecondList(String firstCateID) {
        view.loading();
        payAction.GetSecondList(firstCateID)
                .subscribe(new RxSubscribe<SecondList>() {
                    @Override
                    public void onError(String message) {
                        view.fail(message);
                    }

                    @Override
                    public void onNext(SecondList s) {
                        if (s==null)
                            view.getSecondSucc(null);
                        else
                            view.getSecondSucc(s.getSecondList());
                    }
                });
    }

    @Override
    public void AddProduct(String productName, String firstCateID, String secondCateID, String threeCateID, String price, String unit, String productCover, String productImage) {
        view.loading();
        payAction.AddProduct(productName,firstCateID,secondCateID,threeCateID,price,unit,productCover,productImage)
                .subscribe(new RxSubscribe<DataWrapper>() {
                    @Override
                    public void onError(String message) {
                        view.fail(message);
                    }

                    @Override
                    public void onNext(DataWrapper s) {
                        RxBus.getInstance().send(Events.FRESH_GOODLIST,null);
                        view.addProductSucc();
                    }
                });

    }

    @Override
    public void attachView(AddGoodsContract.View view) {
        this.view = view;
        payAction = new PayAction();
    }
}
