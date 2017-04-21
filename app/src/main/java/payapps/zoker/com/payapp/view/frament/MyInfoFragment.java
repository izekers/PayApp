package payapps.zoker.com.payapp.view.frament;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zekers.pjutils.BaseApplication;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.ui.view.widget.ItemView;
import com.zekers.utils.rx.EventBus.RxBus;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.record.TokenRecord;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.User;
import payapps.zoker.com.payapp.view.Activity.AccountActivity;
import payapps.zoker.com.payapp.view.Activity.AuthActivity;
import payapps.zoker.com.payapp.view.Activity.BankCardManagerActivity;
import payapps.zoker.com.payapp.view.Activity.CompanyAuthActivity;
import payapps.zoker.com.payapp.view.Activity.CompanyInfoActivity;
import payapps.zoker.com.payapp.view.Activity.LoginActivity;
import payapps.zoker.com.payapp.view.Activity.MyDetailActivity;
import payapps.zoker.com.payapp.view.Activity.PayPlatmentActivity;
import payapps.zoker.com.payapp.view.Activity.RealNameActivity;
import payapps.zoker.com.payapp.view.Constant;


/**
 * Created by Zoker on 2017/3/2.
 */
public class MyInfoFragment extends Fragment{
    View bankcard_view,
            companyinfo_view,account_view,pay_view,exit_view,user_view;
    ItemView realname_view,company_view;
    AbilityToolBar abilityToolBar;
    boolean isHideback=true;
    User user;
    ImageView user_icon;
    TextView user_nick;
   public static MyInfoFragment getInstance(boolean isHideback){
       MyInfoFragment fragment=new MyInfoFragment();
       Bundle bundle=new Bundle();
       bundle.putBoolean(Constant.IS_HIDE_BACK,isHideback);
       fragment.setArguments(bundle);
       return fragment;
   }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isHideback=getArguments().getBoolean(Constant.IS_HIDE_BACK);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my,container,false);
        initView(view);
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        user= UserRecord.getInstance().load();
        initData();
    }

    private void initView(View view){
        bankcard_view=view.findViewById(R.id.bankcard_view);
        realname_view=(ItemView) view.findViewById(R.id.realname_view);
        company_view=(ItemView) view.findViewById(R.id.company_view);
        companyinfo_view=view.findViewById(R.id.companyinfo_view);
        account_view=view.findViewById(R.id.account_view);
        pay_view=view.findViewById(R.id.pay_view);
        exit_view=view.findViewById(R.id.exit_view);
        user_view=view.findViewById(R.id.user_view);
        user_icon=(ImageView) view.findViewById(R.id.user_icon);
        user_nick=(TextView) view.findViewById(R.id.user_nick);
        abilityToolBar=(AbilityToolBar) view.findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.me));
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        if (isHideback)
            abilityToolBar.hideBackView();
    }
    private void initListener(){
        user_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), MyDetailActivity.class);
                startActivity(intent);
            }
        });
        bankcard_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), BankCardManagerActivity.class);
                startActivity(intent);
            }
        });
        realname_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getIdCardAuth()==0 || user.getIdCardAuth()==3){
                    Intent intent=new Intent(getContext(), RealNameActivity.class);
                    startActivity(intent);
                }else if (user.getIdCardAuth()==2){
                    Intent intent=new Intent(getContext(), AuthActivity.class);
                    startActivity(intent);
                }else if (user.getIdCardAuth()==1){
                    BaseApplication.Instance.showToast("审核已通过，无需重新提交");
                }
            }
        });
        company_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getBusinessLicenseAuth()==0 || user.getBusinessLicenseAuth()==3){
                    Intent intent=new Intent(getContext(), CompanyAuthActivity.class);
                    startActivity(intent);
                }else if (user.getBusinessLicenseAuth()==2){
                    Intent intent=new Intent(getContext(), AuthActivity.class);
                    intent.putExtra("auth","company");
                    startActivity(intent);
                }else if (user.getBusinessLicenseAuth()==1){
                    BaseApplication.Instance.showToast("审核已通过，无需重新提交");
                }
            }
        });
        companyinfo_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), CompanyInfoActivity.class);
                startActivity(intent);
            }
        });
        account_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), AccountActivity.class);
                startActivity(intent);
            }
        });
        pay_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), PayPlatmentActivity.class);
                startActivity(intent);
            }
        });
        exit_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TokenRecord.getInstance().clearInfo();
                Intent intent=new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
    public void initData(){
        if (user.getSex().equals("女")){
            Glide.with(this)
                    .load(user.getHeadImage())
                    .placeholder(R.drawable.women)
                    .error(R.drawable.women)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(user_icon);
        }else {
            Glide.with(this)
                    .load(user.getHeadImage())
                    .placeholder(R.drawable.man)
                    .error(R.drawable.man)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(user_icon);
        }

        if (user.getNickName()!=null){
            user_nick.setText(user.getNickName());
        }else {
            user_nick.setText("");
        }
        realname_view.setAuth(user.getIdCardAuth());
        company_view.setAuth(user.getBusinessLicenseAuth());
    }
}
