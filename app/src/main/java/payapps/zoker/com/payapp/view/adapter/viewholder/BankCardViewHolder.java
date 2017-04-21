package payapps.zoker.com.payapp.view.adapter.viewholder;

import android.app.Dialog;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.swipe.SwipeLayout;
import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.pjutils.BaseApplication;
import com.zekers.ui.view.recycler.VisitableViewHolder;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.BankCard;
import payapps.zoker.com.payapp.model.Records;

/**
 * Created by Administrator on 2017/3/23.
 */

public class BankCardViewHolder extends VisitableViewHolder<BankCard> {
    ImageView iconView;
    TextView nameView, signView, valueView;
    TextView but_delete;
    PayAction payAction = new PayAction();
//    SwipeLayout swipe_layout;
    View root_view;
    public BankCardViewHolder(View itemView) {
        super(itemView);
        itemView.findViewById(R.id.item_icon).setVisibility(View.GONE);
        iconView = (ImageView) itemView.findViewById(R.id.item_img);
        iconView.setVisibility(View.VISIBLE);
        nameView = (TextView) itemView.findViewById(R.id.item_name);
        signView = (TextView) itemView.findViewById(R.id.item_sign);
        valueView = (TextView) itemView.findViewById(R.id.item_value);

        root_view=itemView.findViewById(R.id.root_view);
        but_delete = (TextView) itemView.findViewById(R.id.btn_delete);


//        swipe_layout = (SwipeLayout) itemView.findViewById(R.id.swipe_layout);
//        //set show mode.
//        swipe_layout.setShowMode(SwipeLayout.ShowMode.LayDown);
//
//        //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
//        swipe_layout.addDrag(SwipeLayout.DragEdge.Left, itemView.findViewById(R.id.bottom_wrapper));
    }

    Dialog dialog;
    class layout extends RecyclerView.LayoutManager{
        //用于保存所有Item的上下左右的偏移量信息
        private SparseArray<Rect> allItemFrames=new SparseArray<>();
        //记录Item是否出现过屏幕切还没有回收。true表示出现过屏幕上，并且还没有被回收
        private SparseBooleanArray hasAttachedItem = new SparseBooleanArray();

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return null;
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            super.onLayoutChildren(recycler, state);
            if (getItemCount()<=0) {
                totalHeight=0;
                verticallScrollOffset=0;
                return;
            }
            //跳过preLayout,preLayout主要用于支持动画
            if (state.isPreLayout()) return;
            //在布局之前，将所有的子View先Detach掉，放入Scrop缓存中
            detachAndScrapAttachedViews(recycler);

            //定义竖直方向上的偏移量
            int offsetY=0;totalHeight=0;
            for (int i=0;i<getItemCount();i++){
                //这里就是从缓存里面取出View
                View view=recycler.getViewForPosition(i);
                //将View加入到RecyclerView中
                addView(view);
                //对子View进行测量
                measureChildWithMargins(view,0,0);
                //把宽高拿到，宽高都是包含ItemDecorate的尺寸
                int width=getDecoratedMeasuredWidth(view);
                int heigth=getDecoratedMeasuredHeight(view);
                Rect frame=allItemFrames.get(i);
                if (frame==null){
                    frame=new Rect();
                }
                frame.set(0,offsetY,width,offsetY+heigth);
                //将当前的Item的Rect边界数据保存
                allItemFrames.put(i,frame);
                //由于已经调用了detachAndScrapAttacheViews,因此需要将当前的Item设置为未出现过
                hasAttachedItem.put(i,false);
                /**
                //最后进行布局
                layoutDecorated(view,0,offsetY,width,offsetY+heigth);
                 */
                //将竖直方向的偏移量增大height
                offsetY +=heigth;
                //计算取得所有子View的总高度
                totalHeight +=heigth;
            }
            //如果所有子View的总高度没有填满RecyclerView的高度
            //则将总高度设置为RecyclerView的高度
            totalHeight=Math.max(totalHeight,getVerticalSpace());
            recycleAndFillItems(recycler,state);
        }

