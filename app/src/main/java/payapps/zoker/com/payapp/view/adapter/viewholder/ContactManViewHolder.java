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
import payapps.zoker.com.payapp.model.Records;

/**
 * Created by Zoker on 2017/3/2.
 */

public class ContactManViewHolder extends VisitableViewHolder<ContactMan>{
    ImageView iconView;
    TextView nameView,signView,valueView;
    SwipeLayout swipe_layout;
    TextView but_delete;
    PayAction payAction = new PayAction();
    public ContactManViewHolder(View itemView) {
        super(itemView);
        iconView=(ImageView) itemView.findViewById(R.id.item_icon);
        nameView=(TextView) itemView.findViewById(R.id.item_name);
        signView=(TextView) itemView.findViewById(R.id.item_sign);
        valueView=(TextView) itemView.findViewById(R.id.item_value);
        but_delete = (TextView) itemView.findViewById(R.id.btn_delete);
    }


    Dialog dialog;
    @Override
    public void setUpView(final ContactMan model, int position, RecyclerView.Adapter adapter) {
        Glide.with(itemView.getContext())
                .load(model.getHeadImage())
                .placeholder(R.drawable.women)
                .error(R.drawable.women)
                .into(iconView);
        nameView.setText(model.getNickName());
        signView.setText(model.getCompanyName());
        valueView.setText(model.getMobilephone());
        if (model.isShowDel()) {
            but_delete.setVisibility(View.VISIBLE);
        }else {
            but_delete.setVisibility(View.GONE);
        }
        but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog= DialogUtils.getLoadingDialog(itemView.getContext(),"正在删除");
                payAction.DeleteAddressBook(model.getId())
                        .subscribe(new RxSubscribe<DataWrapper>() {
                            @Override
                            public void onError(String message) {
                                DialogUtils.fail(dialog,"删除失败,"+message);
                            }

                            @Override
                            public void onNext(DataWrapper s) {
                                DialogUtils.success(dialog,"删除成功");
                                RxBus.getInstance().send(Events.FRESH_ADDBANKS, null);
                            }
                        });
            }
        });

    }
}
