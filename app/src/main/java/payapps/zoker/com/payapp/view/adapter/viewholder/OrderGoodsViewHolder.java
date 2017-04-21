package payapps.zoker.com.payapp.view.adapter.viewholder;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.ui.view.recycler.VisitableViewHolder;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.Goods;

/**
 * Created by Zoker on 2017/3/2.
 */

public class OrderGoodsViewHolder extends VisitableViewHolder<Goods> {
    PayAction payAction;
    TextView item_name, item_vale,item_number,item_account;

    public OrderGoodsViewHolder(final View itemView) {
        super(itemView);
        payAction = new PayAction();
        item_name=(TextView)itemView.findViewById(R.id.item_name);
        item_vale=(TextView)itemView.findViewById(R.id.item_vale);
        item_number=(TextView)itemView.findViewById(R.id.item_number);
        item_account=(TextView)itemView.findViewById(R.id.item_account);
    }
    @Override
    public void setUpView(final Goods model, int position, RecyclerView.Adapter adapter) {
        item_name.setText(model.getProductName());
        item_vale.setText(model.getPrice() + "元/" + model.getUnit());
        item_number.setText("数量："+model.getQuantity()+model.getUnit());
        item_account.setText(model.getCost()+"元");
    }
}
