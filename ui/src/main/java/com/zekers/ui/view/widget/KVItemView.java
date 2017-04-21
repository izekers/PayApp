package com.zekers.ui.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zekers.ui.R;

/**
 * Created by Zoker on 2017/3/1.
 */
public class KVItemView extends LinearLayout{
    private TextView nameView,valueView;
    private EditText inputView;
    public KVItemView(Context context) {
        super(context);
    }

    public KVItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public KVItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KVItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(attrs);
    }

    public void initView(AttributeSet attrs){
        this.setGravity(Gravity.CENTER);
        this.setOrientation(HORIZONTAL);
        TypedArray typedArray=getContext().obtainStyledAttributes(attrs,R.styleable.KVItemView);
        String name=typedArray.getString(R.styleable.KVItemView_kvname);
        name=name!=null ? name:"name";
        nameView=new TextView(getContext());
        nameView.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.kv_itemview_name_textsize));
        nameView.setGravity(Gravity.CENTER_VERTICAL);
        nameView.setTextColor(getContext().getResources().getColor(R.color.kv_itemview_name_textcolor));
        LinearLayout.LayoutParams nameParams=new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        nameView.setLayoutParams(nameParams);
        nameView.setText(name);

        this.addView(nameView);

        Boolean isInput=typedArray.getBoolean(R.styleable.KVItemView_isinput,false);
        String hint=typedArray.getString(R.styleable.KVItemView_valuehint);
        hint=hint!=null ? hint:"";
        if (isInput){
            inputView=new EditText(getContext());
            inputView.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.kv_itemview_value_textsize));
            inputView.setTextColor(getContext().getResources().getColor(R.color.kv_itemview_value_textcolor));
            LinearLayout.LayoutParams inputParams=new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3);
            inputView.setLayoutParams(inputParams);
            inputView.setHint(hint);
            inputView.setBackgroundResource(0);
            this.addView(inputView);
        }else {
            valueView=new TextView(getContext());
            valueView.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.kv_itemview_value_textsize));
            valueView.setTextColor(getContext().getResources().getColor(R.color.kv_itemview_value_textcolor));
            LinearLayout.LayoutParams valueParams=new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3);
            valueView.setLayoutParams(valueParams);
            valueView.setHint(hint);
            this.addView(valueView);
        }
        typedArray.recycle();
    }

    public TextView getNameView() {
        return nameView;
    }

    public void setNameView(TextView nameView) {
        this.nameView = nameView;
    }

    public TextView getValueView() {
        return valueView;
    }

    public void setValueView(TextView valueView) {
        this.valueView = valueView;
    }

    public EditText getInputView() {
        return inputView;
    }

    public void setInputView(EditText inputView) {
        this.inputView = inputView;
    }
}
