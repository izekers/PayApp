package com.zekers.network.action;

import android.util.Log;

import com.zekers.network.api.ConfigApi;
import com.zekers.network.base.HttpMethod;
import com.zekers.network.exception.MethodException;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/23.
 */

public class ConfigHttpAction {
    private ConfigApi service;
    private final String TAG = this.getClass().getSimpleName();
    {
        service= HttpMethod.getService(HttpMethod.API_HTTP,ConfigApi.class);
        if (service==null)
            throw new MethodException(MethodException.API_NULL);
    }

    public Observable<String> getItem(String token, String key){
        Log.i(TAG,"getItem token="+token +",key="+key);
//        token,key,"android"
        return service.getItem();
    }

}
