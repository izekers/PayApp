package payapps.zoker.com.payapp.view.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.zekers.pjutils.BaseApplication;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.permission.PermissionManager;
import com.zoker.base.DialogUtils;
import com.zoker.base.PhotoSelectUtils;

import java.io.File;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import payapps.zoker.com.payapp.PayApplication;
import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.contract.CompanyContract;
import payapps.zoker.com.payapp.control.presenter.CompanyAuthPresenter;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.User;
import payapps.zoker.com.payapp.util.PhotoUtil;

/**
 * Created by Administrator on 2017/3/6.
 */

public class CompanyAuthActivity extends BaseActivity implements CompanyContract.View{
    AbilityToolBar abilityToolBar;
    CompanyContract.Presenter presenter;
    private EditText company_num,company_name;
    private ImageView company_img;
    private String imgurl=null;

    private static final int GET_IMG = 198;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_auth);
        presenter=new CompanyAuthPresenter();
        presenter.attachView(this);
        abilityToolBar=(AbilityToolBar)findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.company_authentication));
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        abilityToolBar.setMenuRes(R.menu.ok_menu);
        abilityToolBar.setOnMenuItemClickListener(new AbilityToolBar.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.ok:
                        if (company_name.getText().toString().equals("")||company_num.getText().toString().equals("")){
                            BaseApplication.Instance.showToast("信息不完整，无法提交");
                            return;
                        }
                        if (imgurl==null){
                            BaseApplication.Instance.showToast("请上传企业证书");
                            return;
                        }
                        presenter.UpdateUserBusinessLicenseAuth(company_name.getText().toString(),imgurl,company_num.getText().toString());
                        break;
                }
            }
        });
        company_num=(EditText)findViewById(R.id.company_num);
        company_name=(EditText)findViewById(R.id.company_name);
        company_img=(ImageView)findViewById(R.id.company_img);
        company_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoUtil.with(CompanyAuthActivity.this).showWindows(GET_IMG,"上传证件图片");
                /**
                PhotoSelectUtils.showPhotoSelect("上传证件图片", CompanyAuthActivity.this, 1, 2, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        File file=new File(resultList.get(0).getPhotoPath());
                        presenter.UploadBusinessLicenseImage(file);
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {

                    }
                });
                 **/
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
    User user;
    private void initData(){
        user= UserRecord.getInstance().load();
        if (user.getCompanyName()!=null)
            company_name.setText(""+user.getCompanyName());
//        company_number.setText(user.get);
    }
    Dialog dialog;
    @Override
    public void uploading() {
        dialog = DialogUtils.getLoadingDialog(this, "正在上传图片",false);
    }

    @Override
    public void upSuccess(String uri) {
        DialogUtils.success(dialog);
        PayApplication.Instance.showToast("上传成功");
        imgurl=uri;
        Glide.with(this).load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(company_img);
    }

    @Override
    public void upfail(String msg) {
        DialogUtils.fail(dialog, "上传失败，"+msg);
    }

    @Override
    public void uploadAuthing() {
        dialog = DialogUtils.getLoadingDialog(this, "正在提交验证数据");
    }

    @Override
    public void updateAuthSucc() {
        DialogUtils.success(dialog);
        PayApplication.Instance.showToast("提交成功");
        finish();
    }

    @Override
    public void updateAuthFail(String msg) {
        DialogUtils.fail(dialog, "提交失败，"+msg);
    }

    @Override
    public void updateAuthToast(String msg) {
        PayApplication.Instance.showToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        File img = PhotoSelectUtils.onResultWithoutCrop(this, requestCode, resultCode, data);
        String string=PhotoUtil.onSingleResult(GET_IMG,requestCode,resultCode,data);
        if (string!=null){
            img =new File(string);
            Logger.e("isexists="+img.exists());
        }

        if (img != null) {
            presenter.UploadBusinessLicenseImage(img);
        }
    }
}
