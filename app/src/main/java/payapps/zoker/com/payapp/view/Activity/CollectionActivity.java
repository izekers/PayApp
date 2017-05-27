package payapps.zoker.com.payapp.view.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.GsonUtils;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import payapps.zoker.com.payapp.PayApplication;
import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.contract.AddGoodsContract;
import payapps.zoker.com.payapp.control.presenter.AddGoodsPresenter;
import payapps.zoker.com.payapp.model.Collection;
import payapps.zoker.com.payapp.model.ContactMan;
import payapps.zoker.com.payapp.model.FirstList;
import payapps.zoker.com.payapp.model.Goods;
import payapps.zoker.com.payapp.model.SecondList;
import payapps.zoker.com.payapp.view.adapter.BeSelectAdapter;

/**
 * Created by Administrator on 2017/3/4.
 */

public class CollectionActivity extends BaseActivity implements AddGoodsContract.View {
    AbilityToolBar abilityToolBar;
    PayAction payAction;
    EditText phone, name, goods_num, goods_price, goods_name,goods_unit;
    AddGoodsContract.Presenter presenter = new AddGoodsPresenter();
    TextView goods_account;
    Spinner sp_first, sp_second,sp_goods;
    FirstList.Data currentFirst;
    SecondList.Data currentSecond = new SecondList.Data();
    View goods_menu;
    View add_book_select;
    int addbooklist_request = 2;
    int goodslist_rquest = 1;
    ListView be_select_goodslist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        presenter.attachView(this);
        payAction = new PayAction();
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.collection_order_add));
        abilityToolBar.setMenuRes(R.menu.collection_new);

        be_select_goodslist=(ListView)findViewById(R.id.be_select_goodslist);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        goods_num = (EditText) findViewById(R.id.goods_num);
        goods_price = (EditText) findViewById(R.id.goods_price);
        goods_name = (EditText) findViewById(R.id.goods_name);
        goods_account = (TextView) findViewById(R.id.goods_account);
        goods_unit=(EditText) findViewById(R.id.goods_unit);
        goods_num.addTextChangedListener(textWatcher);
        goods_price.addTextChangedListener(textWatcher);

        sp_second = (Spinner) findViewById(R.id.sp_second);
        sp_first = (Spinner) findViewById(R.id.sp_first);
        sp_goods = (Spinner) findViewById(R.id.sp_goodslist);
        sp_goods.setAdapter(adapter);
        be_select_goodslist.setAdapter(adapter);
        sp_goods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sp_goods.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        goods_menu = findViewById(R.id.goods_menu);
        goods_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollectionActivity.this, GoodsOrderSelectActivity.class);
                startActivityForResult(intent, goodslist_rquest);
            }
        });

        add_book_select = findViewById(R.id.add_book_select);
        add_book_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollectionActivity.this, ContactSelectActivity.class);
                startActivityForResult(intent, addbooklist_request);
            }
        });
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beSelectGoods=new ArrayList<Goods>();
                finish();
            }
        });
        abilityToolBar.setOnMenuItemClickListener(new AbilityToolBar.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.ok:
                        List<Goods> goodsList = new ArrayList<Goods>();
                        if (!goods_name.getText().toString().equals("")  && !goods_price.getText().toString().equals("") &&
                                !goods_num.getText().toString().equals("") ){
                            Goods goods = new Goods();
                            goods.setProductName(goods_name.getText().toString());
                            goods.setPrice(Float.valueOf(goods_price.getText().toString()));
                            goods.setQuantity(Integer.valueOf(goods_num.getText().toString()));
                            goods.setCost(Float.valueOf(goods_account.getText().toString()));
                            goods.setUnit(goods_unit.getText().toString());
                            goodsList.add(goods);
                        }
                        if (beSelectGoods != null) {
                            goodsList.addAll(beSelectGoods);
//                            for (Goods sss : beSelectGoods) {
//                                Goods bean = new Goods();
//                                bean.setProductName(sss.getProductName());
//                                bean.setPrice(sss.getPrice());
//                                bean.setQuantity(sss.getQuantity());
//                                bean.setCost(sss.getCost());
//                                goodsList.add(bean);
//                            }
                        }
                        Logger.d("payment", new Gson().toJson(goodsList));
                        AddOrder(name.getText().toString(), phone.getText().toString(), currentFirst.getCateID(), currentSecond.getCateID(), new Gson().toJson(goodsList));
                        break;
                }
            }
        });
        Collection collection=(Collection) getIntent().getSerializableExtra("collection");
        if (collection!=null)
            initData(collection);
        presenter.GetFirstList();
    }

    private void initData(Collection collection){
        name.setText(collection.getPayUserName());
        phone.setText(collection.getPayMobilephone());
        beSelectGoods=collection.getOrderProductList();
        setGoodsSp(collection.getOrderProductList());
        float cost=0;
        if(beSelectGoods!=null){
            for (Goods goods:beSelectGoods){
                cost=cost+goods.getCost();
            }
            goods_account.setText("" + Goods.decaima(cost)+"元");
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
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
            float price = 0;
            float cost = 0;
            int number = 0;

            try {
                price = Float.valueOf(goods_price.getText().toString());
                number = Integer.valueOf(goods_num.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (price != 0 && number != 0) {
                try {
                    cost=0;
                    if (beSelectGoods!=null){
                        for (Goods goods:beSelectGoods){
                            cost=cost+goods.getCost();
                        }
                    }
                    cost =cost+ price * number;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                goods_account.setText("" + Goods.decaima(cost)+"元");
            }
        }
    };
    Dialog dialog;

    protected void AddOrder(String PayUserName, String PayMobilephone, String FirstCateID, String SecondCateID, String OrderProductJson) {
        dialog = DialogUtils.getLoadingDialog(CollectionActivity.this, "正在添加");
        payAction.AddOrder(PayUserName, PayMobilephone, FirstCateID, SecondCateID, OrderProductJson)
                .subscribe(new RxSubscribe<Object>() {
                    @Override
                    public void onError(String message) {
                        DialogUtils.fail(dialog, "添加失败，" + message);
                    }

                    @Override
                    public void onNext(Object s) {
                        DialogUtils.success(dialog, "添加成功");
                        RxBus.getInstance().send(Events.FRESH_COLLECTIONLIST,null);
                        beSelectGoods = null;
                        finish();
                    }
                });
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
        beSelectGoods = null;
        finish();
    }

    @Override
    public void fail(String msg) {
        DialogUtils.fail(dialog, "失败，" + msg);
    }

    public void setFirstList(final List<FirstList.Data> firstList) {
        if (firstList == null)
            return;
        ArrayAdapter<FirstList.Data> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, firstList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_first.setAdapter(adapter);
        sp_first.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                currentFirst = firstList.get(position);
                presenter.GetSecondList(firstList.get(position).getCateID());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        currentFirst = firstList.get(0);
        presenter.GetSecondList(firstList.get(0).getCateID());
    }

    public void setSecondList(final List<SecondList.Data> secondList) {
        Logger.d("1231", new Gson().toJson(secondList));
        if (secondList == null)
            return;
        ArrayAdapter<SecondList.Data> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, secondList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_second.setAdapter(adapter);
        sp_second.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                currentSecond = secondList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        currentSecond = secondList.get(0);
    }

    BeSelectAdapter adapter = new BeSelectAdapter();
    public void setGoodsSp(List<Goods> goodsSp){
        float cost=0;
        if(beSelectGoods!=null){
            for (Goods goods:beSelectGoods){
                cost=cost+goods.getCost();
            }
            goods_account.setText("" + Goods.decaima(cost)+"元");
        }
        Logger.d("1231", new Gson().toJson(goodsSp));
        adapter.setData(goodsSp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == goodslist_rquest) {
                setGoodsSp(beSelectGoods);
            } else if (requestCode == addbooklist_request) {
                ContactMan contactMan = (ContactMan) data.getSerializableExtra("contactmanSelect");
                Logger.d("ContactSelectActivity", new Gson().toJson(contactMan));
                if (contactMan != null) {
                    name.setText(contactMan.getUserName());
                    phone.setText(contactMan.getMobilephone());
                }
            }
        }
    }
}