        private void recycleAndFillItems(RecyclerView.Recycler recycler,RecyclerView.State state){
            if (state.isPreLayout()) return;
            //当前scroll offset状态下的显示区域
            Rect displayFrame=new Rect(0,verticallScrollOffset,getHorizontaSpace(),verticallScrollOffset+getVerticalSpace());
            //将滑出屏幕的Items回收到Recycle缓存中
            Rect childFrame=new Rect();
            for (int i=0;i<getChildCount();i++){
                View child=getChildAt(i);
                childFrame.left=getDecoratedLeft(child);
                childFrame.top=getDecoratedTop(child);
                childFrame.right=getDecoratedRight(child);
                childFrame.bottom=getDecoratedBottom(child);
                //如果Item没有在显示区域，就说明需要回收
                if (!Rect.intersects(displayFrame,childFrame)){
                    //回收掉滑出屏幕的View
                    removeAndRecycleView(child,recycler);
                }
            }
            //重新显示需要出现在屏幕的子View
            for (int i=0; i<getItemCount();i++){
                if (Rect.intersects(displayFrame,allItemFrames.get(i))){
                    View scrap=recycler.getViewForPosition(i);
                    measureChildWithMargins(scrap,0,0);
                    addView(scrap);
                    Rect frame=allItemFrames.get(i);
                    //将这个item布局出来
                    layoutDecorated(scrap,frame.left,frame.top-verticallScrollOffset,frame.right,frame.bottom-verticallScrollOffset);
                }
            }
        }
        @Override
        public boolean canScrollVertically() {
            return super.canScrollVertically();
        }

        @Override
        public boolean canScrollHorizontally() {
            return super.canScrollHorizontally();
        }
        private int verticallScrollOffset;//竖直方向的滑动偏移量
        private int totalHeight=0;//所有子View的总高度
        @Override
        public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
            //实际要滑动的距离
            int travel = dy;
            //如果滑动到最顶部
            if (verticallScrollOffset+dy<0){
                travel=-verticallScrollOffset;
            }//如果滑动到底部
            else if (verticallScrollOffset + dy> totalHeight-getVerticalSpace()){
                travel=totalHeight-getVerticalSpace() -verticallScrollOffset;
            }
            //将竖直方向的偏移量+travel
            verticallScrollOffset +=travel;
            //平移容器内的item
            offsetChildrenVertical(-travel);
            return super.scrollVerticallyBy(dy, recycler, state);
        }

        //RecycleView内部垂直方向上的可用高度
        private int getVerticalSpace(){
            return getHeight() - getPaddingBottom() -getPaddingTop();
        }

        //RecyclerView内部水平方向上的可用宽度
        private int getHorizontaSpace(){
            return getWidth() - getPaddingLeft() - getPaddingRight();
        }
    }



    @Override
    public void setUpView(final BankCard model, int position, RecyclerView.Adapter adapter) {
        but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = DialogUtils.getLoadingDialog(itemView.getContext(), "正在删除",false);
                payAction.DeleteBankCard(model.getBankCardID())
                        .subscribe(new RxSubscribe<DataWrapper>() {
                            @Override
                            public void onError(String message) {
                                DialogUtils.fail(dialog, "删除失败," + message);
                            }

                            @Override
                            public void onNext(DataWrapper s) {
                                DialogUtils.success(dialog, "删除成功");
                                RxBus.getInstance().send(Events.FRESH_BANKCARDLIST, null);
                            }
                        });
            }
        });

        iconView.setImageResource(R.drawable.bankcard2);
        nameView.setText(model.getBankName());
        signView.setText(model.getCardNumber());
        valueView.setText("活跃状态");
        valueView.setVisibility(View.GONE);
        if (model.getCardStatus().equals("1"))
            valueView.setVisibility(View.VISIBLE);
        root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=DialogUtils.getLoadingDialog(itemView.getContext(),"正在设置",false);
                payAction.UpdateBankCardStatus(model.getBankCardID())
                        .subscribe(new RxSubscribe<DataWrapper>() {
                            @Override
                            public void onError(String message) {
                                DialogUtils.fail(dialog,"设置错误,"+message);
                            }

                            @Override
                            public void onNext(DataWrapper s) {
                                valueView.setVisibility(View.VISIBLE);
                                DialogUtils.success(dialog);
                                RxBus.getInstance().send(Events.FRESH_BANKCARDLIST, null);
                            }
                        });
            }
        });
    }
}
