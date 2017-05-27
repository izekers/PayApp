package payapps.zoker.com.payapp.view.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.zekers.network.base.RxSubscribe;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import java.util.List;

import payapps.zoker.com.payapp.PayApplication;
import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.contract.AddGoodsContract;
import payapps.zoker.com.payapp.control.presenter.AddGoodsPresenter;
import payapps.zoker.com.payapp.model.FirstList;
import payapps.zoker.com.payapp.model.SecondList;

/**
 * Created by Administrator on 2017/3/6.
 */

public class GoodsAddActivity extends BaseActivity implements AddGoodsContract.View {
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
        setContentView(R.layout.activity_add_goods);
        presenter = new AddGoodsPresenter();
        presenter.attachView(this);
        initView();
        initListener();
        presenter.GetFirstList();
    }

    public void initView() {
        goods_name = (EditText) findViewById(R.id.goods_name);
        goods_price = (EditText) findViewById(R.id.goods_price);
        goods_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String temp=editable.toString();
                int postDot=temp.indexOf(".");
                if (postDot>0){
                    if (temp.length() - postDot -1 >2){
                        editable.delete(postDot + 3,postDot +4) ;
                    }
                }
            }
        });
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
                        presenter.AddProduct(goods_name.getText().toString(),currentFirst.getCateID(),currentSecond.getCateID(),"1",goods_price.getText().toString(),goods_unit.getText().toString(),"","");
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
        dialog = DialogUtils.getLoadingDialog(this, "正在加载",true);
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
