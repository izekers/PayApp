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

import payapps.zoker.com.payapp.model.Records;

/**
 * 这里只用在意排列的问题，不需要在意怎么分类，数据的绑定情况
 * Created by Zoker on 2017/2/22.
 */
public class RecordsAdapter extends RecyclerView.Adapter<VisitableViewHolder> {
    private VisitableTypeControl.TypeFactory typeFactory;
    private List<Records> mDatas;
    public RecordsAdapter(VisitableTypeControl.TypeFactory typeFactory) {
        this.typeFactory = typeFactory;
        this.mDatas=new ArrayList<>();
    }

    @Override
    public VisitableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return typeFactory.createViewHolder(viewType, LayoutInflater.from(parent.getContext()).inflate(typeFactory.getRes(viewType),parent,false));
    }

    @Override
    public void onBindViewHolder(VisitableViewHolder holder, int position) {
        holder.setUpView(mDatas.get(position), position, this);
    }

    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.isEmpty()){
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

    public void setmDatas(List<Records> mDatas) {
        Logger.d(new Gson().toJson(mDatas));
        this.mDatas = mDatas;
        notifyDataSetChanged();
        Logger.d(new Gson().toJson(mDatas));
    }

    public List<Records> getmDatas() {
        return mDatas;
    }

}
