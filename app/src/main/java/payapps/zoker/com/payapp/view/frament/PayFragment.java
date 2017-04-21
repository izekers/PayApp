package payapps.zoker.com.payapp.view.frament;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.pjutils.BaseApplication;
import com.zekers.ui.view.recycler.VisitableTypeControl;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.Collection;
import payapps.zoker.com.payapp.model.Details;
import payapps.zoker.com.payapp.view.Constant;
import payapps.zoker.com.payapp.view.adapter.PayAdapter;
import payapps.zoker.com.payapp.view.adapter.PayTypeFactory;
import rx.functions.Action1;

/**
 * Created by Zoker on 2017/3/2.
 */
//付款订单
public class PayFragment extends Fragment {
    RecyclerView list;
    PayAdapter adapter;
    Constant.Pay_Type type;

    int PageIndex = 1;

    public static PayFragment getInstance(Constant.Pay_Type type) {
        PayFragment fragment = new PayFragment();
        Bundle arg = new Bundle();
        arg.putSerializable(Constant.PAY_TYPE, type);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            type = (Constant.Pay_Type) bundle.getSerializable(Constant.PAY_TYPE);
    }

    SwipeToLoadLayout swipeToLoadLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.view_list, container, false);
        this.swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        list=(RecyclerView)view.findViewById(R.id.swipe_target);
        list.setLayoutManager(new LinearLayoutManager(container.getContext()));
        adapter = new PayAdapter(PayTypeFactory.getInstance());
        list.setAdapter(adapter);
        initData(type);
        empty_view=view.findViewById(R.id.empty_view);
        empty_view.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
        RxBus.getSubscriber().setEvent(Events.FRESH_PAYLIST)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        dialog=DialogUtils.getLoadingDialog(getContext(),"正在重新加载订单");
                        initData(type);
                    }
                })
                .create();

        //为swipeToLoadLayout设置下拉刷新监听者
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                PageIndex = 1;
                initData(type);
            }
        });
        //为swipeToLoadLayout设置上拉加载更多监听者
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                PageIndex++;
                initData(type);
            }
        });
        return view;
    }
    View empty_view;
    Dialog dialog;
    public void initData(Constant.Pay_Type type) {
        switch (type) {
            case ALL:
                initAll();
                break;
            case HAS:
                initHas();
                break;
            case WAIT:
                initWait();
                break;
        }
    }
    public void initAll() {GetPayOrderList(0);
    }

    public void initHas() {
        GetPayOrderList(2);
    }

    public void initWait() {
        GetPayOrderList(1);
    }
    PayAction payAction = new PayAction();

    /**
     * 获得订单列表
     * 1 未付款 2已付款 3已取消
     */
    public void GetPayOrderList(int OrderStatus) {
        payAction.GetPayOrderList(OrderStatus, PageIndex)
                .subscribe(new RxSubscribe<List<Details>>() {
                    @Override
                    public void onError(String message) {
                        PageIndex--;
                        if (swipeToLoadLayout.isRefreshing()) {
                            //设置下拉刷新结束
                            swipeToLoadLayout.setRefreshing(false);
                        }
                        if (swipeToLoadLayout.isLoadingMore()) {
                            //设置上拉加载更多结束
                            swipeToLoadLayout.setLoadingMore(false);
                        }
                    }

                    @Override
                    public void onNext(List<Details> details) {
                        if (swipeToLoadLayout.isRefreshing()) {
                            //设置下拉刷新结束
                            swipeToLoadLayout.setRefreshing(false);
                        }
                        if (swipeToLoadLayout.isLoadingMore()) {
                            //设置上拉加载更多结束
                            swipeToLoadLayout.setLoadingMore(false);
                        }
                        if (PageIndex!=1){
                            List<Details> data=adapter.getmDatas();
                            if (details!=null)
                                data.addAll(details);
                            details=data;
                        }
                        DialogUtils.success(dialog);

                        if (details==null || details.isEmpty()){
                            empty_view.setVisibility(View.VISIBLE);
                            list.setVisibility(View.GONE);
                        }else {
                            adapter.setmDatas(details);
                            empty_view.setVisibility(View.GONE);
                            list.setVisibility(View.VISIBLE);
                        }
//                        adapter.setmDatas();
                    }
                });
    }
}
