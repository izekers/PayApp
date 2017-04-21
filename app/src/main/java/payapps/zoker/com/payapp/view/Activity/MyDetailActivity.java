package payapps.zoker.com.payapp.view.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.zekers.pjutils.BaseApplication;
import com.zekers.ui.utils.PhotoUtils;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.CommonUtils;
import com.zekers.utils.PatternUtil;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.permission.PermissionCallback;
import com.zekers.utils.permission.PermissionManager;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;
import com.zoker.base.PhotoSelectUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import payapps.zoker.com.payapp.PayApplication;
import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.contract.AddGoodsContract;
import payapps.zoker.com.payapp.control.contract.MyDetailContract;
import payapps.zoker.com.payapp.control.presenter.MyDetailPresenter;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.CityList;
import payapps.zoker.com.payapp.model.ProvinceList;
import payapps.zoker.com.payapp.model.User;
import payapps.zoker.com.payapp.util.PhotoUtil;
import payapps.zoker.com.payapp.view.Constant;

/**
 * Created by Administrator on 2017/3/6.
 */

public class MyDetailActivity extends BaseActivity implements MyDetailContract.View {
    private AbilityToolBar abilityToolBar;
    private View user_view;
    private ImageView icon_view;
    private MyDetailContract.Presenter presenter;
    private Dialog dialog;
    private View nick_layout, email_layout, sex_layout, address_layout, signature_layout;
    private TextView id_view, nick_view, email_view, sex_view, address_view, signature_view;
    private static final int CHANGE_INFO_CODE = 105;
    private User user;
    private View ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_detail);
        user = UserRecord.getInstance().load();
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.my_info));
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
                if (id == R.id.ok) {
                    presenter.UpdateUserBasicInfo(user);
                }
            }
        });
        ok = abilityToolBar.findViewById(R.id.ok);
        ok.setVisibility(View.GONE);
        initView();
        presenter = new MyDetailPresenter();
        presenter.attachView(this);
    }

    public void initView() {
        user_view = findViewById(R.id.user_view);
        nick_layout = findViewById(R.id.nick_layout);
        email_layout = findViewById(R.id.email_layout);
        sex_layout = findViewById(R.id.sex_layout);
        address_layout = findViewById(R.id.address_layout);
        signature_layout = findViewById(R.id.signature_layout);
        icon_view = (ImageView) findViewById(R.id.icon_view);
        id_view = (TextView) findViewById(R.id.id_view);
        nick_view = (TextView) findViewById(R.id.nick_view);
        email_view = (TextView) findViewById(R.id.email_view);
        sex_view = (TextView) findViewById(R.id.sex_view);
        address_view = (TextView) findViewById(R.id.address_view);
        signature_view = (TextView) findViewById(R.id.signature_view);
        initData();
        initListener();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    private static final int GET_IMG = 198;
    public void initListener() {
        user_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoUtil.with(MyDetailActivity.this).showWindows(GET_IMG,"上传证件图片");
                /**
                PhotoSelectUtils.showPhotoSelect("修改头像", MyDetailActivity.this, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        File file=new File(resultList.get(0).getPhotoPath());
                        Logger.d("test",""+file.exists()+"resultList.get(0).getPhotoPath()="+resultList.get(0).getPhotoPath());
                        presenter.UploadHeadImage(file);
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {

                    }
                });
                 **/
            }
        });

        nick_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnChangeView(Constant.ChangeType.NAME);
            }
        });
        email_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnChangeView(Constant.ChangeType.EMAIL);
            }
        });
        sex_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnChangeView(Constant.ChangeType.SEX);
            }
        });
        signature_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnChangeView(Constant.ChangeType.SIGNATURE);
            }
        });
        address_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnChangeView(Constant.ChangeType.ADDRESS);
            }
        });
    }

    public void initData() {
        if (user.getSex().equals("女")){
            Glide.with(this)
                    .load(user.getHeadImage())
                    .placeholder(R.drawable.women)
                    .error(R.drawable.women)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(icon_view);
        }else {
            Glide.with(this)
                    .load(user.getHeadImage())
                    .placeholder(R.drawable.man)
                    .error(R.drawable.man)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(icon_view);
        }

        id_view.setText(user.getMobilephone());
        nick_view.setText(user.getNickName());
        email_view.setText(user.getEmail());
        sex_view.setText("" + user.getSex());
        address_view.setText(user.getProvinceName() + " " + user.getCityName());
        signature_view.setText(user.getSignature());
    }

    public void turnChangeView(Constant.ChangeType changeType) {
        Intent intent = new Intent(this, ChangeActivity.class);
        intent.putExtra(Constant.CHANGETYPE, changeType);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, CHANGE_INFO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHANGE_INFO_CODE) {
            if (resultCode == RESULT_OK) {
                updateUser(data);
            }
        } else {
            File img = PhotoSelectUtils.onResult(this, requestCode, resultCode, data);

            String string=PhotoUtil.onSingleResult(GET_IMG,requestCode,resultCode,data);
            if (string!=null){
                img =new File(string);
                Logger.e("isexists="+img.exists());
            }

            if (img != null) {
                presenter.UploadHeadImage(img);
            }
        }
    }

    public void updateUser(Intent intent) {
        Constant.ChangeType changeType = (Constant.ChangeType) intent.getSerializableExtra(Constant.CHANGETYPE);
        switch (changeType) {
            case NAME:
                user.setNickName(intent.getStringExtra(Constant.CHANGE_TXT));
                break;
            case EMAIL:
                user.setEmail(intent.getStringExtra(Constant.CHANGE_TXT));
                break;
            case ADDRESS:
                ProvinceList.Data province = (ProvinceList.Data) intent.getSerializableExtra(Constant.CHANGE_PROVINCE);
                CityList.Data city = (CityList.Data) intent.getSerializableExtra(Constant.CHANGE_CITY);
                if (province != null)
                    user.setProvinceID(province.getAddressID());
                user.setProvinceName(province.getAddressName());
                if (city != null) {
                    user.setCityID(city.getAddressID());
                    user.setCityName(city.getAddressName());
                }
                break;
            case SEX:
                if (intent.getSerializableExtra(Constant.SEX) == Constant.SexType.MAN)
                    user.setSex(1);
                else
                    user.setSex(0);
                break;
            case SIGNATURE:
                user.setSignature(intent.getStringExtra(Constant.CHANGE_TXT_BIG));
                break;
            default:
                return;
        }
        initData();
        ok.setVisibility(View.VISIBLE);
    }

    @Override
    public void uploading() {
        dialog = DialogUtils.getLoadingDialog(this, "正在更新头像",false);
    }

    //    "http://219.136.249.65:8888/upload/headimage/2017/3/27/201732710192954028.jpg"
    @Override
    public void upImgSuccess(String url) {
        if (url == null)
            upfail("未知错误");
        else {
            String s1 = url.substring(0, url.lastIndexOf("."));
            String s2 = url.substring(url.lastIndexOf("."));
            Logger.d("sss", s1);
            Logger.d("sss", s2);
            Logger.d("sss", url);
            String ss = s1 + "_100" + s2;
            Logger.d("sss", ss);
            user.setHeadImage(ss);
            Logger.d("sss", new Gson().toJson(user));
            Glide.with(this).load(ss)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(icon_view);
            DialogUtils.success(dialog, "头像更新成功");
        }
        ok.setVisibility(View.VISIBLE);
    }

    @Override
    public void upSuccess() {
        UserRecord.getInstance().save(user);
        RxBus.getInstance().send(Events.FRESH_USERINFO, null);
        DialogUtils.success(dialog, "用户信息更新成功");
        ok.setVisibility(View.GONE);
    }

    @Override
    public void upfail(String msg) {
        DialogUtils.fail(dialog, "用户信息更新失败，" + msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.destroy(dialog);
    }
}
