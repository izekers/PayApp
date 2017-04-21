package payapps.zoker.com.payapp.view.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.record.TokenRecord;
import payapps.zoker.com.payapp.view.Activity.BaseActivity;
import payapps.zoker.com.payapp.view.frament.LoginFragment;
import payapps.zoker.com.payapp.view.frament.LogupFragment;
import rx.functions.Action1;

/**
 * Created by Zoker on 2017/3/1.
 */

public class LoginActivity extends BaseActivity {
    LoginFragment loginFragment;
    LogupFragment logupFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initFragment();
        loadFragment(loginFragment);
        RxBus.getInstance().addSubscription(this,RxBus.getSubscriber().setEvent(Events.GO_LOGIN)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        loadFragment(loginFragment);
                    }
                }).create());
        RxBus.getInstance().addSubscription(this,RxBus.getSubscriber().setEvent(Events.GO_LOGUP)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        loadFragment(logupFragment);
                    }
                }).create());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubscribe(this);
    }

    public void initFragment(){
        loginFragment=LoginFragment.getInstance();
        logupFragment=LogupFragment.getInstance();
    }

    public void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.login_fl,fragment).commit();
    }
}
