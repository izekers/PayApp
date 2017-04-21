package payapps.zoker.com.payapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.model.Goods;

/**
 * Created by Administrator on 2017/3/30.
 */

public class BeSelectAdapter extends BaseAdapter{
    private List<Goods> mdata;
    @Override
    public int getCount() {
        if (mdata==null ||mdata.isEmpty())
            return 0;
        return mdata.size();
    }

    @Override
    public Object getItem(int i) {
        if (i==0)
            return null;
        return mdata.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==-1)
            return 1;
        else
            return 2;
    }

    public void setData(List<Goods> data){
        this.mdata=data;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (position==-1){
            view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_empty,viewGroup,false);
            if (mdata==null || mdata.isEmpty())
                ((TextView)view.findViewById(R.id.value)).setText("没有被选中的商品");
            else
                ((TextView)view.findViewById(R.id.value)).setText("点击查看被选中的商品");
        }else {
            Goods goods=mdata.get(position);
            String name=goods.getProductName();
            int quantity=goods.getQuantity();
            view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_be_select_goods,viewGroup,false);
            ((TextView)view.findViewById(R.id.name_view)).setText(name+"("+goods.getPrice()+"元/"+goods.getUnit()+")");
            ((TextView)view.findViewById(R.id.value_view)).setText(""+quantity);
        }
        return view;
    }
    public static class ViewHolder{
        View itemView;
        TextView name_view,value_view;
        public ViewHolder(View itemView){
            this.itemView=itemView;
            name_view=(TextView) this.itemView.findViewById(R.id.name_view);
            value_view=(TextView) this.itemView.findViewById(R.id.value_view);
        }
    }
}
