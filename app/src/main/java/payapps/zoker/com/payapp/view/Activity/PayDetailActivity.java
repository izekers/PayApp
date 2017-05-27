package payapps.zoker.com.payapp.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zekers.ui.view.widget.AbilityToolBar;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.model.Records;
import payapps.zoker.com.payapp.view.Constant;

/**
 * 订单记录->点击按钮
 * Created by Administrator on 2017/3/4.
 */

public class PayDetailActivity extends BaseActivity{
    AbilityToolBar abilityToolBar;
    Records records;
    TextView trade_time,trade_type,trade_no,trade_account,trade_company,trade_man;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        abilityToolBar=(AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.payment_order_success));
        abilityToolBar.setMenuRes(R.menu.paydetail_menu);
        abilityToolBar.setOnMenuItemClickListener(new AbilityToolBar.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.search:
                        abilityToolBar.showSearchBar();
                        break;
                }
            }
        });
        abilityToolBar.setSearchListener(new AbilityToolBar.SearchListener() {
            @Override
            public void search(EditText editText, String keyWord) {
                Intent intent=new Intent(PayDetailActivity.this, SearchOrderActivity.class);
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
        records=(Records)getIntent().getSerializableExtra("Records");
        trade_time=(TextView) findViewById(R.id.trade_time);
        trade_type=(TextView) findViewById(R.id.trade_type);
        trade_no=(TextView) findViewById(R.id.trade_no);
        trade_account=(TextView) findViewById(R.id.trade_account);
        trade_company=(TextView) findViewById(R.id.trade_company);
        trade_man=(TextView) findViewById(R.id.trade_man);

        trade_time.setText(records.getTransactionTime());
        trade_no.setText(records.getOrderNo());
        trade_company.setText(records.getCompanyName());
        trade_man.setText(records.getUserName());
        trade_account.setText(Math.abs(Float.valueOf(records.getAmount()))+"元");
        String type=Float.valueOf(records.getAmount())>0?"订单收款":"订单付款";
        trade_type.setText(type);
    }

}
