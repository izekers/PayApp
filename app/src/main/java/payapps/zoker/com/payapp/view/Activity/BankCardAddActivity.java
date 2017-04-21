package payapps.zoker.com.payapp.view.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zekers.pjutils.BaseApplication;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.PatternUtil;
import com.zoker.base.DialogUtils;

import payapps.zoker.com.payapp.PayApplication;
import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.contract.BankCardAddContract;
import payapps.zoker.com.payapp.control.presenter.BankCardAddPresenter;

/**
 * Created by Administrator on 2017/3/6.
 */

public class BankCardAddActivity extends BaseActivity implements BankCardAddContract.View {
    AbilityToolBar abilityToolBar;
    private EditText bank_name, bank_number, bank_address, bank_phone, verification_view;
    private Button btn_ok, btn_verification;
    BankCardAddContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new BankCardAddPresenter();
        presenter.attachView(this);
        setContentView(R.layout.activity_bankcard_add);
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.bank_account_add));
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initView();
    }

    public void initView() {
        bank_name = (EditText) findViewById(R.id.bank_name);
        bank_number = (EditText) findViewById(R.id.bank_number);
        bank_address = (EditText) findViewById(R.id.bank_address);
        bank_phone = (EditText) findViewById(R.id.bank_phone);
        verification_view = (EditText) findViewById(R.id.verification_view);
        btn_verification = (Button) findViewById(R.id.btn_verification);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        initListener();
    }

    public void initListener() {
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PatternUtil.isBankCard(bank_number.getText().toString())){
                    BaseApplication.Instance.showToast("请上传正确的银行卡信息");
                    return;
                }
                presenter.AddBankCard(bank_address.getText().toString(), bank_number.getText().toString(), bank_phone.getText().toString(), verification_view.getText().toString());
            }
        });
        btn_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = bank_phone.getText().toString();
                if (PatternUtil.isMobileNO(phone)) {
                    presenter.sendMobileCaptcha(phone);
                    timer = verCountDown(30);
                    timer.start();
                } else {
                    PayApplication.Instance.showToast("请输入正确的手机号码");
                }
            }
        });
    }

    Dialog dialog;
    CountDownTimer timer;

    @Override
    public void loading() {
        dialog = DialogUtils.getLoadingDialog(this, "正在添加");
    }

    @Override
    public void success() {
        DialogUtils.success(dialog);
        PayApplication.Instance.showToast("添加成功");
        finish();
    }

    @Override
    public void fail(String msg) {
        DialogUtils.fail(dialog, "添加失败，" + msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null)
            timer.cancel();
    }

    /**
     * 验证码倒计数
     *
     * @param time
     * @return
     */
    public CountDownTimer verCountDown(int time) {
        return timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long l) {
                btn_verification.setText(l / 1000 + "秒");
                btn_verification.setTextColor(getResources().getColor(R.color.gray_9));
                btn_verification.setClickable(false);
            }

            @Override
            public void onFinish() {
                btn_verification.setText("验证码");
                btn_verification.setTextColor(getResources().getColor(R.color.gray_3));
                btn_verification.setClickable(true);
            }
        };
    }
}
