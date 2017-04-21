package payapps.zoker.com.payapp.view.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.logger.Logger;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.ProductList;
import payapps.zoker.com.payapp.view.adapter.GoodsSelectAdapter;
import payapps.zoker.com.payapp.view.adapter.SelectTypeFactory;

/**
 * Created by Administrator on 2017/3/21.
 */

public class GoodsSelectActivity extends BaseActivity {
    AbilityToolBar abilityToolBar;
    RecyclerView rv_list;
    GoodsSelectAdapter adapter;
    int pagIndex;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_goods);
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle("选择商品");
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
                if (view.getId()==R.id.ok){
                    Logger.d("GoodsSelectActivity",new Gson().toJson(adapter.getSelectGoods()));
                    finish();
                }
            }
        });
        rv_list=(RecyclerView)findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        adapter=new GoodsSelectAdapter(SelectTypeFactory.getInstance());
        rv_list.setAdapter(adapter);
        GetProductList();
    }

    PayAction payAction=new PayAction();
    public void GetProductList(){
        payAction.GetProductList(pagIndex)
                .subscribe(new RxSubscribe<ProductList>() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onNext(ProductList s) {
                        if (s==null)
                            adapter.setmDatas(null);
                        else
                            adapter.setmDatas(s.getProductList());
                    }
                });
    }
}
