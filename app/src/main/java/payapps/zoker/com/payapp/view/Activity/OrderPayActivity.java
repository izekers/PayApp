package payapps.zoker.com.payapp.view.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zekers.network.base.RxSubscribe;
import com.zekers.ui.view.recycler.RecyclerWrapView;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.logger.Logger;

import payapps.zoker.com.payapp.QRCodeUtil;
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

public class OrderPayActivity extends BaseActivity{
    AbilityToolBar abilityToolBar;
    String order_id;
    PayAction payAction=new PayAction();
    ImageView qr_img;
    TextView account_view,company,time_view,address_view;
    private RecyclerView order_update_list;
    private GoodsAdapter goodsAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        abilityToolBar=(AbilityToolBar)findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.collection_order_pay));
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        account_view=(TextView)findViewById(R.id.account_view);
        company=(TextView)findViewById(R.id.company);
        time_view=(TextView)findViewById(R.id.time_view);
        address_view=(TextView)findViewById(R.id.address_view);
        qr_img=(ImageView)findViewById(R.id.qr_img);

        order_id = getIntent().getStringExtra(Constant.ORDER_ID);
        order_update_list=(RecyclerView) findViewById(R.id.order_update_list);
        order_update_list.setLayoutManager(new LinearLayoutManager(this));
        goodsAdapter=new GoodsAdapter(OrderTypeFactory.getInstance());
        order_update_list.setAdapter(goodsAdapter);
        GetOrderDetail(order_id);
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
                        account_view.setText("￥ "+s.getAmount());
                        company.setText(s.getUser().getCompanyName());
                        time_view.setText(s.getOrderTime());
                        address_view.setText(s.getUser().getBusinessAddress());
                        qr_img.setImageBitmap(QRCodeUtil.createQRImage(s.getOrderID()+"&"+s.getPayUserID(),300,300));
                        Logger.d("test","test="+s.getOrderProductList());
                        goodsAdapter.setmDatas(s.getOrderProductList());
//                        order_update_list.notifyDataSetChanged();
                    }
                });
    }
}
