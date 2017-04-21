package com.zekers.ui.view.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * 可以添加header和fooder的RecyclerView
 * Created by Zekers on 2017/1/11.
 */
public class RecyclerWrapView extends RecyclerView {
    public RecyclerWrapView(Context context) {
        super(context);
    }

    public RecyclerWrapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerWrapView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();

    private Adapter mAdapter;

    /**
     * 添加头部
     */
    public void addHeaderView(View view) {
        mHeaderViews.clear();
        mHeaderViews.add(view);
        if (mAdapter != null) {
            if (!(mAdapter instanceof RecyclerWrapAdapter)) {
                mAdapter = new RecyclerWrapAdapter(mHeaderViews, mFooterViews, mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 添加尾部
     */
    public void addFooderView(View view) {
        mFooterViews.clear();
        mFooterViews.add(view);
        if (mAdapter != null) {
            if (!(mAdapter instanceof RecyclerWrapAdapter)) {
                mAdapter = new RecyclerWrapAdapter(mHeaderViews, mFooterViews, mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 设置adapter
     */
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (mHeaderViews.isEmpty() && mFooterViews.isEmpty()) {
            super.setAdapter(adapter);
        } else {
            adapter = new RecyclerWrapAdapter(mHeaderViews, mFooterViews, adapter);
            super.setAdapter(adapter);
        }
        mAdapter = adapter;
    }

    public Adapter getmAdapter() {
        return mAdapter;
    }
}
