package payapps.zoker.com.payapp.view.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zekers.ui.view.widget.AbilityToolBar;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.presenter.BankCardAddPresenter;

/**
 * Created by Administrator on 2017/3/27.
 */

public class AuthActivity  extends BaseActivity{
    AbilityToolBar abilityToolBar;
    TextView auth_msg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_auth);
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle("提交信息成功");
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        auth_msg=(TextView)findViewById(R.id.auth_msg);
        if (getIntent().getStringExtra("auth")!=null && getIntent().getStringExtra("auth").equals("company"))
            auth_msg.setText("您的营业执照信息已提交认证，请耐心等候。");
    }

}
