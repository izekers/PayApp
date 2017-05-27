package payapps.zoker.com.payapp.view.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.ui.view.widget.SimpleFragmentPagerAdapter;
import com.zekers.utils.logger.Logger;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.view.Constant;
import payapps.zoker.com.payapp.view.frament.CollectionFragment;
import payapps.zoker.com.payapp.view.frament.PayFragment;

/**
 * Created by Administrator on 2017/4/27.
 */

public class SearchOrderActivity extends BaseActivity{
    AbilityToolBar abilityToolBar;
    TabLayout tab;
    ViewPager vp;
    SimpleFragmentPagerAdapter adapter;
    String keyWord="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_order);
        abilityToolBar=(AbilityToolBar)findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle("搜索");
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        keyWord=getIntent().getStringExtra(Constant.keyWord);
        vp = (ViewPager) findViewById(R.id.pay_vp);
        tab = (TabLayout) findViewById(R.id.pay_tab);
        adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        initFragment(adapter);
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);
    }

    public void initFragment(SimpleFragmentPagerAdapter adapter) {
        adapter.addFragment(PayFragment.getSearchInstance(Constant.Pay_Type.ALL,keyWord), "付款单");
        adapter.addFragment(CollectionFragment.getSearchInstance(Constant.Pay_Type.ALL,keyWord), "收款单");
    }
}
