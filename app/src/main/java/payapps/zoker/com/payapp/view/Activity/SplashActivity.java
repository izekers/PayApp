package payapps.zoker.com.payapp.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.record.TokenRecord;

/**
 * Created by Administrator on 2017/3/9.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (TokenRecord.getInstance().load() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
