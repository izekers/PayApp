package payapps.zoker.com.payapp.view.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.pjutils.BaseApplication;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.PatternUtil;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/6.
 */

public class CompanyInfoActivity extends BaseActivity {
    private AbilityToolBar abilityToolBar;
    private EditText company_name, company_number, company_address, company_phone, company_des;
    private PayAction payAction;
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        payAction = new PayAction();
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        company_name = (EditText) findViewById(R.id.company_name);
        company_number = (EditText) findViewById(R.id.company_number);
        company_address = (EditText) findViewById(R.id.company_address);
        company_phone = (EditText) findViewById(R.id.company_phone);
        company_des = (EditText) findViewById(R.id.company_des);
        abilityToolBar.setTitle(getString(R.string.company_info));
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        abilityToolBar.setMenuRes(R.menu.ok_menu);
        abilityToolBar.setOnMenuItemClickListener(new AbilityToolBar.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.ok:
                        dialog = DialogUtils.getLoadingDialog(CompanyInfoActivity.this, "正在提交");
                        payAction.UpdateCompanyBasicInfo(company_name.getText().toString(), company_number.getText().toString(), company_address.getText().toString(), company_phone.getText().toString(), company_des.getText().toString())
                                .subscribe(new RxSubscribe<DataWrapper>() {
                                    @Override
                                    public void onError(String message) {
                                        DialogUtils.fail(dialog, "提交失败," + message);
                                    }

                                    @Override
                                    public void onNext(DataWrapper s) {
                                        DialogUtils.success(dialog, "提交成功");
                                        RxBus.getInstance().send(Events.FRESH_USERINFO, null);
                                        finish();
                                    }
                                });
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    User user;

    private void initData() {
        user = UserRecord.getInstance().load();
        company_name.setText(user.getCompanyName());
        String number = user.getBusinessLicenseNumber() == null ? "" : user.getBusinessLicenseNumber();
        company_number.setText(number);
        company_address.setText(user.getBusinessAddress());
        company_phone.setText(user.getTelephone());
        company_des.setText(user.getCompanyProfile());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.destroy(dialog);
    }
}
