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
import android.widget.Toast;

import com.zekers.pjutils.BaseApplication;
import com.zekers.utils.PatternUtil;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import payapps.zoker.com.payapp.PayApplication;
import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.contract.LogupContract;
import payapps.zoker.com.payapp.control.presenter.LogupPresenter;
import payapps.zoker.com.payapp.view.Activity.MainActivity;

import static payapps.zoker.com.payapp.R.*;

/**
 * Created by Administrator on 2017/3/4.
 */

public class LogupFragment extends Fragment implements LogupContract.View {
    View root_view, btn_go_login;
    EditText phone_view, password_view, verification_view;
    Button btn_logup, btn_verification;
    LogupContract.Presenter presenter;
    CountDownTimer timer;
    private Dialog dialog;

    public static LogupFragment getInstance() {
        LogupFragment logupFragment = new LogupFragment();
        return logupFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LogupPresenter();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root_view = inflater.inflate(layout.fragment_logup, container, false);
        initView();
        initListener();
        return root_view;
    }

    public void initView() {
        phone_view = (EditText) root_view.findViewById(id.phone_view);
        password_view = (EditText) root_view.findViewById(id.password_view);
        verification_view = (EditText) root_view.findViewById(id.verification_view);
        btn_logup = (Button) root_view.findViewById(id.btn_logup);
        btn_go_login = root_view.findViewById(id.btn_go_login);
        btn_verification = (Button) root_view.findViewById(id.btn_verification);
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
        btn_go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().send(Events.GO_LOGIN, null);
            }
        });
        btn_logup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.logup(phone_view.getText().toString(), password_view.getText().toString(), verification_view.getText().toString());
            }
        });
    }

    public CountDownTimer verCountDown(int time) {
        return timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long l) {
                Logger.d("LogupFragment", "time=" + l / 1000);
                btn_verification.setText(l / 1000 + "秒");
                btn_verification.setTextColor(getResources().getColor(color.gray_9));
                btn_verification.setClickable(false);
            }

            @Override
            public void onFinish() {
                btn_verification.setText("验证码");
                btn_verification.setTextColor(getResources().getColor(color.gray_3));
                btn_verification.setClickable(true);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null)
            timer.cancel();
    }

    @Override
    public void success() {
        DialogUtils.success(dialog);
        BaseApplication.Instance.showToast("注册成功");
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra("type","logup");
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void loging() {
        dialog = DialogUtils.getLoadingDialog(getContext(), "正在注册");
    }

    @Override
    public void fail(String msg) {
        DialogUtils.fail(dialog, "注册失败，"+msg);
    }
}
