package payapps.zoker.com.payapp.view.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.pjutils.BaseApplication;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.PatternUtil;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import java.util.List;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.CityList;
import payapps.zoker.com.payapp.model.FirstList;
import payapps.zoker.com.payapp.model.ProvinceList;
import payapps.zoker.com.payapp.model.SecondList;
import payapps.zoker.com.payapp.model.User;
import payapps.zoker.com.payapp.view.Constant;

/**
 * Created by Administrator on 2017/3/6.
 */

public class ChangeActivity extends BaseActivity{
    private AbilityToolBar abilityToolBar;
    private  Constant.ChangeType changeType;
    private View txt_layout,select_layout,big_txt_layout;
    private View man_layout,woManLayout,man_icon,woman_icon,address_layout;
    private EditText input_txt,big_input_txt;
    private Constant.SexType select=null;

    private User user;
    private PayAction payAction;
    private ProvinceList.Data currentProvince=null;
    private CityList.Data currentCityList=new CityList.Data();
    private Spinner sp_provice,sp_city;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        user= UserRecord.getInstance().load();
        payAction=new PayAction();
        input_txt=(EditText) findViewById(R.id.input_txt);
        abilityToolBar=(AbilityToolBar)findViewById(R.id.ability_toolbar);
        abilityToolBar.setMenuRes(R.menu.ok_menu);
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        abilityToolBar.setOnMenuItemClickListener(new AbilityToolBar.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view) {
                int id=view.getId();
                switch (id){
                    case R.id.ok:
                        updateUserInfo();
                        break;
                }
            }
        });
        txt_layout=findViewById(R.id.txt_layout);
        select_layout=findViewById(R.id.select_layout);
        select_layout.setVisibility(View.GONE);
        changeType=(Constant.ChangeType) getIntent().getSerializableExtra(Constant.CHANGETYPE);
        woManLayout=findViewById(R.id.woman_layout);
        address_layout=findViewById(R.id.address_layout);
        woManLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select(view);
            }
        });
        man_layout=findViewById(R.id.man_layout);
        man_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select(view);
            }
        });
        man_icon=findViewById(R.id.man_icon);
        woman_icon=findViewById(R.id.woman_icon);
        big_txt_layout=findViewById(R.id.big_txt_layout);
        big_input_txt=(EditText)findViewById(R.id.big_input_txt);
        big_txt_layout.setVisibility(View.GONE);
        address_layout.setVisibility(View.GONE);
        sp_provice=(Spinner) findViewById(R.id.sp_province);
        sp_city=(Spinner) findViewById(R.id.sp_city);
        switch (changeType){
            case NAME:
                abilityToolBar.setTitle(getString(R.string.my_name_change));
                break;
            case EMAIL:
                abilityToolBar.setTitle(getString(R.string.my_email_change));
                break;
            case ADDRESS:
                abilityToolBar.setTitle(getString(R.string.my_address_change));
                address_layout.setVisibility(View.VISIBLE);
                txt_layout.setVisibility(View.GONE);
                GetProvinceList();
                break;
            case SEX:
                abilityToolBar.setTitle(getString(R.string.my_sex_change));
                select_layout.setVisibility(View.VISIBLE);
                txt_layout.setVisibility(View.GONE);
                break;
            case SIGNATURE:
                abilityToolBar.setTitle(getString(R.string.my_signature_change));
                big_txt_layout.setVisibility(View.VISIBLE);
                txt_layout.setVisibility(View.GONE);
                break;
            default:
        }
    }

    public void select(View view){
        int id=view.getId();
        switch (id){
            case R.id.man_layout:
                select= Constant.SexType.MAN;
                man_icon.setVisibility(View.VISIBLE);
                woman_icon.setVisibility(View.GONE);
                break;
            case R.id.woman_layout:
                select= Constant.SexType.WOMAN;
                man_icon.setVisibility(View.GONE);
                woman_icon.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void updateUserInfo(){
        if (changeType==Constant.ChangeType.EMAIL){
            String email=input_txt.getText().toString();
            if (!PatternUtil.isEmail(email)){
                BaseApplication.Instance.showToast("邮箱格式输入不正确");
                return;
            }
        }
        if (changeType==Constant.ChangeType.ADDRESS){
            if (currentProvince==null){
                BaseApplication.Instance.showToast("无法省市，请检查网络");
            }
            if (currentCityList==null){
                BaseApplication.Instance.showToast("请选择城市");
            }
        }
        Intent intent=new Intent();
        intent.putExtra(Constant.CHANGE_TXT,input_txt.getText().toString());
        intent.putExtra(Constant.SEX,select);
        intent.putExtra(Constant.CHANGE_TXT_BIG,big_input_txt.getText().toString());
        intent.putExtra(Constant.CHANGETYPE, changeType);
        intent.putExtra(Constant.CHANGE_PROVINCE, currentProvince);
        intent.putExtra(Constant.CHANGE_CITY, currentCityList);
        Logger.d("ChangeActivity",new Gson().toJson(currentProvince));
        Logger.d("ChangeActivity",new Gson().toJson(currentCityList));
        setResult(RESULT_OK,intent);
        finish();
    }

    Dialog dialog;
    public void GetProvinceList(){
        dialog=DialogUtils.getLoadingDialog(this,"正在加载省份列表");
        payAction.GetProvinceList()
                .subscribe(new RxSubscribe<ProvinceList>() {
                    @Override
                    public void onError(String message) {
                        DialogUtils.fail(dialog,"加载失败，"+message);
                    }

                    @Override
                    public void onNext(ProvinceList list) {
                        DialogUtils.success(dialog);
                        if (list!=null){
                            setProvinceList(list.getProvinceList());
                        }else {
                            setProvinceList(null);
                        }
                    }
                });
    }
    public void GetCityListByProvinceID(String province){
        dialog=DialogUtils.getLoadingDialog(this,"正在加载城市列表");
        payAction.GetCityListByProvinceID(province)
                .subscribe(new RxSubscribe<CityList>() {
                    @Override
                    public void onError(String message) {
                        DialogUtils.fail(dialog,"加载失败，"+message);
                    }

                    @Override
                    public void onNext(CityList list) {
                        DialogUtils.success(dialog);
                        if (list!=null){
                            setCityList(list.getProvinceList());
                        }else {
                            setCityList(null);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.destroy(dialog);
    }

    public void setProvinceList(final List<ProvinceList.Data> datas) {
        if (datas==null)
            return;
        currentProvince=datas.get(0);
        ArrayAdapter<ProvinceList.Data> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_provice.setAdapter(adapter);
        sp_provice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                currentProvince=datas.get(position);
                GetCityListByProvinceID(datas.get(position).getAddressID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        GetCityListByProvinceID(datas.get(0).getAddressID());
    }

    public void setCityList(final List<CityList.Data> datas) {
        if (datas==null)
            return;
        ArrayAdapter<CityList.Data> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_city.setAdapter(adapter);
        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                currentCityList=datas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        currentCityList=datas.get(0);
    }
}
