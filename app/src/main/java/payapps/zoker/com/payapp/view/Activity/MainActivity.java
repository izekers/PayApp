package payapps.zoker.com.payapp.view.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.zekers.ui.view.widget.SimpleFragmentPagerAdapter;
import com.zekers.utils.CommonUtils;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.view.Activity.BaseActivity;
import payapps.zoker.com.payapp.view.frament.AddBookFragment;
import payapps.zoker.com.payapp.view.frament.MainFragment;
import payapps.zoker.com.payapp.view.frament.MyInfoFragment;
import payapps.zoker.com.payapp.view.frament.RecordsFragment;

public class MainActivity extends BaseActivity {
    BottomNavigationBar mBottomNavigationBar;
    ViewPager vp;
    SimpleFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        RxBus.getInstance().send(Events.FRESH_USERINFO, null);
        if (CommonUtils.isMarshmallow())
            checkPermisstionAndrThenLoad(Manifest.permission.WRITE_EXTERNAL_STORAGE, null);
        if (getIntent().getStringExtra("type") != null && getIntent().getStringExtra("type").equals("logup")) {
            startActivity(new Intent(MainActivity.this, RealNameActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RxBus.getInstance().send(Events.FRESH_USERINFO,null);
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.main_vp);
        adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        initFragment(adapter);
        vp.setAdapter(adapter);

        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.setBarBackgroundColor(R.color.gray_f2);
        mBottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.home, R.string.home).setInactiveIcon(getResources().getDrawable(R.drawable.home_no)))
                .addItem(new BottomNavigationItem(R.drawable.trade, R.string.transaction_records).setInactiveIcon(getResources().getDrawable(R.drawable.trade_no)))
                .addItem(new BottomNavigationItem(R.drawable.addbook, R.string.the_address_book).setInactiveIcon(getResources().getDrawable(R.drawable.addbook_no)))
                .addItem(new BottomNavigationItem(R.drawable.my, R.string.my).setInactiveIcon(getResources().getDrawable(R.drawable.my_no)))//依次添加item,分别icon和名称
                .setFirstSelectedPosition(0)//设置默认选择item
                .initialise();//初始化
    }

    public void initFragment(SimpleFragmentPagerAdapter adapter) {
        adapter.addFragment(MainFragment.getInstance(), "主页");
        adapter.addFragment(RecordsFragment.getInstance(true), "订单");
//        adapter.addFragment(RecordsFragment.getInstance(),"通信录");
        adapter.addFragment(AddBookFragment.getInstance(true), "通信录");
        adapter.addFragment(MyInfoFragment.getInstance(true), "我的");
    }

    public void initListener() {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomNavigationBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                vp.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

}
