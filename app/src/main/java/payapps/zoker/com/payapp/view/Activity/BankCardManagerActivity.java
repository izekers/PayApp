package payapps.zoker.com.payapp.view.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.ui.view.recycler.RecyclerWrapView;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;

import java.util.ArrayList;
import java.util.List;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.model.BankCard;
import payapps.zoker.com.payapp.model.BankCardList;
import payapps.zoker.com.payapp.view.adapter.BankCardAdapter;
import payapps.zoker.com.payapp.view.adapter.GoodsAdapter;
import payapps.zoker.com.payapp.view.adapter.PayTypeFactory;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/3/6.
 */

public class BankCardManagerActivity extends BaseActivity{
    AbilityToolBar abilityToolBar;
    BankCardAdapter bankCardAdapter;
    RecyclerWrapView rv_list;
    View empty_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankcard_manager);
        abilityToolBar=(AbilityToolBar)findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.bank_account_management));
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        abilityToolBar.setMenuRes(R.menu.add_menu);
        abilityToolBar.setOnMenuItemClickListener(new AbilityToolBar.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.add:
                        Intent intent = new Intent(BankCardManagerActivity.this, BankCardAddActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        RxBus.getSubscriber().setEvent(Events.FRESH_BANKCARDLIST)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        GetBankCardList();
                    }
                })
                .create();
        empty_view=findViewById(R.id.empty_view);
        empty_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BankCardManagerActivity.this, BankCardAddActivity.class);
                startActivity(intent);
            }
        });
        rv_list=(RecyclerWrapView) findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        bankCardAdapter=new BankCardAdapter(PayTypeFactory.getInstance());
        rv_list.setAdapter(bankCardAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();


//        rv_list.setVisibility(View.VISIBLE);
//        empty_view.setVisibility(View.GONE);
//        List<BankCard> bankCards=new ArrayList<>();
//        bankCards.add(new BankCard());
//        bankCardAdapter.setmDatas(bankCards);
//        rv_list.getAdapter().notifyDataSetChanged();
        GetBankCardList();
    }

    PayAction payAction=new PayAction();
    public void GetBankCardList(){
        payAction.GetBankCardList()
                .subscribe(new RxSubscribe<BankCardList>() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onNext(BankCardList bankCardList) {
                        if (bankCardList==null||bankCardList.getBankCardList()==null || bankCardList.getBankCardList().isEmpty()){
                            rv_list.setVisibility(View.GONE);
                            empty_view.setVisibility(View.VISIBLE);
                            return;
                        }
                        rv_list.setVisibility(View.VISIBLE);
                        empty_view.setVisibility(View.GONE);
                        bankCardAdapter.setmDatas(bankCardList.getBankCardList());
                        rv_list.getAdapter().notifyDataSetChanged();
                    }
                });
    }
}
