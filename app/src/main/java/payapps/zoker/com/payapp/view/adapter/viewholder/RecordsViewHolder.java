package payapps.zoker.com.payapp.view.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zekers.ui.view.recycler.VisitableViewHolder;
import com.zekers.utils.logger.Logger;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.model.Records;
import payapps.zoker.com.payapp.view.Activity.PayDetailActivity;

/**
 * Created by Zoker on 2017/3/2.
 */

public class RecordsViewHolder extends VisitableViewHolder<Records>{
    ImageView iconView;
    TextView nameView,signView,valueView;
    Records model;
    public RecordsViewHolder(final View itemView) {
        super(itemView);
        iconView=(ImageView) itemView.findViewById(R.id.item_icon);
        nameView=(TextView) itemView.findViewById(R.id.item_name);
        signView=(TextView) itemView.findViewById(R.id.item_sign);
        valueView=(TextView) itemView.findViewById(R.id.item_value);

    }

    @Override
    public void setUpView(final Records model, int position, RecyclerView.Adapter adapter) {
        Logger.d("test","inhere");
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(itemView.getContext(), PayDetailActivity.class);
                intent.putExtra("Records",model);
                itemView.getContext().startActivity(intent);
            }
        });
        Glide.with(itemView.getContext())
                .load(model.getHeadImage())
                .placeholder(R.drawable.man)
                .error(R.drawable.man)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iconView);
        nameView.setText(model.getUserName());
        signView.setText(model.getTransactionTime());
        valueView.setText(model.getAmount());
    }
}
