package payapps.zoker.com.payapp.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zekers.network.base.RxSubscribe;
import com.zekers.ui.view.recycler.RecyclerWrapView;
import com.zekers.ui.view.widget.AbilityToolBar;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.Collection;
import payapps.zoker.com.payapp.model.Goods;
import payapps.zoker.com.payapp.view.Constant;
import payapps.zoker.com.payapp.view.adapter.GoodsAdapter;
import payapps.zoker.com.payapp.view.adapter.OrderTypeFactory;

/**
 * Created by Administrator on 2017/3/4.
 */

public class OrderUpdateActivity extends BaseActivity {
    AbilityToolBar abilityToolBar;
    PayAction payAction;
    String order_id;
    private TextView account_view,name_view,id_view;
    private RecyclerWrapView order_update_list;
    private GoodsAdapter goodsAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_update);
        payAction=new PayAction();
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.collection_order_update));
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
                        finish();
                        break;
                }
            }
        });
        findViewById(R.id.id_layout).setVisibility(View.GONE);
        account_view=(TextView)findViewById(R.id.account_view);
        name_view=(TextView)findViewById(R.id.name_view);
        id_view=(TextView)findViewById(R.id.id_view);
        order_id = getIntent().getStringExtra(Constant.ORDER_ID);
        order_update_list=(RecyclerWrapView) findViewById(R.id.order_update_list);
        order_update_list.setLayoutManager(new LinearLayoutManager(this));
        goodsAdapter=new GoodsAdapter(OrderTypeFactory.getInstance());
        order_update_list.addFooderView(footer());
        order_update_list.setAdapter(goodsAdapter);
        GetOrderDetail(order_id);
    }

    Collection currentCollection;

    private View footer() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_add_goods_menu, null);
        view.findViewById(R.id.add_goods)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentCollection != null) {
                            Intent intent = new Intent(OrderUpdateActivity.this, GoodsAddToOrderActivity.class);
                            intent.putExtra(Constant.ORDER_ID, currentCollection.getOrderID());
                            startActivity(intent);
                        }
                    }
                });
        view.findViewById(R.id.ok)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
        return view;
    }

    private void GetOrderDetail(String Order_id){
        if (Order_id==null)
            return;
        payAction.GetOrderDetail(order_id)
                .subscribe(new RxSubscribe<Collection>() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onNext(Collection s) {
                        currentCollection=s;
                        account_view.setText(s.getAmount());
                        name_view.setText(s.getPayUserName());
                        id_view.setText(s.getOrderNo());

                        goodsAdapter.setmDatas(s.getOrderProductList());
                        order_update_list.getmAdapter().notifyDataSetChanged();
                    }
                });
    }
}
