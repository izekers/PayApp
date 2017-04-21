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
import com.zekers.utils.PatternUtil;
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
import payapps.zoker.com.payapp.control.contract.RealNameContract;
import payapps.zoker.com.payapp.control.presenter.RealNamePresenter;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.User;
import payapps.zoker.com.payapp.util.PhotoUtil;

/**
 * Created by Administrator on 2017/3/6.
 */

public class RealNameActivity extends BaseActivity implements RealNameContract.View {
    private AbilityToolBar abilityToolBar;
    private RealNameContract.Presenter presenter;
    private Dialog dialog;
    private ImageView realname_img1, realname_img2, realname_img3;
    private EditText real_number_txt, real_name_txt;
    private static final int img1_code = 55;
    private static final int img2_code = 56;
    private static final int img3_code = 57;
    private String img1 = null;
    private String img2 = null;
    private String img3 = null;
    private int updateImg;
    User user;

    private static final int GET_IMG = 198;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name);
        presenter = new RealNamePresenter();
        presenter.attachView(this);
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.real_name_authentication));
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
                        if (real_number_txt.getText().toString().equals("")||real_number_txt.getText().toString().equals("")){
                            BaseApplication.Instance.showToast("请完善信息后提交");
                            return;
                        }
                        if (!PatternUtil.isIdCard(real_number_txt.getText().toString())){
                            BaseApplication.Instance.showToast("请上传正确的身份证信息");
                            return;
                        }
                        if (img1 == null) {
                            BaseApplication.Instance.showToast("请上传正面证件照");
                            return;
                        }
                        if (img2 == null) {
                            BaseApplication.Instance.showToast("请上传背面证件照");
                            return;
                        }
                        if (img3 == null) {
                            BaseApplication.Instance.showToast("请上传手持证件照");
                            return;
                        }
                        presenter.UpdateUserIdCardAuth(real_name_txt.getText().toString(), img1 + "," + img2 + "," + img3, real_number_txt.getText().toString());
                        break;
                }
            }
        });
        realname_img1 = (ImageView) findViewById(R.id.realname_img1);
        realname_img2 = (ImageView) findViewById(R.id.realname_img2);
        realname_img3 = (ImageView) findViewById(R.id.realname_img3);
        real_number_txt = (EditText) findViewById(R.id.real_number_txt);
        real_name_txt = (EditText) findViewById(R.id.real_name_txt);
        realname_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateImg = img1_code;
                PhotoUtil.with(RealNameActivity.this).showWindows(GET_IMG,"上传身份证正面");
                /**
                PhotoSelectUtils.showPhotoSelect("上传身份证正面", RealNameActivity.this, 1, 2, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        Logger.d("test",new Gson().toJson(resultList));
                        File file=new File(resultList.get(0).getPhotoPath());
                        presenter.updateImg(file);
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {

                    }
                });
                 **/
            }
        });

        realname_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateImg = img2_code;
                PhotoUtil.with(RealNameActivity.this).showWindows(GET_IMG,"上传身份证背面");
                /**
                PhotoSelectUtils.showPhotoSelect("上传身份证背面", RealNameActivity.this, 1, 2, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        File file=new File(resultList.get(0).getPhotoPath());
                        presenter.updateImg(file);
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {

                    }
                });
                 **/
            }
        });
        realname_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateImg = img3_code;
                PhotoUtil.with(RealNameActivity.this).showWindows(GET_IMG,"上传手持身份证照片");
                /**
                PhotoSelectUtils.showPhotoSelect("上传手持身份证照片", RealNameActivity.this, 1, 2, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        File file=new File(resultList.get(0).getPhotoPath());
                        presenter.updateImg(file);
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
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    private void initData() {
        real_name_txt.setText(user.getUserName());
        real_number_txt.setText(user.getIdCardNumber());
        if (img1 != null)
            Glide.with(this).load(img1)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(realname_img1);

        if (img2 != null)
            Glide.with(this).load(img2)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(realname_img2);

        if (img3 != null)
            Glide.with(this).load(img3)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(realname_img3);

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
            Logger.d("message",""+img.getAbsolutePath());
                presenter.updateImg(img);
        }
    }

    @Override
    public void uploading() {
        dialog = DialogUtils.getLoadingDialog(this, "正在上传图片",false);
    }

    @Override
    public void upSuccess(String imageUri) {
        Logger.d("tag","uri:"+imageUri);
        DialogUtils.success(dialog);
        PayApplication.Instance.showToast("上传成功");

        if (updateImg == img1_code) {
            img1 = imageUri;
            Glide.with(this).load(img1)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(realname_img1);
        } else if (updateImg == img2_code) {
            img2 = imageUri;
            Glide.with(this).load(img2)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(realname_img2);
        } else if (updateImg == img3_code) {
            img3 = imageUri;
            Glide.with(this).load(img3)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(realname_img3);
        }
    }

    @Override
    public void upfail(String msg) {
        DialogUtils.fail(dialog, "上传失败，" + msg);
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
        DialogUtils.fail(dialog, "提交失败，" + msg);
    }

    @Override
    public void updateAuthToast(String msg) {
        PayApplication.Instance.showToast(msg);
    }
}
