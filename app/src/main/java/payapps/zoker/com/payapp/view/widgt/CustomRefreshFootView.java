package payapps.zoker.com.payapp.view.widgt;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by Administrator on 2017/4/1.
 */

public class CustomRefreshFootView extends TextView implements SwipeLoadMoreTrigger,SwipeTrigger {
    public CustomRefreshFootView(Context context) {
        super(context);
    }

    public CustomRefreshFootView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRefreshFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomRefreshFootView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLoadMore() {
        setText("正在拼命加载数据...");
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {
        setText("释放刷新");
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        setText("刷新成功");
    }

    @Override
    public void onReset() {

    }
}
