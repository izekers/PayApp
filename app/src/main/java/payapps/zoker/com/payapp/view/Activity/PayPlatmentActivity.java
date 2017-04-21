package payapps.zoker.com.payapp.view.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zekers.ui.view.widget.AbilityToolBar;

import payapps.zoker.com.payapp.R;

/**
 * Created by Administrator on 2017/3/6.
 */

public class PayPlatmentActivity extends BaseActivity{
    AbilityToolBar abilityToolBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_platment);
        abilityToolBar=(AbilityToolBar)findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.about));
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
