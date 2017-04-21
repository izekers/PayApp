package com.zekers.ui.view.recycler;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础的Adapter实现，不完善不建议用
 * Created by Zekers on 2017/1/13.
 */
public abstract class RecyclerBaseAdapter extends RecyclerView.Adapter{
    private static String TAG = "AppFavorAdapter";
    private List mdatas;
    private OnItemClickerListener onItemClickerListener;

    public abstract <T> List<T>setDatas(List<T> mdatas);


    public RecyclerBaseAdapter(List datas) {
        this.mdatas = datas;
    }


    public List getDatas() {
        return mdatas;
    }

    //控件地址保存类
    private class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_icon, iv_bg;
        TextView tv_itemName;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    //设置点击事件监听
    public void setOnItemClickerListener(OnItemClickerListener listener) {
        this.onItemClickerListener = listener;
    }


    public void resetData(List mdatas){
        this.mdatas=mdatas;
        notifyDataSetChanged();
    }



    //添加数据
    //当充满RecycleView同时插入位置为position=0时，记得调用mRecyclerView.scrollToPosition(0)
    public void addItems(List addDatas, int position, boolean isAnimation) {
        if (this.mdatas != null && position > this.mdatas.size())
            position = this.mdatas.size();

        //数据处理部分
        if (mdatas == null) {
            this.mdatas = addDatas;
        } else {
            this.mdatas.addAll(position, addDatas);
        }

        //UI
        if (isAnimation) {
            notifyItemRangeInserted(position, addDatas.size());
            //刷新从position+addDatas.size()开始的(position+addDatas.size())数量的item
            //也就是刷新插入的item后面的item
            notifyItemRangeChanged(position + addDatas.size(), getItemCount() - (position + addDatas.size()));
        } else
            notifyDataSetChanged();
    }

    public void addItem(Object addDatas, int position,boolean isAnimation){
        if (this.mdatas != null && position > this.mdatas.size())
            position = this.mdatas.size();

        //数据处理部分
        if (mdatas == null) {
            this.mdatas = new ArrayList();
        } else {
            this.mdatas.add(position, addDatas);
        }
    }
    //从最后添加数据
    public void addItems(List addDatas, boolean isAnimation) {
        addItems(addDatas, mdatas.size(), isAnimation);
    }

    //删除数据,从startPosition开始，num个数量
    public void removeItems(int startPosition, int num, boolean isAnimation) {
        if (this.mdatas != null && startPosition > this.mdatas.size() - 1) {
            startPosition = this.mdatas.size() - 1;
            num = 1;
        }
        Log.i(TAG, "#removeItems start=" + startPosition + ",num=" + num);
        //数据处理部分
        if (mdatas != null) {
            if (num == 0) return;
            if (num == 1)
                this.mdatas.remove(startPosition);
            else {
                for (int i = startPosition; i < (startPosition + num); i++)
                    this.mdatas.remove(i);
            }
        }
        //UI
        if (isAnimation) {
            notifyItemRangeRemoved(startPosition, num);
            notifyItemRangeChanged(startPosition, getItemCount() - startPosition);
        } else {
            notifyDataSetChanged();
        }
    }

    //通过动画删除数据
    public void removeItem(int position, boolean isAnimation) {
        removeItems(position, 1, isAnimation);
    }





    //点击事件监听者
    public interface OnItemClickerListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}
