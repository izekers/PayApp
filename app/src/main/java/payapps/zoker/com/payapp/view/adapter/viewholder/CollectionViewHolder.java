package payapps.zoker.com.payapp.view.adapter.viewholder;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zekers.ui.view.recycler.VisitableViewHolder;
import com.zekers.utils.logger.Logger;

import java.text.DecimalFormat;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.model.Collection;
import payapps.zoker.com.payapp.model.Details;
import payapps.zoker.com.payapp.model.Goods;
import payapps.zoker.com.payapp.view.Activity.CollectionActivity;
import payapps.zoker.com.payapp.view.Activity.CollectionGoodsList;
import payapps.zoker.com.payapp.view.Activity.OrderCheckActivity;
import payapps.zoker.com.payapp.view.Activity.OrderPayActivity;
import payapps.zoker.com.payapp.view.Activity.OrderUpdateActivity;
import payapps.zoker.com.payapp.view.Activity.PaymentGoodsList;
import payapps.zoker.com.payapp.view.Constant;

/**
 * Created by Zoker on 2017/3/2.
 */
//收
public class CollectionViewHolder extends VisitableViewHolder<Collection> {
    private TextView item_time, item_state, item_goods_num, item_amount, btn_more;
    private LinearLayout item_pay_record;
    private Button collection_immediately, order_update, order_check;
    private View list_2,buff,latyout_cancle,latyout_ime;
    private TextView list_1_name, list_1_value, list_2_name, list_2_value;

    public CollectionViewHolder(final View itemView) {
        super(itemView);
        itemView.findViewById(R.id.payment_menu).setVisibility(View.GONE);
        item_time = (TextView) itemView.findViewById(R.id.item_time);
        item_state = (TextView) itemView.findViewById(R.id.item_state);
        item_goods_num = (TextView) itemView.findViewById(R.id.item_goods_num);
        item_amount = (TextView) itemView.findViewById(R.id.item_amount);
        item_pay_record = (LinearLayout) itemView.findViewById(R.id.item_pay_record);
        collection_immediately = (Button) itemView.findViewById(R.id.collection_immediately);
        order_update = (Button) itemView.findViewById(R.id.order_update);
        order_check = (Button) itemView.findViewById(R.id.order_check);
        list_1_name = (TextView) itemView.findViewById(R.id.list_1_name);
        list_1_value = (TextView) itemView.findViewById(R.id.list_1_value);
        list_2_name = (TextView) itemView.findViewById(R.id.list_2_name);
        list_2_value = (TextView) itemView.findViewById(R.id.list_2_value);
        list_2 = itemView.findViewById(R.id.list_2);
        btn_more = (TextView) itemView.findViewById(R.id.btn_more);
        buff=itemView.findViewById(R.id.buff2);
        latyout_cancle=itemView.findViewById(R.id.latyout_cancle2);
        latyout_ime=itemView.findViewById(R.id.latyout_ime2);
    }

    @Override
    public void setUpView(final Collection model, int position, RecyclerView.Adapter adapter) {
        order_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), OrderCheckActivity.class);
                intent.putExtra(Constant.ORDER_ID, model.getOrderID());
                intent.putExtra(Constant.TRAD_TYPE, Constant.Trad_Type.COLLECTION);
                itemView.getContext().startActivity(intent);
            }
        });
        order_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), CollectionActivity.class);
                intent.putExtra("collection",model);
                intent.putExtra(Constant.ORDER_ID, model.getOrderID());
                itemView.getContext().startActivity(intent);
            }
        });
        collection_immediately.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), OrderPayActivity.class);
                intent.putExtra(Constant.ORDER_ID, model.getOrderID());
                itemView.getContext().startActivity(intent);
            }
        });
        initView(model);
    }

    private void initView(final Collection model) {
        item_time.setText(model.getOrderTime() + " 付款人：" + model.getPayUserName());
        float cost = 0;
        for (Goods goods : model.getOrderProductList()) {
            cost = cost + goods.getCost();
        }
        DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p=decimalFormat.format(cost);//format 返回的是字符串
        Logger.e("payViewHolder","const int="+p);
        item_amount.setText("" + p+"元");
        if (model.getOrderProductList() == null || model.getOrderProductList().isEmpty()) {
            item_goods_num.setText("共0件商品 合计");
        } else {
            item_goods_num.setText("共" + model.getOrderProductList().size() + "件商品 合计");
        }
        int size = 0;
        if (model.getOrderProductList() != null){
            size = model.getOrderProductList().size();
            list_2.setVisibility(View.GONE);
            Goods goods1=model.getOrderProductList().get(0);
            list_1_name.setText(goods1.getProductName());
            list_1_value.setText(Goods.decaima(goods1.getCost())+"元");
            if (size > 1){
                list_2.setVisibility(View.VISIBLE);
                Goods goods2=model.getOrderProductList().get(1);
                list_2_name.setText(goods2.getProductName());
                list_2_value.setText(Goods.decaima(goods2.getCost())+"元");
            }
        }
        if (size > 2)
            btn_more.setTextColor(itemView.getContext().getResources().getColor(R.color.kv_itemview_name_textcolor));
        else {
            btn_more.setTextColor(itemView.getContext().getResources().getColor(R.color.gray_f2));
        }

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(itemView.getContext(),PaymentGoodsList.class);
//                intent.putExtra("collection",model);
//                intent.putExtra("payType","collection");
//                itemView.getContext().startActivity(intent);
                Intent intent = new Intent(itemView.getContext(), OrderCheckActivity.class);
                intent.putExtra(Constant.ORDER_ID, model.getOrderID());
                intent.putExtra(Constant.TRAD_TYPE, Constant.Trad_Type.COLLECTION);
                itemView.getContext().startActivity(intent);
            }
        });

        switch (model.getOrderStatus()) {
            case 2:
                item_state.setTextColor(itemView.getContext().getResources().getColor(R.color.green));
                item_state.setText("已收款");
                latyout_cancle.setVisibility(View.GONE);
                latyout_ime.setVisibility(View.GONE);
                buff.setVisibility(View.VISIBLE);
                break;
            case 1:
                item_state.setTextColor(itemView.getContext().getResources().getColor(R.color.strong_color));
                item_state.setText("待收款");
                latyout_cancle.setVisibility(View.VISIBLE);
                latyout_ime.setVisibility(View.VISIBLE);
                buff.setVisibility(View.GONE);
                break;
            case 3:
                item_state.setTextColor(itemView.getContext().getResources().getColor(R.color.gray_6));
                item_state.setText("已取消");
                latyout_cancle.setVisibility(View.GONE);
                latyout_ime.setVisibility(View.GONE);
                buff.setVisibility(View.VISIBLE);
                break;
        }
    }
}
