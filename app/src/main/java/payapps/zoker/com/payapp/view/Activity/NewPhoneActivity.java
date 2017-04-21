package payapps.zoker.com.payapp.view.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.PatternUtil;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import payapps.zoker.com.payapp.PayApplication;
import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.contract.NewPhoneContract;
import payapps.zoker.com.payapp.control.presenter.NewPhonePresenter;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/6.
 */

public class NewPhoneActivity extends BaseActivity implements NewPhoneContract.View {
    AbilityToolBar abilityToolBar;
    EditText new_phone, verification_view;
    Button btn_ok, btn_verification;
    NewPhoneContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_change);
        presenter = new NewPhonePresenter();
        presenter.attachView(this);
        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        new_phone = (EditText) findViewById(R.id.new_phone);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_verification = (Button) findViewById(R.id.btn_verification);
        verification_view = (EditText) findViewById(R.id.verification_view);
        initListener();
    }

    public void initListener() {
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.UpdateMobilephone(new_phone.getText().toString(), verification_view.getText().toString());
            }
        });
        btn_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = new_phone.getText().toString();
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

    @Override
    public void uploading() {
        dialog = DialogUtils.getLoadingDialog(this, "正在登陆");
    }

    Dialog dialog;

    @Override
    public void success() {
        RxBus.getInstance().send(Events.FRESH_USERINFO,null);
        DialogUtils.success(dialog);
        PayApplication.Instance.showToast("修改成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void fail(String msg) {
        DialogUtils.fail(dialog, "登陆失败，" + msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.destroy(dialog);
        if (timer != null)
            timer.cancel();
    }

    CountDownTimer timer;

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
