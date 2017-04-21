package com.zekers.utils.logger;

import android.content.Context;

import java.util.HashMap;

/**
 * 埋点
 * Created by zekers on 2016/11/30.
 */
public class EventCollection {
    private volatile static EventCollection instance;
//    private IEventCollection collection= UmengCollection.getInstance();

    private IEventCollection collection= new EmptyCollection();
    private EventCollection(){

    }
    public static EventCollection getInstance(){
        if (instance==null){
            synchronized (EventCollection.class){
                instance=new EventCollection();
            }
        }
        return instance;
    }

    /**
     * 课程入口位置埋点
     * @param whereIn 入口
     */
    public void schedule(Context context,String whereIn){
        HashMap<String,String> event=new HashMap<>();
        event.put("in",whereIn);
        collection.onEvent(context,"_Schedule",event);
    }

    /**
     * 课程详情入口位置埋点
     * @param whereIn 入口
     */
    public void courseArrange(Context context,String whereIn){
        HashMap<String,String> event=new HashMap<>();
        event.put("in",whereIn);
        collection.onEvent(context,"_CourseArrange",event);
    }

    /**
     * 用户名片入口位置埋点
     */
    public void userCard(Context context,String whereIn){
        HashMap<String,String> event=new HashMap<>();
        event.put("in",whereIn);
        collection.onEvent(context,"_UserCard",event);
    }

    /**
     * 更新软件点击事件埋点
     */
    public void updateApp(Context context,Boolean isUpdate){
        HashMap<String,String> event=new HashMap<>();
        event.put("action",isUpdate?"更新":"取消");
        collection.onEvent(context,"_UpdateApp",event);
    }
}
