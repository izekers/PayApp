package payapps.zoker.com.payapp.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.pjutils.BaseApplication;
import com.zekers.ui.view.recycler.RecyclerWrapAdapter;
import com.zekers.ui.view.recycler.RecyclerWrapView;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.Collection;
import payapps.zoker.com.payapp.model.Details;
import payapps.zoker.com.payapp.model.Goods;
import payapps.zoker.com.payapp.view.Constant;
import payapps.zoker.com.payapp.view.adapter.GoodsAdapter;
import payapps.zoker.com.payapp.view.adapter.OrderTypeFactory;
import payapps.zoker.com.payapp.view.adapter.viewholder.OrderGoodsViewHolder;

/**
 * Created by Administrator on 2017/3/4.
 */

public class OrderCheckActivity extends BaseActivity {
    AbilityToolBar abilityToolBar;
    String order_id;
    Constant.Trad_Type trad_type;
    PayAction payAction;
    private TextView account_view, name_view, id_view;
    private RecyclerWrapView order_update_list;
    private GoodsAdapter goodsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_check);
        payAction = new PayAction();
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.collection_order_check));
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        order_id = getIntent().getStringExtra(Constant.ORDER_ID);
        trad_type = (Constant.Trad_Type) getIntent().getSerializableExtra(Constant.TRAD_TYPE);
        account_view = (TextView) findViewById(R.id.account_view);
        name_view = (TextView) findViewById(R.id.name_view);
        id_view = (TextView) findViewById(R.id.id_view);
        switch (trad_type) {
            case PAY:
                abilityToolBar.setTitle("付款单详情");
                GetPayOrderDetail(order_id);
                break;
            case COLLECTION:
                GetOrderDetail(order_id);
                break;
        }
        order_update_list = (RecyclerWrapView) findViewById(R.id.order_update_list);
        order_update_list.setLayoutManager(new LinearLayoutManager(this));
        goodsAdapter = new GoodsAdapter(OrderTypeFactory.getInstance());
        footView=footer(trad_type);
        order_update_list.addFooderView(footView);
        order_update_list.setAdapter(goodsAdapter);
    }
    View footView;
    private View footer(Constant.Trad_Type trad_type) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_collection_check_menu, null);
        switch (trad_type) {
            case PAY:
                view.findViewById(R.id.order_update).setVisibility(View.GONE);
                View view1 = view.findViewById(R.id.order_cancle);
                ((RelativeLayout.LayoutParams) view1.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                Button view2 = (Button) view.findViewById(R.id.collection_immediately);
                view2.setText("现在付款");
                view2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(OrderCheckActivity.this, OrderCollectionActivity.class);
                        intent.putExtra(Constant.ORDER_ID, currentPay.getOrderID());
                        startActivity(intent);
                    }
                });
                break;
            case COLLECTION:
                view.findViewById(R.id.order_update).setVisibility(View.GONE);

                View view3 = view.findViewById(R.id.order_cancle);
                ((RelativeLayout.LayoutParams) view3.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);

                view.findViewById(R.id.order_cancle)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (currentCollection != null) {
                                    CancelOrder(currentCollection.getOrderID());
                                }
                            }
                        });

                view.findViewById(R.id.collection_immediately)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (currentCollection != null) {
                                    Intent intent = new Intent(OrderCheckActivity.this, OrderPayActivity.class);
                                    intent.putExtra(Constant.ORDER_ID, currentCollection.getOrderID());
                                    startActivity(intent);
                                }
                            }
                        });
                break;
        }
        return view;
    }

    Collection currentCollection;
    Details currentPay;

    private void GetOrderDetail(String Order_id) {
        if (Order_id == null)
            return;
        payAction.GetOrderDetail(order_id)
                .subscribe(new RxSubscribe<Collection>() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onNext(Collection s) {
                        currentCollection = s;
                        account_view.setText(s.getAmount());
                        name_view.setText(s.getPayUserName());
                        id_view.setText(s.getOrderNo());

                        goodsAdapter.setmDatas(s.getOrderProductList());
                        order_update_list.getmAdapter().notifyDataSetChanged();
                        if (s.getOrderStatus()==3){
                            footView.findViewById(R.id.collection_immediately).setVisibility(View.GONE);
                            footView.findViewById(R.id.order_cancle).setVisibility(View.GONE);
                        }
                    }
                });
    }

    Details details;

    private void GetPayOrderDetail(String Order_id) {
        if (Order_id == null)
            return;
        payAction.GetPayOrderDetail(order_id)
                .subscribe(new RxSubscribe<Details>() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onNext(Details s) {
                        currentPay = s;
                        account_view.setText(s.getAmount());
                        name_view.setText(s.getPayUserName());
                        id_view.setText(s.getOrderNo());

                        goodsAdapter.setmDatas(s.getOrderProductList());
                        order_update_list.getmAdapter().notifyDataSetChanged();

                        if (s.getOrderStatus()==3){
                            footView.findViewById(R.id.collection_immediately).setVisibility(View.GONE);
                            footView.findViewById(R.id.order_cancle).setVisibility(View.GONE);
                        }

                    }
                });
    }

    private void CancelOrder(final String OrderID) {
        showSelectMsg("确定取消订单?", new SelectCallBack() {
            @Override
            public void cancel() {

            }

            @Override
            public void ok() {
                payAction.CancelOrder(OrderID)
                        .subscribe(new RxSubscribe<DataWrapper>() {
                            @Override
                            public void onError(String message) {

                            }

                            @Override
                            public void onNext(DataWrapper s) {
                                BaseApplication.Instance.showToast("订单已取消");
                                RxBus.getInstance().send(Events.FRESH_COLLECTIONLIST,null);
                                finish();
                            }
                        });
            }
        });

    }
}
