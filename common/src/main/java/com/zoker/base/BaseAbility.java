package com.zoker.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 *
 * Created by Zoker on 2017/2/21.
 */
public class BaseAbility extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        closeSoftKeybord(((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0),this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        closeSoftKeybord(((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0),this);
    }

    @Override
    protected void onStart() {
        closeSoftKeybord(((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0),this);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        closeSoftKeybord(((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0),this);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        hintKbTwo();
        closeSoftKeybord(((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0),this);
        super.onStop();
    }

    @Override
    public void finish() {
        hintKbTwo();
        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        getWindow().addFlags(flags);
        closeSoftKeybord(((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0),this);
        super.finish();
    }

    public static void closeSoftKeybord(View mEditText, Context mContext) {
        View view=null;
        if (view==null)
             return;
        if (mEditText==null)
            return;
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    //此方法只是关闭软键盘
    private void hintKbTwo() {

        if(getCurrentFocus()!=null)
        {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus()
                            .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
