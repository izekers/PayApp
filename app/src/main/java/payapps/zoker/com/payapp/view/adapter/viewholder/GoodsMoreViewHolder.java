package payapps.zoker.com.payapp.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zekers.ui.view.recycler.VisitableViewHolder;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.model.Goods;

/**
 * Created by Administrator on 2017/3/21.
 */

public class GoodsMoreViewHolder extends VisitableViewHolder<Goods> {
    private TextView name,value;
    public GoodsMoreViewHolder(View itemView) {
        super(itemView);
        name=(TextView)itemView.findViewById(R.id.name);
        value=(TextView)itemView.findViewById(R.id.value);
    }

    @Override
    public void setUpView(final Goods model, int position, RecyclerView.Adapter adapter) {
        name.setText(model.getProductName());
        value.setText(model.getCost()+"元");
    }
}
