package payapps.zoker.com.payapp.view.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zekers.network.base.RxSubscribe;
import com.zekers.network.data.DataWrapper;
import com.zekers.pjutils.BaseApplication;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zoker.base.DialogUtils;

import org.w3c.dom.Text;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.record.TokenRecord;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/6.
 */

public class AccountActivity extends BaseActivity{
    AbilityToolBar abilityToolBar;
    View phone_layout;
    TextView phone_view,id_view;
    EditText old_password,new_password,re_password;
    User user;
    PayAction payAction;
    Button btn_ok;
    private static final int CHANGE_PHONE = 720;
    Dialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        payAction=new PayAction();
        abilityToolBar=(AbilityToolBar)findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle(getString(R.string.account_soft));
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        phone_layout=findViewById(R.id.phone_layout);
        phone_view=(TextView) findViewById(R.id.phone_view);
        id_view=(TextView) findViewById(R.id.id_view);
        old_password=(EditText) findViewById(R.id.old_password);
        new_password=(EditText) findViewById(R.id.new_password);
        re_password=(EditText) findViewById(R.id.re_password);
        btn_ok     =(Button) findViewById(R.id.btn_ok);
        phone_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AccountActivity.this,NewPhoneActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,CHANGE_PHONE);
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String new_password_txt=new_password.getText().toString();
                String old_password_txt=old_password.getText().toString();
                String re_password_txt=re_password.getText().toString();
                try {
                    if (PayAction.getRsaPassword(old_password_txt).equals(TokenRecord.getInstance().load())){
                        BaseApplication.Instance.showToast("原密码输入不正确");
                    }else if (!new_password_txt.equals(re_password_txt)){
                        BaseApplication.Instance.showToast("两次密码输入不一致");
                    }else {
                        dialog= DialogUtils.getLoadingDialog(AccountActivity.this,"正在修改");
                        payAction.UpdatePassword(new_password_txt)
                                .subscribe(new RxSubscribe<DataWrapper>() {
                                    @Override
                                    public void onError(String message) {
                                        DialogUtils.fail(dialog,"密码修改失败，"+ message);
                                    }

                                    @Override
                                    public void onNext(DataWrapper s) {
                                        DialogUtils.success(dialog);
                                        user=UserRecord.getInstance().load();
                                        try {
                                            String token=PayAction.getRsaPassword(new_password_txt+user.getTimeSignature());
                                            TokenRecord.getInstance().save(token);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        BaseApplication.Instance.showToast("密码修改成功");
                                        new_password.setText("");
                                        old_password.setText("");
                                        re_password.setText("");
                                    }
                                });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    BaseApplication.Instance.showToast("未知错误");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void initData(){
        user= UserRecord.getInstance().load();
        phone_view.setText(user.getMobilephone());
        id_view.setText(user.getId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==CHANGE_PHONE){
                initData();
            }
        }
    }
}
