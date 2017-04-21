package payapps.zoker.com.payapp.view.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zekers.ui.view.widget.AbilityToolBar;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/22.
 */

public class PaySuccActivity extends BaseActivity{
    AbilityToolBar abilityToolBar;
    TextView username;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_succ);
        abilityToolBar=(AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.payment_order_success));
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        username=(TextView) findViewById(R.id.user_name);
        initData();
    }
    private void initData(){
        user= UserRecord.getInstance().load();
        username.setText("尊敬的"+user.getUserName());
    }
}
