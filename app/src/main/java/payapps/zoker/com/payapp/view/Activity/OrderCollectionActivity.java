package payapps.zoker.com.payapp.view.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.zekers.network.base.RxSubscribe;
import com.zekers.ui.view.recycler.RecyclerWrapView;
import com.zekers.ui.view.widget.AbilityToolBar;

import java.util.List;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.Details;
import payapps.zoker.com.payapp.model.User;
import payapps.zoker.com.payapp.view.Constant;
import payapps.zoker.com.payapp.view.adapter.GoodsAdapter;
import payapps.zoker.com.payapp.view.adapter.OrderTypeFactory;

/**
 * Created by Administrator on 2017/3/4.
 */

public class OrderCollectionActivity extends BaseActivity {
    AbilityToolBar abilityToolBar;
    String order_id;
    PayAction payAction = new PayAction();
    Details currentPay;
    private TextView account_view, name_view, company_view;
    private RecyclerWrapView order_update_list;
    private GoodsAdapter goodsAdapter;

    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_collection);
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.payment_order_pay));
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        account_view = (TextView) findViewById(R.id.account_view);
        name_view = (TextView) findViewById(R.id.name_view);
        company_view = (TextView) findViewById(R.id.company_view);

        order_update_list = (RecyclerWrapView) findViewById(R.id.order_update_list);
        order_update_list.setLayoutManager(new LinearLayoutManager(this));
        goodsAdapter = new GoodsAdapter(OrderTypeFactory.getInstance());
        order_update_list.setAdapter(goodsAdapter);

        order_id = getIntent().getStringExtra(Constant.ORDER_ID);
        GetPayOrderDetail(order_id);
        user=UserRecord.getInstance().load();
        if (user.getIdCardAuth()==0||user.getIdCardAuth()==3 )
            showDialogMsg("提示","付款前请先上传身份证验证并绑定银行卡");
    }

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
                        if (s.getUser() != null)
                            company_view.setText(s.getUser().getCompanyName());
                        goodsAdapter.setmDatas(s.getOrderProductList());
                        order_update_list.getmAdapter().notifyDataSetChanged();
                    }
                });
    }
}
