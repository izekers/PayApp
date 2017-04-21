package com.zekers.ui.view.recycler;

import android.view.View;
import android.view.ViewGroup;

/**
 * 应用了Visitable分类方法的RecyclerView分类控制器
 * Created by Administrator on 2017/1/17.
 */
public class VisitableTypeControl {
    public interface TypeFactory{
        //直接跟model有关
        int type(Visitable visitable);
        VisitableViewHolder createViewHolder(int type, View itemView);
        VisitableViewHolder createViewHolder(int type, ViewGroup parent);
        int getRes(int type);
    }

    public interface Visitable {
        int type(TypeFactory typeFactory);
    }
}
