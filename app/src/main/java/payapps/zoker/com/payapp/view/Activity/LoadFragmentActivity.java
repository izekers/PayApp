package payapps.zoker.com.payapp.view.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.view.Constant;
import payapps.zoker.com.payapp.view.frament.MyInfoFragment;
import payapps.zoker.com.payapp.view.frament.RecordsFragment;

/**
 * Created by Administrator on 2017/3/4.
 */

public class LoadFragmentActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_fragment);
        Constant.Fragment_Type type=(Constant.Fragment_Type)getIntent().getSerializableExtra(Constant.FRAGMENT_TYPE);
        Fragment fragment=null;
        switch (type){
            case USER:
                fragment=MyInfoFragment.getInstance(false);
                break;
            case RECORD:
                fragment=RecordsFragment.getInstance(false);
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.root_view,fragment).commit();
    }
}
