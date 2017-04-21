package payapps.zoker.com.payapp.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zekers.network.base.RxSubscribe;
import com.zekers.ui.view.recycler.VisitableTypeControl;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;

import java.util.ArrayList;
import java.util.List;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.Details;
import payapps.zoker.com.payapp.model.Goods;
import payapps.zoker.com.payapp.model.ProductList;
import payapps.zoker.com.payapp.model.ProvinceList;
import payapps.zoker.com.payapp.model.Records;
import payapps.zoker.com.payapp.view.adapter.GoodsAdapter;
import payapps.zoker.com.payapp.view.adapter.PayTypeFactory;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/3/4.
 */

public class GoodsActivity extends BaseActivity{
    AbilityToolBar abilityToolBar;
    RecyclerView recyclerView;
    GoodsAdapter goodsAdapter;
    PayAction payAction;
    int pagIndex=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        RxBus.getSubscriber().setEvent(Events.FRESH_GOODLIST)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        pagIndex=1;
                        GetProductList();
                    }
                })
        .create();

        abilityToolBar=(AbilityToolBar)findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.commodity_management));
        abilityToolBar.setMenuRes(R.menu.goods_menu);
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
                    case R.id.add:
                        Intent intent = new Intent(GoodsActivity.this, GoodsAddActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        recyclerView=(RecyclerView) findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        goodsAdapter=new GoodsAdapter(PayTypeFactory.getInstance());
        recyclerView.setAdapter(goodsAdapter);
        payAction=new PayAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetProductList();
    }

    public void GetProductList(){
        payAction.GetProductList(pagIndex)
                .subscribe(new RxSubscribe<ProductList>() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onNext(ProductList s) {
                        if (s==null)
                            goodsAdapter.setmDatas(null);
                        else
                            goodsAdapter.setmDatas(s.getProductList());
                    }
                });
    }
}
