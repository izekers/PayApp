package payapps.zoker.com.payapp.view.adapter.viewholder;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import payapps.zoker.com.payapp.model.Goods;
import payapps.zoker.com.payapp.model.Records;
import payapps.zoker.com.payapp.view.Activity.PayDetailActivity;

/**
 * Created by Zoker on 2017/3/2.
 */

public class GoodsViewHolder extends VisitableViewHolder<Goods> {
    SwipeLayout swipe_layout;
    TextView but_delete;
    PayAction payAction;
    TextView item_name, item_vale;

    public GoodsViewHolder(final View itemView) {
        super(itemView);
        swipe_layout = (SwipeLayout) itemView.findViewById(R.id.swipe_layout);
        item_name = (TextView) itemView.findViewById(R.id.item_name);
        item_vale = (TextView) itemView.findViewById(R.id.item_vale);
        //set show mode.
        swipe_layout.setShowMode(SwipeLayout.ShowMode.LayDown);

        //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
        swipe_layout.addDrag(SwipeLayout.DragEdge.Left, itemView.findViewById(R.id.bottom_wrapper));
        but_delete = (TextView) itemView.findViewById(R.id.btn_delete);
        swipe_layout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });
        payAction = new PayAction();
    }
    Dialog dialog;
    @Override
    public void setUpView(final Goods model, int position, RecyclerView.Adapter adapter) {
        item_name.setText(model.getProductName());
        item_vale.setText(model.getPrice() + "元/" + model.getUnit());
        but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog= DialogUtils.getLoadingDialog(itemView.getContext(),"正在删除");
                payAction.DeleteProduct(model.getProductID())
                        .subscribe(new RxSubscribe<DataWrapper>() {
                            @Override
                            public void onError(String message) {
                                DialogUtils.fail(dialog,"删除失败,"+message);
                            }

                            @Override
                            public void onNext(DataWrapper s) {
                                DialogUtils.success(dialog,"删除成功");
                                RxBus.getInstance().send(Events.FRESH_GOODLIST, null);
                            }
                        });
            }
        });
    }
}
