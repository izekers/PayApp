package com.zekers.ui.view.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 添加了header和fooder的RecyclerAdapter
 * Created by Zekers on 2017/1/11.
 */
public class RecyclerWrapAdapter extends RecyclerView.Adapter {
    private ArrayList<View> mHeaderViews;
    private ArrayList<View> mFooterViews;
    private RecyclerView.Adapter mAdapter;
    private int mCurrentPosition;
    static final ArrayList<View> EMPTY_INFO_LIST = new ArrayList<View>();

    public RecyclerWrapAdapter(ArrayList<View> mHeaderViews, ArrayList<View> mFooterViews, RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
        this.mHeaderViews = mHeaderViews != null ? mHeaderViews : EMPTY_INFO_LIST;
        this.mFooterViews = mFooterViews != null ? mFooterViews : EMPTY_INFO_LIST;
    }

    /**
     * 把View直接封装在ViewHolder中，然后我们面向的是ViewHolder这个实例
     * 如果是Header、Footer则使用HeaderViewHolder封装
     * 如果是其他的就不变
     *
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RecyclerView.INVALID_TYPE) {
            return new HeaderViewHolder(mHeaderViews.get(0));
        } else if (viewType == RecyclerView.INVALID_TYPE - 1) {
            return new HeaderViewHolder(mFooterViews.get(0));
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    /**
     * 用于适配渲染数据到View中。方法提供给你了一个viewHolder，而不是原来的convertView。
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //头
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return;
        }
        //中
        int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            //尾
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition);
                return;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mAdapter != null) {
            return getHeadersCount() + getFootersCount() + mAdapter.getItemCount();
        } else {
            return getHeadersCount() + getFootersCount();
        }
    }

    @Override
    public int getItemViewType(int position) {
        mCurrentPosition = position;
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return RecyclerView.INVALID_TYPE;   // 说明是Header所占用的空间
        }
        int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }
        return RecyclerView.INVALID_TYPE - 1;   // 说明是Footer的所占用的空间
    }

    @Override
    public long getItemId(int position) {
        int numHeaders = getHeadersCount();
        if (mAdapter != null && position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemId(adjPosition);     // 不是Header和Footer则返回其itemId
            }
        }
        return -1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {   // 布局是GridLayoutManager所管理
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果是Header、Footer的对象则占据spanCount的位置，否则就只占用1个位置
                    return (isHeader(position) || isFooter(position)) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 获取布局数量
     * @return
     */
    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFooterViews.size();
    }

    /**
     * 判断是否布局按钮
     */
    private boolean isHeader(int position) {

        return position >= 0 && position < mHeaderViews.size();
    }

    private boolean isFooter(int position) {

        return position < getItemCount() && position >= getItemCount() - mFooterViews.size();
    }

    public int getmCurrentPosition() {
        return mCurrentPosition;
    }

    //
    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
