package payapps.zoker.com.payapp.view.adapter.viewholder;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.ui.view.recycler.VisitableViewHolder;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.ContactMan;

/**
 * Created by Zoker on 2017/3/2.
 */

public class ContactManSelectViewHolder extends VisitableViewHolder<ContactMan>{
    ImageView iconView;
    TextView nameView,signView,valueView;
    PayAction payAction = new PayAction();
    public ContactManSelectViewHolder(View itemView) {
        super(itemView);
        iconView=(ImageView) itemView.findViewById(R.id.item_icon);
        nameView=(TextView) itemView.findViewById(R.id.item_name);
        signView=(TextView) itemView.findViewById(R.id.item_sign);
        valueView=(TextView) itemView.findViewById(R.id.item_value);
    }

    Dialog dialog;
    @Override
    public void setUpView(final ContactMan model, int position, RecyclerView.Adapter adapter) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().send(Events.SELECT_CONTACTMAN,model);
            }
        });
        Glide.with(itemView.getContext())
                .load(model.getHeadImage())
                .into(iconView);
        nameView.setText(model.getNickName());
        signView.setText(model.getCompanyName());
        valueView.setText(model.getMobilephone());
    }
}
