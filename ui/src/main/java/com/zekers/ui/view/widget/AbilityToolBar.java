package com.zekers.ui.view.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zekers.ui.R;

import java.util.logging.Logger;

/**
 * 状态栏
 * Created by Administrator on 2016/6/29.
 * how to user
 *    xml->    <include layout="@layout/ablity_toolbar" />
 *    menu->
 *   abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
 *   abilityToolBar.setStyle(abilityToolBar.getTransparentStyle());
 *   ablityToolBar.setOnBackOnClickListener(new View.OnClickListener()});
 */
public class AbilityToolBar extends RelativeLayout {
    public static String TAG=AbilityToolBar.class.getSimpleName();

    public AbilityToolBar(Context context) {
        super(context);
    }

    public AbilityToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbilityToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(View view);
    }

    public OnMenuItemClickListener onMenuItemClickListener;
    private ViewGroup mBackView;
    private ViewGroup mMenuView1;
    private ViewGroup mMenuView2;
    private ViewGroup mMenuMore;
    private Menu mMenu;
    private TextView mTitle;
    private AblityToolBarStyle style;
    private int mMenuRes;
    private SparseArrayCompat<View> view=new SparseArrayCompat<>();
    private View search_bar,search_back;
    private EditText toolbar_search_txt;
    private SearchListener searchListener;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackView = (ViewGroup) findViewById(R.id.toolbar_back);
        mMenuView1 = (ViewGroup) findViewById(R.id.toolbar_menu1);
        mMenuView2 = (ViewGroup) findViewById(R.id.toolbar_menu2);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mMenuMore = (ViewGroup) findViewById(R.id.toolbar_menu_more);
        search_bar=findViewById(R.id.toolbar_search_bar);
        search_back=findViewById(R.id.toolbar_search_back);
        toolbar_search_txt=(EditText)findViewById(R.id.toolbar_search_txt);

        toolbar_search_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //回车键
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    if (searchListener!=null){
                        searchListener.search(toolbar_search_txt,toolbar_search_txt.getText().toString());
                    }
                }
                return true;
            }
        });

        search_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                search_bar.setVisibility(GONE);
            }
        });
        mMenuView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMenuItemClickListener != null) {
                    if (mMenuView1.getChildAt(0).getVisibility() == View.VISIBLE)
                        onMenuItemClickListener.onMenuItemClick(mMenuView1.getChildAt(0));
                    else
                        onMenuItemClickListener.onMenuItemClick(mMenuView1.getChildAt(1));
                }
            }
        });
        mMenuView2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMenuItemClickListener != null)
                    if (mMenuView2.getChildAt(0).getVisibility() == View.VISIBLE)
                        onMenuItemClickListener.onMenuItemClick(mMenuView2.getChildAt(0));
                    else
                        onMenuItemClickListener.onMenuItemClick(mMenuView2.getChildAt(1));
            }
        });

        mMenuMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setStyle(getMainStyle());
    }

    public void showSearchBar(){
        search_bar.setVisibility(VISIBLE);
    }
    public void hideSearchBar(){
        toolbar_search_txt.setText("");
        search_bar.setVisibility(GONE);
    }

    public SearchListener getSearchListener() {
        return searchListener;
    }

    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    public interface SearchListener{
        void search(EditText editText,String keyWord);
    }
    //设置标题
    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    //隐藏返回按钮
    public void hideBackView() {
        mBackView.setVisibility(GONE);
    }

    //设置返回按钮图片
    public void setBackViewIcon(Drawable drawable) {
        ((ImageView) (mBackView.getChildAt(0))).setImageDrawable(drawable);
    }

    public void setmMenuMoreIcon(Drawable drawable){
        ((ImageView) (mMenuMore.getChildAt(0))).setImageDrawable(drawable);
    }


    //设置menu源
    public void setMenuRes(int menuRes) {
        if (menuRes == 0) {
            return;
        }
        mMenu=new MenuBuilder(mMenuMore.getContext());
        ((Activity)mMenuMore.getContext()).getMenuInflater().inflate(menuRes,mMenu);

        Log.i(TAG, "#setMenuRes size=" + mMenu.size()+",isPopMenu="+style.isPopMenu);

        //默认没有PopMenu
        if (style == null || !style.isPopMenu) {
            for (int i = 0; i < mMenu.size(); i++) {
                ViewGroup mMenuView = null;
                boolean isShow=true;
                switch (i){
                    case 0:
                        mMenuView = mMenuView1;
                        break;
                    case 1:
                        mMenuView = mMenuView2;
                        break;
                    default:
                        isShow=false;
                        break;
                }
                if (!isShow) break;
                mMenuView.setVisibility(VISIBLE);
                mMenuView.getChildAt(1).setId(mMenu.getItem(i).getItemId());
                mMenuView.getChildAt(0).setId(mMenu.getItem(i).getItemId());
                if (mMenu.getItem(i).getIcon() != null) {
                    ((ImageView) (mMenuView.getChildAt(0))).setImageDrawable(mMenu.getItem(i).getIcon());
                    mMenuView.getChildAt(1).setVisibility(GONE);
                    view.put(mMenu.getItem(i).getItemId(),mMenuView.getChildAt(0));
                } else {
                    ((TextView) (mMenuView.getChildAt(1))).setText(mMenu.getItem(i).getTitle());
                    mMenuView.getChildAt(0).setVisibility(GONE);
                    view.put(mMenu.getItem(i).getItemId(),mMenuView.getChildAt(1));
                }
                Log.e("AbilityToolBar","id="+mMenu.getItem(i).getItemId()+"text="+mMenuView.getChildAt(1).getId()+"img="+mMenuView.getChildAt(0).getId());
            }
            //显示[更多]按钮
        } else {
            if (mMenu.size()>0) {
                mMenuMore.setVisibility(VISIBLE);
            }
        }
    }

    public View getView(int id){
        Log.e("AbilityToolBar","id="+view.get(id));
            return view.get(id);
    }

    //设置风格
    public void setStyle(AblityToolBarStyle style) {
        this.style = null;
        if (style == null)
            this.style = getMainStyle();
        else
            this.style = style;
        if (this.style.bgColorRes != -1)
            setBackgroundColor(getResources().getColor(this.style.bgColorRes));
        if (this.style.backIconRes != -1)
            ((ImageView) (mBackView.getChildAt(0))).setImageResource(this.style.backIconRes);
        if (this.style.moreIconRes != -1)
            ((ImageView) (mMenuMore.getChildAt(0))).setImageResource(this.style.backIconRes);
        if (this.style.titleColorRes!=-1)
            mTitle.setTextColor(getResources().getColor(this.style.titleColorRes));
        if (this.style.backBtnSize!=-1){
            LayoutParams layoutParams= (LayoutParams) this.mBackView.getChildAt(0).getLayoutParams();
            layoutParams.height=getResources().getDimensionPixelOffset(style.backBtnSize);
            layoutParams.width=getResources().getDimensionPixelOffset(style.backBtnSize);
            this.mBackView.getChildAt(0).setLayoutParams(layoutParams);
        }
    }

    public AblityToolBarStyle getMainStyle() {
        AblityToolBarStyle blueStyle = new AblityToolBarStyle();
        blueStyle.bgColorRes = R.color.main;
        blueStyle.backIconRes = R.drawable.toolbar_back;
        blueStyle.titleColorRes=R.color.white;
        blueStyle.isPopMenu = false;
        return blueStyle;
    }

    //暂时废用
    public AblityToolBarStyle getTransparentStyle() {
        AblityToolBarStyle transparentStyle = new AblityToolBarStyle();
        transparentStyle.backIconRes = R.drawable.toolbar_back;
        transparentStyle.isPopMenu = true;
        transparentStyle.moreIconRes = R.drawable.toolbar_back;
        transparentStyle.titleColorRes=R.color.black;
        transparentStyle.bgColorRes=R.color.transparent;

        transparentStyle.btnSize=R.dimen.toolbar_btn_size2;
        return transparentStyle;
    }

    public class AblityToolBarStyle {
        public int bgColorRes = -1;
        public int backIconRes = -1;
        public int moreIconRes = -1;
        public int titleColorRes = -1;
        public  int backBtnSize=-1;
        public int btnSize=-1;
        //大于三个都默认isPopMenu为true;
        boolean isPopMenu = false;
    }


    //设置监听
    //设置返回按钮点击事件
    public void setOnBackOnClickListener(OnClickListener listener) {
        if (mBackView != null && listener != null) {
            mBackView.setOnClickListener(listener);
        }
    }

    //设置按钮监听
    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        onMenuItemClickListener = listener;
    }
}
