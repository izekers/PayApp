package com.zekers.utils.logger;

import android.content.Context;

import java.util.Map;

/**
 * 事件统计日志
 * Created by zekers on 2016/11/30.
 */
public interface IEventCollection {
    void onEvent(Context context, String eventId);
    void onEvent(Context context, String eventId, Map<String, String> map);
    void onEventValue(Context context, String id, Map<String, String> m, int du);
}
