package payapps.zoker.com.payapp.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;

import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.ui.view.widget.SimpleFragmentPagerAdapter;
import com.zekers.utils.logger.Logger;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.view.Constant;
import payapps.zoker.com.payapp.view.frament.CollectionFragment;
import payapps.zoker.com.payapp.view.frament.PayFragment;

/**
 * Created by Zoker on 2017/3/2.
 */

public class PayActivity extends BaseActivity {
    TabLayout tab;
    ViewPager vp;
    SimpleFragmentPagerAdapter adapter;
    AbilityToolBar abilityToolBar;
    private Constant.Trad_Type type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        type = (Constant.Trad_Type) getIntent().getSerializableExtra(Constant.TRAD_TYPE);
        initView();
    }

    public void initView() {
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        if (type == Constant.Trad_Type.PAY) {
            abilityToolBar.setMenuRes(R.menu.pay_menu);
            abilityToolBar.setTitle("付款订单");
            abilityToolBar.setOnMenuItemClickListener(new AbilityToolBar.OnMenuItemClickListener() {
                @Override
                public void onMenuItemClick(View view) {
                    Logger.d("payactivity","进来了");
                    Logger.d("payactivity","id="+view.getId());
                    int id = view.getId();
                    switch (id) {
                        case R.id.search:
                            abilityToolBar.showSearchBar();
                            break;
                        case R.id.add:
                            Intent intent = new Intent(PayActivity.this, CollectionActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            });
        } else {
            abilityToolBar.setMenuRes(R.menu.collection_menu);
            abilityToolBar.setTitle("收款订单");
            abilityToolBar.setOnMenuItemClickListener(new AbilityToolBar.OnMenuItemClickListener() {
                @Override
                public void onMenuItemClick(View view) {
                    Logger.d("payactivity","进来了");
                    Logger.d("payactivity","id="+view.getId());
                    int id = view.getId();
                    switch (id) {
                        case -1:
                        case R.id.search:
                            abilityToolBar.showSearchBar();
                            break;
                        case R.id.add:
                            Intent intent = new Intent(PayActivity.this, CollectionActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            });
        }
        abilityToolBar.setSearchListener(new AbilityToolBar.SearchListener() {
            @Override
            public void search(EditText editText, String keyWord) {
                Intent intent=new Intent(PayActivity.this, SearchOrderActivity.class);
                intent.putExtra(Constant.keyWord,keyWord);
                startActivity(intent);
                abilityToolBar.hideSearchBar();
            }
        });

        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        vp = (ViewPager) findViewById(R.id.pay_vp);
        tab = (TabLayout) findViewById(R.id.pay_tab);
        adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        if (type == Constant.Trad_Type.PAY) {
            initPayFragment(adapter);
        } else {
            initCollectionFragment(adapter);
        }
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);
    }

    public void initPayFragment(SimpleFragmentPagerAdapter adapter) {
        adapter.addFragment(PayFragment.getInstance(Constant.Pay_Type.ALL), getString(R.string.payment_order_all));
        adapter.addFragment(PayFragment.getInstance(Constant.Pay_Type.WAIT), getString(R.string.payment_order_wait));
        adapter.addFragment(PayFragment.getInstance(Constant.Pay_Type.HAS), getString(R.string.payment_order_has));
    }

    public void initCollectionFragment(SimpleFragmentPagerAdapter adapter) {
        adapter.addFragment(CollectionFragment.getInstance(Constant.Pay_Type.ALL), getString(R.string.collection_order_all));
        adapter.addFragment(CollectionFragment.getInstance(Constant.Pay_Type.WAIT), getString(R.string.collection_order_wait));
        adapter.addFragment(CollectionFragment.getInstance(Constant.Pay_Type.HAS), getString(R.string.collection_order_has));
    }
}
