package payapps.zoker.com.payapp.view.frament;

import android.app.Dialog;
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
import java.util.Collections;
import java.util.List;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.Collection;
import payapps.zoker.com.payapp.model.Details;
import payapps.zoker.com.payapp.view.Constant;
import payapps.zoker.com.payapp.view.adapter.CollectionAdapter;
import payapps.zoker.com.payapp.view.adapter.PayAdapter;
import payapps.zoker.com.payapp.view.adapter.PayTypeFactory;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Zoker on 2017/3/2.
 */

//收款订单
public class CollectionFragment extends Fragment {
    RecyclerView list;
    CollectionAdapter adapter;
    List<Collection> mdata;
    Constant.Pay_Type type;

    int PageIndex = 1;
    boolean isSearch=false;
    String keyWord="";
    public static CollectionFragment getInstance(Constant.Pay_Type type) {
        CollectionFragment fragment = new CollectionFragment();
        Bundle arg = new Bundle();
        arg.putSerializable(Constant.PAY_TYPE, type);
        fragment.setArguments(arg);
        return fragment;
    }

    public static CollectionFragment getSearchInstance(Constant.Pay_Type type,String keyWord) {
        CollectionFragment fragment = new CollectionFragment();
        Bundle arg = new Bundle();
        arg.putSerializable(Constant.PAY_TYPE, type);
        arg.putBoolean(Constant.IS_SEARCH_ORDER_,true);
        arg.putString(Constant.keyWord,keyWord);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            type = (Constant.Pay_Type) bundle.getSerializable(Constant.PAY_TYPE);

        if (bundle!=null){
            isSearch =  bundle.getBoolean(Constant.IS_SEARCH_ORDER_,false);
            keyWord=bundle.getString(Constant.keyWord,"");
        }
    }

    View empty_view;
    SwipeToLoadLayout swipeToLoadLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_list, container, false);
        this.swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        list = (RecyclerView) view.findViewById(R.id.swipe_target);
        list.setLayoutManager(new LinearLayoutManager(container.getContext()));
        adapter = new CollectionAdapter(PayTypeFactory.getInstance());
        list.setAdapter(adapter);
        initData(type);
        empty_view = view.findViewById(R.id.empty_view);
        empty_view.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
        RxBus.getSubscriber().setEvent(Events.FRESH_COLLECTIONLIST)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
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

    public void initAll() {
        GetOrderList(0);
    }

    public void initHas() {
        GetOrderList(2);
    }

    public void initWait() {
        GetOrderList(1);
    }

    PayAction payAction = new PayAction();

    /**
     * 获得订单列表
     * 1 未付款 2已付款 3已取消
     */
    public void GetOrderList(int OrderStatus) {
        Observable<List<Collection>> observable;
        if (isSearch)
            observable=payAction.GetOrderSearchList(keyWord,OrderStatus,PageIndex);
        else
            observable=payAction.GetOrderList(OrderStatus,PageIndex);

        Logger.d("CollectionFragment", "OrderStatus=" + OrderStatus);
        observable
                .subscribe(new RxSubscribe<List<Collection>>() {
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
                    public void onNext(List<Collection> collections) {
                        if (swipeToLoadLayout.isRefreshing()) {
                            //设置下拉刷新结束
                            swipeToLoadLayout.setRefreshing(false);
                        }
                        if (swipeToLoadLayout.isLoadingMore()) {
                            //设置上拉加载更多结束
                            swipeToLoadLayout.setLoadingMore(false);
                        }

                        if (PageIndex!=1){
                            List<Collection> data=adapter.getmDatas();
                            if (collections!=null)
                                data.addAll(collections);
                            collections=data;
                        }

                        if (collections == null || collections.isEmpty()) {
                            empty_view.setVisibility(View.VISIBLE);
                            list.setVisibility(View.GONE);
                        } else {
                            adapter.setmDatas(collections);
                            empty_view.setVisibility(View.GONE);
                            list.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
