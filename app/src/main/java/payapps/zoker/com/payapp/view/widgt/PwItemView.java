package payapps.zoker.com.payapp.view.widgt;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zekers.ui.view.widget.KVItemView;

import payapps.zoker.com.payapp.R;

/**
 * Created by Zoker on 2017/3/2.
 */

public class PwItemView extends KVItemView{
    private TextView fgText;
    public PwItemView(Context context) {
        super(context);
    }

    public PwItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PwItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PwItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void initView(AttributeSet attrs) {
        super.initView(attrs);
        getInputView().setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        getValueView().setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        fgText=new TextView(getContext());
        fgText.setTextColor(getContext().getResources().getColor(com.zekers.ui.R.color.gray_f2));
        fgText.setText(getContext().getResources().getString(R.string.forget_passwrod));
        this.addView(fgText);
    }
}
