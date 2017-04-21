package payapps.zoker.com.payapp.view.frament;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.ui.view.recycler.RecyclerWrapView;
import com.zekers.ui.view.recycler.VisitableTypeControl;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.Records;
import payapps.zoker.com.payapp.view.Activity.CollectionActivity;
import payapps.zoker.com.payapp.view.Activity.PayActivity;
import payapps.zoker.com.payapp.view.Constant;
import payapps.zoker.com.payapp.view.adapter.PayTypeFactory;
import payapps.zoker.com.payapp.view.adapter.RecordsAdapter;

/**
 * Created by Zoker on 2017/3/2.
 */
public class RecordsFragment extends Fragment {
    View view;
    RecyclerView recyclerWrapView;
    AbilityToolBar abilityToolBar;
    //    RecordsAdapter adapter;
    RecordsAdapter adapter;
    boolean isHideback = true;
    PayAction payAction = new PayAction();
    View empty_view;
    public static RecordsFragment getInstance(boolean isHideback) {
        RecordsFragment fragment = new RecordsFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constant.IS_HIDE_BACK, isHideback);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isHideback = getArguments().getBoolean(Constant.IS_HIDE_BACK);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_records, container, false);
        recyclerWrapView = (RecyclerView) view.findViewById(R.id.rw_list);
        adapter = new RecordsAdapter(PayTypeFactory.getInstance());
        recyclerWrapView.setAdapter(adapter);
        recyclerWrapView.setLayoutManager(new LinearLayoutManager(getContext()));
        abilityToolBar = (AbilityToolBar) view.findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getContext().getResources().getString(R.string.transaction_records));
        abilityToolBar.setMenuRes(R.menu.trade_menu);
        abilityToolBar.setOnMenuItemClickListener(new AbilityToolBar.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.search:
                        break;
                    case R.id.ok:
//                        Intent intent = new Intent(getContext(), CollectionActivity.class);
//                        startActivity(intent);
                        break;
                }
            }
        });
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        if (isHideback)
            abilityToolBar.hideBackView();
        empty_view=view.findViewById(R.id.empty_view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetTransactionList();
    }

    int pagIndex = 1;

    void GetTransactionList() {
        payAction.GetTransactionList(pagIndex)
                .subscribe(new RxSubscribe<List<Records>>() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onNext(List<Records> s) {
                        if (s==null || s.isEmpty()){
                            empty_view.setVisibility(View.VISIBLE);
                            recyclerWrapView.setVisibility(View.GONE);
                        }else {
                            Logger.d("这里");
                            empty_view.setVisibility(View.GONE);
                            recyclerWrapView.setVisibility(View.VISIBLE);
                            adapter.setmDatas(s);
                        }
                    }
                });
    }
}
