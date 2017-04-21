package payapps.zoker.com.payapp.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zekers.ui.view.recycler.VisitableViewHolder;
import com.zekers.utils.logger.Logger;

import org.w3c.dom.Text;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.model.Goods;

/**
 * Created by Administrator on 2017/3/21.
 */

public class GoodsSelectViewHolder extends VisitableViewHolder<Goods> {
    private TextView name,number;
    private View jian,add;
    public GoodsSelectViewHolder(View itemView) {
        super(itemView);
        name=(TextView)itemView.findViewById(R.id.name);
        number=(TextView)itemView.findViewById(R.id.number);
        jian=itemView.findViewById(R.id.jian);
        add=itemView.findViewById(R.id.add);
    }

    @Override
    public void setUpView(final Goods model, int position, RecyclerView.Adapter adapter) {
        Logger.d("123",new Gson().toJson(model));
        name.setText(model.getProductName());
        number.setText(""+model.getQuantity());
        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.jianQuantity();
                number.setText(model.getQuantity()+"");
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.addQuantity();
                number.setText(model.getQuantity()+"");
            }
        });
    }
}
