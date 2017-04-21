package com.zekers.ui.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 使用了Visitale解耦模式的基础适配器
 * Created by Zekers on 2017/1/18.
 */
public class VisitableAdapter extends RecyclerView.Adapter<VisitableViewHolder> {
    private VisitableTypeControl.TypeFactory typeFactory;
    private List<VisitableTypeControl.Visitable> mDatas;
    public VisitableAdapter(VisitableTypeControl.TypeFactory typeFactory, List<VisitableTypeControl.Visitable> mDatas){
        this.typeFactory=typeFactory;
        this.mDatas=mDatas;
    }
    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).type(typeFactory);
    }

    @Override
    public VisitableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(typeFactory.getRes(viewType), parent, false);
        return typeFactory.createViewHolder(viewType, itemView);
    }

    @Override
    public void onBindViewHolder(VisitableViewHolder holder, int position) {
        holder.setUpView(mDatas.get(position), position, this);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
}
