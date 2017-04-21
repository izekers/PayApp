package com.zekers.ui.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 将滑动时的x,y坐标暴露出来的HorizontalScrollView
 * Created by zekers on 2016/11/3.
 */
public class HorizontalScrollView extends android.widget.HorizontalScrollView {
    public interface OnScrollChangeListener {
        void onScrollChanged(HorizontalScrollView scrollView, int x, int y, int oldx, int oldy);
    }
    private OnScrollChangeListener onScrollChangedListener;

    public HorizontalScrollView(Context context) {
        super(context);
    }
    public HorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setScrollViewListener(OnScrollChangeListener scrollViewListener) {
        this.onScrollChangedListener = scrollViewListener;
    }
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (this.onScrollChangedListener != null) {
            this.onScrollChangedListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
