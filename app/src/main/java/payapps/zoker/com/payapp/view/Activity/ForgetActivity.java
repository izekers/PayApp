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
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import payapps.zoker.com.payapp.PayApplication;
import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.contract.ForgetContract;
import payapps.zoker.com.payapp.control.contract.LoginContract;
import payapps.zoker.com.payapp.control.presenter.ForgetPresenter;
import payapps.zoker.com.payapp.control.presenter.LoginPresenter;

/**
 * Created by Administrator on 2017/4/12.
 */

public class ForgetActivity extends BaseActivity implements ForgetContract.View{
    private View  btn_go_logup;
    private EditText phone_view, password_view, verification_view;
    private Button btn_login, btn_verification;
    private ForgetContract.Presenter presenter;
    private Dialog dialog;
    private CountDownTimer timer;
    private AbilityToolBar abilityToolBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forget);
        presenter = new ForgetPresenter();
        presenter.attachView(this);
        initView();
        initListener();
    }

    public void initView() {
        abilityToolBar=(AbilityToolBar)findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle("修改密码");
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        phone_view = (EditText) findViewById(R.id.phone_view);
        password_view = (EditText) findViewById(R.id.password_view);
        verification_view = (EditText) findViewById(R.id.verification_view);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_go_logup = findViewById(R.id.btn_go_logup);
        btn_verification = (Button)findViewById(R.id.btn_verification);
    }

    public void initListener() {
        btn_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phone_view.getText().toString();
                if (PatternUtil.isMobileNO(phone)) {
                    presenter.sendMobileCaptcha(phone);
                    timer = verCountDown(30);
                    timer.start();
                } else {
                    PayApplication.Instance.showToast("请输入正确的手机号码");
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.ForgotPassword(phone_view.getText().toString(), password_view.getText().toString(), verification_view.getText().toString());
            }
        });
    }


    @Override
    public void loging() {
        dialog = DialogUtils.getLoadingDialog(this, "正在修改密码");
    }

    @Override
    public void success() {
        BaseApplication.Instance.showToast("修改成功");
        finish();
    }

    @Override
    public void fail(String msg) {
        DialogUtils.fail(dialog, "修改失败，"+msg);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.success(dialog);
        if (timer != null)
            timer.cancel();
    }

    /**
     * 验证码倒计数
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
