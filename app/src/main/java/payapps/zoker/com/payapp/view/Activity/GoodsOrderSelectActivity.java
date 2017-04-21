package payapps.zoker.com.payapp.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.GsonUtils;
import com.zekers.utils.logger.Logger;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.Goods;
import payapps.zoker.com.payapp.model.ProductList;
import payapps.zoker.com.payapp.view.adapter.GoodsSelectAdapter;
import payapps.zoker.com.payapp.view.adapter.SelectTypeFactory;

/**
 * Created by Administrator on 2017/3/25.
 */

public class GoodsOrderSelectActivity extends BaseActivity{
    AbilityToolBar abilityToolBar;
    RecyclerView recyclerView;
    GoodsSelectAdapter goodsAdapter;
    int pagIndex=1;
    PayAction payAction=new PayAction();
    View more_goods;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_select_goods);

        abilityToolBar=(AbilityToolBar)findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle("选择商品");
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
                int id = view.getId();
                switch (id) {
                    case R.id.ok:
                        beSelectGoods=goodsAdapter.getSelectGoods();
                        setResult(RESULT_OK);
                        finish();
                        break;
                }
            }
        });
        recyclerView=(RecyclerView) findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        goodsAdapter=new GoodsSelectAdapter(SelectTypeFactory.getInstance());
        recyclerView.setAdapter(goodsAdapter);

        more_goods=findViewById(R.id.more_goods);
        more_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GoodsOrderSelectActivity.this,GoodsAddActivity.class);
                startActivity(intent);
            }
        });
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
                        Logger.d("123",new Gson().toJson(s));
                        if (s==null)
                            goodsAdapter.setmDatas(null);
                        else
                            goodsAdapter.setmDatas(s.getProductList());

                        goodsAdapter.setSelectGoods(beSelectGoods);
                    }
                });
    }
}
