package com.zekers.ui.listener;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * 上拉刷新的控制，直接通过RecyclerView的addOnScrollListener()使用
 * 并通过实现OnRecycleRefreshListener进行更新控制
 * Created by Zekers on 2017/1/17.
 */
public class LoadDataScrollController extends RecyclerView.OnScrollListener implements SwipeRefreshLayout.OnRefreshListener{
    RecyclerView.LayoutManager mLayoutManager;


    /**
     * RecyclerView 的布局管理器类型
     */
    private enum LayoutManagerType {
        LINEAR_LAYOUT,
        GRID_LAYOUT,
        STAGGERED_GRID_LAYOUT
    }

    private int mLastVisibleItemPosition;//当前RecyclerView显示的最大条目
    private int[] mLastPosition;//每列的最后一个条目
    private boolean isLoadData = false;//是否正在加载数据，包括刷新和向上加载更多
    private LayoutManagerType mLayoutManagerType;
    private OnRecycleRefreshListener mListener;

    public LoadDataScrollController(OnRecycleRefreshListener onRecyclerRefreshListener){
        this.mListener=onRecyclerRefreshListener;
    }
    //获取布局类型和最后一个item数
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        /**
         * 获取布局参数
         */
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        //如果为null，第一次运行，确定布局类型
        if (mLayoutManagerType == null) {
            if (layoutManager instanceof GridLayoutManager) {
                mLayoutManagerType = LayoutManagerType.GRID_LAYOUT;
            } else if (layoutManager instanceof LinearLayoutManager) {
                mLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                mLayoutManagerType = LayoutManagerType.STAGGERED_GRID_LAYOUT;
            } else {
                throw new RuntimeException("LayoutManager should be LinearLayout,GridLayoutManager or StaggeredGridLayoutManager");
            }
        }

        //对于不太能够的布局参数，不同的方法获取到当前显示的最后一个条目数
        switch (mLayoutManagerType) {
            case LINEAR_LAYOUT:
                mLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GRID_LAYOUT:
                mLastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID_LAYOUT:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (mLastPosition == null) {
                    mLastPosition = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(mLastPosition);
                mLastVisibleItemPosition = findMax(mLastPosition);
                break;
            default:
                break;
        }
        super.onScrolled(recyclerView, dx, dy);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //RecyclerView 显示的条目数
        int visibleCount = layoutManager.getChildCount();

        //显示数据总数
        int totalCount = layoutManager.getItemCount();

        //四个条件，分别是是否有数据，状态是否是滑动停止状态，显示的最大条目数是否大于整个数据（注意偏移量），
        //是否正在加载数据
        if (visibleCount > 0
                && newState == RecyclerView.SCROLL_STATE_IDLE
                && mLastVisibleItemPosition >= totalCount - 1
                && !isLoadData) {
            isLoadData = true;
            mListener.loadMore();
        }
    }
    //瀑布流发现最后一个条目数
    private int findMax(int[] nums) {
        int maxnum = nums[0];
        for (int num : nums) {
            if (num > maxnum)
                maxnum = num;
        }
        return maxnum;
    }
    //
    public void setLoadDataStatus(boolean isLoadData){
        this.isLoadData=isLoadData;
    }


    @Override
    public void onRefresh() {
        //刷新数据的方法
        if (mListener!=null){
            isLoadData=true;
            mListener.refresh();
        }
    }

    public interface OnRecycleRefreshListener{
        void refresh();
        void loadMore();
    }
}
