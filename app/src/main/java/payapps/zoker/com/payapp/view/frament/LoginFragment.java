package payapps.zoker.com.payapp.view.frament;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zekers.utils.PatternUtil;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import payapps.zoker.com.payapp.PayApplication;
import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.contract.LoginContract;
import payapps.zoker.com.payapp.control.presenter.LoginPresenter;
import payapps.zoker.com.payapp.control.record.TokenRecord;
import payapps.zoker.com.payapp.view.Activity.ForgetActivity;
import payapps.zoker.com.payapp.view.Activity.MainActivity;
import payapps.zoker.com.payapp.view.Activity.PayActivity;
import payapps.zoker.com.payapp.view.Constant;

/**
 * Created by Administrator on 2017/3/4.
 */

public class LoginFragment extends Fragment implements LoginContract.View {
    private View root_view, btn_go_logup,fogit_view;
    private EditText phone_view, password_view, verification_view;
    private Button btn_login, btn_verification;
    private LoginContract.Presenter presenter;
    private Dialog dialog;
    private CountDownTimer timer;

    public static LoginFragment getInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_login, container, false);
        initView();
        initListener();
        return root_view;
    }

    public void initView() {
        phone_view = (EditText) root_view.findViewById(R.id.phone_view);
        password_view = (EditText) root_view.findViewById(R.id.password_view);
        verification_view = (EditText) root_view.findViewById(R.id.verification_view);
        btn_login = (Button) root_view.findViewById(R.id.btn_login);
        btn_go_logup = root_view.findViewById(R.id.btn_go_logup);
        btn_verification = (Button) root_view.findViewById(R.id.btn_verification);
        fogit_view=root_view.findViewById(R.id.fogit_view);
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
        btn_go_logup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().send(Events.GO_LOGUP, null);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.login(phone_view.getText().toString(), password_view.getText().toString(), verification_view.getText().toString());
            }
        });
        fogit_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), ForgetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    @Override
    public void loging() {
        dialog = DialogUtils.getLoadingDialog(getContext(), "正在登陆");
    }

    @Override
    public void success() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void fail(String msg) {
        DialogUtils.fail(dialog, "登陆失败，"+msg);
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
