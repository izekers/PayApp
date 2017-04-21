package payapps.zoker.com.payapp.control.presenter;

import android.net.Uri;

import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.utils.encryption.Base64Utils;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;

import java.io.File;
import java.util.List;

import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.contract.CompanyContract;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.ImageResult;
import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/13.
 */

public class CompanyAuthPresenter implements CompanyContract.Presenter{
    private CompanyContract.View view;
    private PayAction payAction;

    @Override
    public void UploadBusinessLicenseImage(final File file) {
        view.uploading();
        try {
            payAction.UploadBusinessLicenseImage(Base64Utils.encodeBase64File(file))
                    .subscribe(new RxSubscribe<ImageResult>() {
                        @Override
                        public void onError(String message) {
                            view.upfail(message);
                        }

                        @Override
                        public void onNext(ImageResult s) {
                            if (s!=null)
                             view.upSuccess(s.getImageUrl());
                            else
                             view.upSuccess(null);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            view.upfail("未知错误");
        }
    }

    @Override
    public void UpdateUserBusinessLicenseAuth(String companyName, String businessLicenseImage, String businessLicenseNumber) {
        if (companyName==null){
            view.updateAuthToast("企业名不能为空");
        }else if (businessLicenseImage == null){
            view.updateAuthToast("请上传企业证件");
        }else if (businessLicenseNumber == null){
            view.updateAuthToast("企业编码不能为空");
        }else {
            view.uploadAuthing();
            payAction.UpdateUserBusinessLicenseAuth(companyName,businessLicenseImage,businessLicenseNumber)
                    .subscribe(new RxSubscribe<DataWrapper>() {
                        @Override
                        public void onError(String message) {
                            view.updateAuthFail(message);
                        }

                        @Override
                        public void onNext(DataWrapper s) {
                            RxBus.getInstance().send(Events.FRESH_USERINFO,null);
                            view.updateAuthSucc();
                        }
                    });
        }
    }

    @Override
    public void attachView(CompanyContract.View view) {
        this.view=view;
        payAction=new PayAction();
    }
}
