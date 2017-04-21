package payapps.zoker.com.payapp.control.presenter;

import android.net.Uri;

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
import payapps.zoker.com.payapp.control.contract.RealNameContract;
import payapps.zoker.com.payapp.model.ImageResult;
import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/13.
 */

public class RealNamePresenter implements RealNameContract.Presenter {
    private RealNameContract.View view;
    private PayAction payAction;

    @Override
    public void attachView(RealNameContract.View view) {
        this.view = view;
        payAction = new PayAction();
    }

    @Override
    public void updateImg(final File file) {
        view.uploading();
        try {
            payAction.UploadIdCardImage(Base64Utils.encodeBase64File(file))
                    .subscribe(new RxSubscribe<ImageResult>() {
                        @Override
                        public void onError(String message) {
                            Logger.d("message", message);
                            view.upfail(message);
                        }

                        @Override
                        public void onNext(ImageResult result) {
                            view.upSuccess(result.getImageUrl());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            view.upfail("未知错误");
        }
    }

    @Override
    public void UpdateUserIdCardAuth(String userName, String idCardImage, String idCardNumber) {
        view.uploadAuthing();
        payAction.UpdateUserIdCardAuth(userName, idCardImage, idCardNumber)
                .subscribe(new RxSubscribe<DataWrapper>() {
                    @Override
                    public void onError(String message) {
                        view.updateAuthFail(message);
                    }

                    @Override
                    public void onNext(DataWrapper s) {
                        view.updateAuthSucc();
                        RxBus.getInstance().send(Events.FRESH_USERINFO, null);
                    }
                });
    }
}
