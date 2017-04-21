package payapps.zoker.com.payapp.view.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.ui.view.recycler.RecyclerWrapAdapter;
import com.zekers.ui.view.recycler.RecyclerWrapView;
import com.zekers.ui.view.recycler.VisitableViewHolder;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.Collection;
import payapps.zoker.com.payapp.model.Details;
import payapps.zoker.com.payapp.view.Constant;
import payapps.zoker.com.payapp.view.adapter.GoodsAdapter;
import payapps.zoker.com.payapp.view.adapter.ShowTypeFactory;

/**
 * Created by Administrator on 2017/3/25.
 */

public class PaymentGoodsList extends BaseActivity {
    AbilityToolBar abilityToolBar;
    String type;
    Details details;
    Collection collection;
    RecyclerView rv_list;
    GoodsAdapter goodsAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_goods);

        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle("所有商品");
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rv_list=(RecyclerView)findViewById(R.id.rv_list);
        goodsAdapter=new GoodsAdapter(ShowTypeFactory.getInstance());
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setAdapter(goodsAdapter);
        type = getIntent().getStringExtra("payType");
        if (type.equals("pay")) {
            details = (Details) getIntent().getSerializableExtra("pay");
            Logger.d("Payment",new Gson().toJson(details));
            initPayView();
        } else if (type.equals("collection")) {
            collection = (Collection) getIntent().getSerializableExtra("collection");
            Logger.d("Payment",new Gson().toJson(collection));
            initCollectionView();
        }
    }

    Button pay_immediately, order_pay_check, order_cancle;

    private void initPayView() {
        findViewById(R.id.collection_menu).setVisibility(View.GONE);
        pay_immediately = (Button) findViewById(R.id.pay_immediately);
        order_pay_check = (Button) findViewById(R.id.order_pay_check);
        order_cancle = (Button) findViewById(R.id.order_cancle);
        if (details == null)
            return;
        goodsAdapter.setmDatas(details.getOrderProductList());
        order_pay_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentGoodsList.this, OrderCheckActivity.class);
                intent.putExtra(Constant.ORDER_ID, details.getOrderID());
                intent.putExtra(Constant.TRAD_TYPE, Constant.Trad_Type.PAY);
                startActivity(intent);
            }
        });
        order_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectMsg("确定删除订单", new SelectCallBack() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok() {
                        final Dialog dialog = DialogUtils.getLoadingDialog(PaymentGoodsList.this, "正在删除");
                        new PayAction().CancelPayOrder(details.getOrderID())
                                .subscribe(new RxSubscribe<DataWrapper>() {
                                    @Override
                                    public void onError(String message) {
                                        DialogUtils.fail(dialog, "删除失败，" + message);
                                    }

                                    @Override
                                    public void onNext(DataWrapper s) {
                                        DialogUtils.success(dialog);
                                        RxBus.getInstance().send(Events.FRESH_PAYLIST, null);
                                    }
                                });
                    }
                });
            }
        });
        pay_immediately.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentGoodsList.this, OrderCollectionActivity.class);
                intent.putExtra(Constant.ORDER_ID, details.getOrderID());
                startActivity(intent);
            }
        });

    }

    private Button collection_immediately, order_update, order_check;

    private void initCollectionView() {
        findViewById(R.id.payment_menu).setVisibility(View.GONE);
        collection_immediately = (Button) findViewById(R.id.collection_immediately);
        order_update = (Button) findViewById(R.id.order_update);
        order_check = (Button) findViewById(R.id.order_check);
        if (collection == null)
            return;
        goodsAdapter.setmDatas(collection.getOrderProductList());
        order_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentGoodsList.this, OrderCheckActivity.class);
                intent.putExtra(Constant.ORDER_ID, collection.getOrderID());
                intent.putExtra(Constant.TRAD_TYPE, Constant.Trad_Type.COLLECTION);
                startActivity(intent);
            }
        });
        order_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentGoodsList.this, OrderUpdateActivity.class);
                intent.putExtra(Constant.ORDER_ID, collection.getOrderID());
                startActivity(intent);
            }
        });
        collection_immediately.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentGoodsList.this, OrderPayActivity.class);
                intent.putExtra(Constant.ORDER_ID, collection.getOrderID());
                startActivity(intent);
            }
        });
    }
}
