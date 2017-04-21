package com.zekers.utils.logger;

import android.content.Context;

import java.util.Map;

/**
 * Created by Administrator on 2017/1/24.
 */

public class EmptyCollection implements IEventCollection,IErrorCollection{
    @Override
    public void onEvent(Context context, String eventId) {

    }

    @Override
    public void onEvent(Context context, String eventId, Map<String, String> map) {

    }

    @Override
    public void onEventValue(Context context, String id, Map<String, String> m, int du) {

    }

    @Override
    public void reportError(Context context, String error) {

    }

    @Override
    public void reportError(Context context, Throwable e) {

    }
}
