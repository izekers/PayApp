package com.zekers.utils.rx.EventBus;

import android.support.annotation.IntDef;

import com.google.gson.Gson;
import com.zekers.utils.logger.Logger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Event管家
 * Created by zekers on 2016/9/22.
 */
public class Events<T> {

    //所有事件的CODE
    public static final int First = 1; //点击事件
    public static final int Second = 21; //其它事件
    public static final int Results = 22; //其它事件

    public static final int GO_LOGUP=2;
    public static final int GO_LOGIN=3;
    public static final int LOGIN_SUCC=4;
    public static final int LOGOUT_SUCC=5;
    public static final int LOGIN_MISS=8;
    public static final int FRESH_USERINFO = 6;
    public static final int FRESH_GOODLIST=9;
    public static final int FRESH_BANKCARDLIST=10;
    public static final int FRESH_ADDBANKS=11;
    public static final int FRESH_PAYLIST=12;
    public static final int FRESH_COLLECTIONLIST=13;
    public static final int SELECT_CONTACTMAN=14;
    public static final int MediaUpdate=7;
    public static final int CONTACT_DEL_NO_SHOW=15;


    //枚举
    @IntDef({First, Second ,Results ,GO_LOGUP ,GO_LOGIN ,LOGIN_SUCC,LOGOUT_SUCC,LOGIN_MISS,FRESH_USERINFO ,FRESH_GOODLIST,FRESH_BANKCARDLIST,FRESH_ADDBANKS,FRESH_PAYLIST,FRESH_COLLECTIONLIST,SELECT_CONTACTMAN,MediaUpdate,CONTACT_DEL_NO_SHOW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventCode {}


    public @Events.EventCode int code;
    public T content;

    public static <O> Events<O> setContent(O t) {
        Events<O> events = new Events<>();
        events.content = t;
        return events;
    }

    public <T> T getContent() {
        Logger.d(new Gson().toJson(content));
        return (T) content;
    }

}