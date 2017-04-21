package payapps.zoker.com.payapp.view.adapter.viewholder;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.ui.view.recycler.VisitableViewHolder;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.Collection;
import payapps.zoker.com.payapp.model.Details;
import payapps.zoker.com.payapp.model.Goods;
import payapps.zoker.com.payapp.view.Activity.OrderCheckActivity;
import payapps.zoker.com.payapp.view.Activity.OrderCollectionActivity;
import payapps.zoker.com.payapp.view.Activity.OrderPayActivity;
import payapps.zoker.com.payapp.view.Activity.OrderUpdateActivity;
import payapps.zoker.com.payapp.view.Activity.PaymentGoodsList;
import payapps.zoker.com.payapp.view.Constant;

/**
 * Created by Zoker on 2017/3/2.
 */
//付
public class PayViewHolder extends VisitableViewHolder<Details> {
    TextView item_time, item_state, item_goods_num, item_amount ,btn_more;
    LinearLayout item_pay_record;
    Button pay_immediately, order_pay_check, order_cancle;
    private View list_2,buff,latyout_cancle,latyout_ime;
    private TextView list_1_name, list_1_value, list_2_name, list_2_value;

    public PayViewHolder(final View itemView) {
        super(itemView);
        itemView.findViewById(R.id.collection_menu).setVisibility(View.GONE);
        item_time = (TextView) itemView.findViewById(R.id.item_time);
        item_state = (TextView) itemView.findViewById(R.id.item_state);
        item_goods_num = (TextView) itemView.findViewById(R.id.item_goods_num);
        item_amount = (TextView) itemView.findViewById(R.id.item_amount);
        item_pay_record = (LinearLayout) itemView.findViewById(R.id.item_pay_record);
        pay_immediately = (Button) itemView.findViewById(R.id.pay_immediately);
        order_pay_check = (Button) itemView.findViewById(R.id.order_pay_check);
        order_cancle = (Button) itemView.findViewById(R.id.order_cancle);
        list_1_name = (TextView) itemView.findViewById(R.id.list_1_name);
        list_1_value = (TextView) itemView.findViewById(R.id.list_1_value);
        list_2_name = (TextView) itemView.findViewById(R.id.list_2_name);
        list_2_value = (TextView) itemView.findViewById(R.id.list_2_value);
        list_2 = itemView.findViewById(R.id.list_2);
        btn_more = (TextView) itemView.findViewById(R.id.btn_more);
        buff=itemView.findViewById(R.id.buff);
        latyout_cancle=itemView.findViewById(R.id.latyout_cancle);
        latyout_ime=itemView.findViewById(R.id.latyout_ime);
    }

    @Override
    public void setUpView(final Details model, int position, RecyclerView.Adapter adapter) {
        order_pay_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), OrderCheckActivity.class);
                intent.putExtra(Constant.ORDER_ID, model.getOrderID());
                intent.putExtra(Constant.TRAD_TYPE, Constant.Trad_Type.PAY);
                itemView.getContext().startActivity(intent);
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
                        final Dialog dialog= DialogUtils.getLoadingDialog(itemView.getContext(),"正在删除");
                        new PayAction().CancelPayOrder(model.getOrderID())
                                .subscribe(new RxSubscribe<DataWrapper>() {
                                    @Override
                                    public void onError(String message) {
                                        DialogUtils.fail(dialog,"删除失败，"+message);
                                    }

                                    @Override
                                    public void onNext(DataWrapper s) {
                                        DialogUtils.success(dialog);
                                        RxBus.getInstance().send(Events.FRESH_PAYLIST,null);
                                    }
                                });
                    }
                });
            }
        });
        pay_immediately.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), OrderCollectionActivity.class);
                intent.putExtra(Constant.ORDER_ID, model.getOrderID());
                itemView.getContext().startActivity(intent);
            }
        });
        initView(model);
    }

    private void initView(final Details model) {
        item_time.setText(model.getOrderTime() + " 收款人：" + model.getUser().getUserName());
        float cost = 0;
        for (Goods goods : model.getOrderProductList()) {
            cost = cost + goods.getCost();
        }
        item_amount.setText("" + cost);
        if (model.getOrderProductList() == null || model.getOrderProductList().isEmpty()) {
            item_goods_num.setText("共0件商品 合计");
        } else {
            item_goods_num.setText("共" + model.getOrderProductList().size() + "件商品 合计");
        }
        int size = 0;
        if (model.getOrderProductList() != null) {
            size = model.getOrderProductList().size();
            list_2.setVisibility(View.GONE);
            Goods goods1 = model.getOrderProductList().get(0);
            list_1_name.setText(goods1.getProductName());
            list_1_value.setText(goods1.getCost() + "元");
            if (size > 1) {
                list_2.setVisibility(View.VISIBLE);
                Goods goods2 = model.getOrderProductList().get(1);
                list_2_name.setText(goods2.getProductName());
                list_2_value.setText(goods2.getCost() + "元");
            }
            if (size > 2)
                btn_more.setTextColor(itemView.getContext().getResources().getColor(R.color.kv_itemview_name_textcolor));
            else {
                btn_more.setTextColor(itemView.getContext().getResources().getColor(R.color.gray_f2));
            }
        }

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(itemView.getContext(),PaymentGoodsList.class);
//                intent.putExtra("pay",model);
//                intent.putExtra("payType","pay");
//                itemView.getContext().startActivity(intent);

                Intent intent = new Intent(itemView.getContext(), OrderCheckActivity.class);
                intent.putExtra(Constant.ORDER_ID, model.getOrderID());
                intent.putExtra(Constant.TRAD_TYPE, Constant.Trad_Type.PAY);
                itemView.getContext().startActivity(intent);
            }
        });
        switch (model.getOrderStatus()) {
            case 2:
                item_state.setTextColor(itemView.getContext().getResources().getColor(R.color.green));
                item_state.setText("已付款");
                latyout_cancle.setVisibility(View.GONE);
                latyout_ime.setVisibility(View.GONE);
                buff.setVisibility(View.VISIBLE);
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) order_pay_check.getLayoutParams();
//                layoutParams.alignWithParent=true;
//                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                order_pay_check.setLayoutParams(layoutParams);
                break;
            case 1:
                item_state.setTextColor(itemView.getContext().getResources().getColor(R.color.strong_color));
                item_state.setText("待付款");
                latyout_cancle.setVisibility(View.VISIBLE);
                latyout_ime.setVisibility(View.VISIBLE);
                buff.setVisibility(View.GONE);
//                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) order_pay_check.getLayoutParams();
//                layoutParams2.alignWithParent=false;
//                layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                order_pay_check.setLayoutParams(layoutParams2);
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
