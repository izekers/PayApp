package payapps.zoker.com.payapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.zekers.ui.view.recycler.VisitableTypeControl;
import com.zekers.ui.view.recycler.VisitableViewHolder;
import com.zekers.utils.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import payapps.zoker.com.payapp.model.Details;
import payapps.zoker.com.payapp.model.Goods;
import payapps.zoker.com.payapp.view.Constant;

/**
 * 这里只用在意排列的问题，不需要在意怎么分类，数据的绑定情况
 * Created by Zoker on 2017/2/22.
 */
public class GoodsSelectAdapter extends RecyclerView.Adapter<VisitableViewHolder> {
    private VisitableTypeControl.TypeFactory typeFactory;
    private List<Goods> mDatas;

    public GoodsSelectAdapter(VisitableTypeControl.TypeFactory typeFactory) {
        this.typeFactory = typeFactory;
        this.mDatas = new ArrayList<>();
    }

    @Override
    public VisitableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return typeFactory.createViewHolder(viewType, LayoutInflater.from(parent.getContext()).inflate(typeFactory.getRes(viewType), parent, false));
    }

    @Override
    public void onBindViewHolder(VisitableViewHolder holder, int position) {
        holder.setUpView(mDatas.get(position), position, this);
    }

    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.isEmpty()) {
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas != null) {
            return mDatas.get(position).type(typeFactory);
        }
        return super.getItemViewType(position);
    }

    public void setmDatas(List<Goods> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    //    {"ProductID":15,"UserID":6,"ProductName":"gghs","FirstCateID":0,"SecondCateID":1,"ThreeCateID":1,"Price":123.00,"Unit":"gg","ProductCover":"","ProductImage":"","ProductTime":"2017/3/20 14:02:42"}
    public List<Goods> getSelectGoods() {
        List<Goods> selectGoodsList = new ArrayList<>();
        Logger.d("goodsList", new Gson().toJson(mDatas));
        for (Goods goods : mDatas) {
            if (goods.getQuantity() != 0) {
                goods.setCost(goods.getPrice() * goods.getQuantity());
                goods.setUserID("0");
                goods.setOrderNo("0");
                goods.setProductTime("");
                selectGoodsList.add(goods);
            }
        }
        return selectGoodsList;
    }

    public void setSelectGoods(List<Goods> goodsList) {
        if (goodsList==null || goodsList.isEmpty())
            return;
        if (mDatas==null || mDatas.isEmpty()){
            mDatas=goodsList;
            return;
        }

        for (Goods goods : goodsList) {
            for (int i=0;i<mDatas.size();i++){
                if (mDatas.get(i).getProductName().equals(goods.getProductName())) {
                    mDatas.get(i).setQuantity(goods.getQuantity());
                    break;
                }
                if (i==mDatas.size()-1){
                    mDatas.add(goods);
                }
            }
        }
        notifyDataSetChanged();
    }
}
