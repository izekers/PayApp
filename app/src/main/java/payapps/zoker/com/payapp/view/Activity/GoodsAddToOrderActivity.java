package payapps.zoker.com.payapp.view.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.zekers.ui.view.widget.AbilityToolBar;
import com.zoker.base.DialogUtils;

import java.util.List;

import payapps.zoker.com.payapp.PayApplication;
import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.contract.AddGoodsContract;
import payapps.zoker.com.payapp.control.contract.BankCardAddContract;
import payapps.zoker.com.payapp.control.presenter.BankCardAddPresenter;
import payapps.zoker.com.payapp.model.FirstList;
import payapps.zoker.com.payapp.model.SecondList;

/**
 * Created by Administrator on 2017/3/22.
 */

public class GoodsAddToOrderActivity extends BaseActivity implements AddGoodsContract.View{
    AbilityToolBar abilityToolBar;
    AddGoodsContract.Presenter presenter;
    EditText goods_name, goods_price, goods_unit;
    Spinner sp_first, sp_second;
    Dialog dialog;

    FirstList.Data currentFirst;
    SecondList.Data currentSecond=new SecondList.Data();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankcard_add);
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.goods_add));
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initView();
        initListener();
        presenter.GetFirstList();
    }

    public void initView(){
        findViewById(R.id.goods_select).setVisibility(View.VISIBLE);
        goods_name = (EditText) findViewById(R.id.goods_name);
        goods_price = (EditText) findViewById(R.id.goods_price);
        goods_unit = (EditText) findViewById(R.id.goods_unit);
        sp_second = (Spinner) findViewById(R.id.sp_second);
        sp_first = (Spinner) findViewById(R.id.sp_first);
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.goods_add));
        abilityToolBar.setMenuRes(R.menu.ok_menu);
    }

    public void initListener() {
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        abilityToolBar.setOnMenuItemClickListener(new AbilityToolBar.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.ok:

                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void initData() {

    }

    @Override
    public void loading() {
        dialog = DialogUtils.getLoadingDialog(this, "正在加载");
    }

    @Override
    public void getFirstSucc(List<FirstList.Data> datas) {
        DialogUtils.success(dialog);
        setFirstList(datas);
    }

    @Override
    public void getSecondSucc(List<SecondList.Data> datas) {
        DialogUtils.success(dialog);
        setSecondList(datas);
    }

    @Override
    public void addProductSucc() {
        DialogUtils.success(dialog);
        PayApplication.Instance.showToast("添加成功");
        finish();
    }

    @Override
    public void fail(String msg) {
        DialogUtils.fail(dialog, "失败，" + msg);
    }

    public void setFirstList(final List<FirstList.Data> firstList) {
        if (firstList==null)
            return;
        ArrayAdapter<FirstList.Data> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, firstList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_first.setAdapter(adapter);
        sp_first.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                currentFirst=firstList.get(position);
                presenter.GetSecondList(firstList.get(position).getCateID());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        currentFirst=firstList.get(0);
        presenter.GetSecondList(firstList.get(0).getCateID());
    }

    public void setSecondList(final List<SecondList.Data> secondList) {
        if (secondList==null)
            return;
        ArrayAdapter<SecondList.Data> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, secondList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_second.setAdapter(adapter);
        sp_second.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        currentSecond=secondList.get(0);
    }
}