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
import android.widget.Spinner;
import android.widget.TextView;

import com.zekers.ui.view.recycler.RecyclerWrapView;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.ContactUtils;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import payapps.zoker.com.payapp.PayApplication;
import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.contract.AddBookContract;
import payapps.zoker.com.payapp.control.presenter.AddBookPresenter;
import payapps.zoker.com.payapp.model.ContactMan;
import payapps.zoker.com.payapp.model.FirstList;
import payapps.zoker.com.payapp.model.Records;
import payapps.zoker.com.payapp.model.User;
import payapps.zoker.com.payapp.view.Constant;
import payapps.zoker.com.payapp.view.adapter.ContactManAdapter;
import payapps.zoker.com.payapp.view.adapter.PayTypeFactory;
import payapps.zoker.com.payapp.view.adapter.RecordsAdapter;
import rx.functions.Action1;

/**
 * Created by Zoker on 2017/3/2.
 */
public class AddBookFragment extends Fragment implements AddBookContract.View {
    View view;
    RecyclerView recyclerView;
    AbilityToolBar abilityToolBar;
    ContactManAdapter adapter;
    List<ContactMan> contactManList;
    boolean isHideback = true;
    Dialog dialog;
    AddBookContract.Presenter presenter;

    TextView delView;
    boolean isShowDel=false;    //true表示正在编辑，false表示没有
    public static AddBookFragment getInstance(boolean isHideback) {
        AddBookFragment fragment = new AddBookFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constant.IS_HIDE_BACK, isHideback);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AddBookPresenter();
        presenter.attachView(this);
        isHideback = getArguments().getBoolean(Constant.IS_HIDE_BACK);
        RxBus.getSubscriber().setEvent(Events.FRESH_ADDBANKS)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        presenter.GetAddressBook(true);
                    }
                })
                .create();

        RxBus.getSubscriber().setEvent(Events.CONTACT_DEL_NO_SHOW)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        isShowDel=false;
                        delView.setText("编辑");
                        adapter.setDel(isShowDel);
                    }
                })
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_book, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rw_list);
        adapter = new ContactManAdapter(PayTypeFactory.getInstance());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        abilityToolBar = (AbilityToolBar) view.findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getContext().getResources().getString(R.string.the_address_book));
        if (isHideback)
            abilityToolBar.hideBackView();
        abilityToolBar.setMenuRes(R.menu.del_menu);
        abilityToolBar.setOnMenuItemClickListener(new AbilityToolBar.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view) {
                isShowDel=adapter.setDel(!isShowDel);
                if (isShowDel)
                    delView.setText("完成");
                else
                    delView.setText("编辑");
            }
        });
        delView=(TextView) abilityToolBar.getView(R.id.delete);
        presenter.GetAddressBook(false);
        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        RxBus.getInstance().send(Events.CONTACT_DEL_NO_SHOW,null);
    }

    @Override
    public void success(List<ContactMan> data) {
        DialogUtils.success(dialog, "获取通讯录成功");
        isShowDel=false;
        adapter.setmDatas(data);
        RxBus.getInstance().send(Events.CONTACT_DEL_NO_SHOW,null);
    }

    @Override
    public void fail(String msg) {
        DialogUtils.fail(dialog, "获取通讯录失败，" + msg);
        RxBus.getInstance().send(Events.CONTACT_DEL_NO_SHOW,null);
    }


    @Override
    public void loading() {
        dialog = DialogUtils.getLoadingDialog(getContext(), "正在加载通讯录");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.destroy(dialog);
    }
}
