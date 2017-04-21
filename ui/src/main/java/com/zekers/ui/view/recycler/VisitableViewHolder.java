package com.zekers.ui.view.recycler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 *
 * Created by Administrator on 2017/1/17.
 */
public abstract class VisitableViewHolder<T> extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private View mItemView;

    public VisitableViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        this.mItemView = itemView;
    }

    public View getView(int resID) {
        View view = views.get(resID);
        if (view == null) {
            view = mItemView.findViewById(resID);
            views.put(resID, view);
        }
        return view;
    }

    public abstract void setUpView(T model, int position, RecyclerView.Adapter adapter);



    protected void showSelectMsg(String title,final SelectCallBack callBack){
        new AlertDialog.Builder(itemView.getContext()).setTitle(title)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callBack.ok();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callBack.cancel();
                    }
                }).create().show();
    }

    public interface SelectCallBack{
        void cancel();
        void ok();
    }
}
