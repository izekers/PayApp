package payapps.zoker.com.payapp.control.presenter;

import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.utils.encryption.Base64Utils;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;

import java.io.File;
import java.util.List;

import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.contract.MyDetailContract;
import payapps.zoker.com.payapp.model.ImageResult;
import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/13.
 */

public class MyDetailPresenter implements MyDetailContract.Presenter {
    private MyDetailContract.View view;
    PayAction payAction;

    @Override
    public void attachView(MyDetailContract.View view) {
        this.view = view;
        payAction = new PayAction();
    }

    @Override
    public void UploadHeadImage(File file) {
        view.uploading();
        try {
            payAction.UploadHeadImage(Base64Utils.encodeBase64File(file))
                    .subscribe(new RxSubscribe<ImageResult>() {
                        @Override
                        public void onError(String message) {
                            view.upfail(message);
                        }

                        @Override
                        public void onNext(ImageResult s) {
                            RxBus.getInstance().send(Events.FRESH_USERINFO,null);
                            view.upImgSuccess(s.getImageUrl());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            view.upfail("未知错误");
        }
    }

    @Override
    public void UpdateUserBasicInfo(User user) {
        Logger.d("MyDetailPresenter","#UpdateUserBasicInfo");
        view.uploading();
        payAction.UpdateUserBasicInfo(user)
                .subscribe(new RxSubscribe<DataWrapper>() {
                    @Override
                    public void onError(String message) {
                        view.upfail(message);
                    }

                    @Override
                    public void onNext(DataWrapper s) {
                        view.upSuccess();
                    }
                });
    }
}
