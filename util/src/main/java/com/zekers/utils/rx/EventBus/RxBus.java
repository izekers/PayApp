package com.zekers.utils.rx.EventBus;

import com.google.gson.Gson;
import com.zekers.utils.logger.Logger;

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;
import rx.subscriptions.CompositeSubscription;

/**
 * 基础的RxBus
 * Created by Zekers on 2016/9/22.
 */
public class RxBus {
    private static RxBus rxBus;
    private final Subject<Events<?>, Events<?>> _bus = new SerializedSubject<>(PublishSubject.<Events<?>>create());
    private HashMap<String, CompositeSubscription> mSubscriptionMap;
    private RxBus() {}

    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    /**
     * 旧版本的
     */
//    public void send(Object o) {
//        _bus.onNext(o);
//    }

    /**
     * 发送事件
     * @param o
     */
    private void send(Events<?> o) {
        _bus.onNext(o);
    }
    public void send(@Events.EventCode int code, Object content) {
        Events<Object> event = new Events<>();
        event.code = code;
        event.content = content;
        send(event);
    }

    /**
     * 返回Observable实例
     * @return
     */
    public Observable<Events<?>> toObservable() {
        return _bus;
    }

    //code的第二种方案，目的是提高自由度
    public Observable<Events<?>> toObservable(final @Events.EventCode int event){
        return _bus .filter(new Func1<Events<?>, Boolean>() {
            @Override
            public Boolean call(Events<?> events) {
                return events.code == event;
            }
        });
    }

    /**
     * 是否已有观察者订阅
     * @return
     */
    public boolean hasObservers() {
        return _bus.hasObservers();
    }

    /**
     * 保存订阅后的subscription
     * 用于之后的取消
     * @param key
     * @param subscription
     */
    public void addSubscription(String key, Subscription subscription) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }

        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(subscription);
        } else {
            CompositeSubscription compositeSubscription = new CompositeSubscription();
            compositeSubscription.add(subscription);
            mSubscriptionMap.put(key, compositeSubscription);
        }
    }
    /**
     * 保存订阅后的subscription
     * 用于之后的取消
     * @param o
     * @param subscription
     */
    public void addSubscription(Object o, Subscription subscription) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(subscription);
        } else {
            CompositeSubscription compositeSubscription = new CompositeSubscription();
            compositeSubscription.add(subscription);
            mSubscriptionMap.put(key, compositeSubscription);
        }
    }

    /**
     * 取消订阅
     * @param o
     */
    public void unSubscribe(Object o) {
        if (mSubscriptionMap == null) {
            return;
        }

        String key = o.getClass().getName();
        if (!mSubscriptionMap.containsKey(key)){
            return;
        }
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).unsubscribe();
        }

        mSubscriptionMap.remove(key);
    }




    public static SubscriberBuilder getSubscriber(){
        return new SubscriberBuilder();
    }

    public static class SubscriberBuilder {
        private int event;
        private Action1<? super Events<?>> onNext;
        private Action1<Throwable> onError;
        public Subscription create() {
            return _created();
        }


        public SubscriberBuilder setEvent(@Events.EventCode int event) {
            this.event = event;
            return this;
        }

        private Subscription _created() {
            return RxBus.getInstance().toObservable()
                    .filter(new Func1<Events<?>, Boolean>() {
                        @Override
                        public Boolean call(Events<?> events) {
                            Logger.d("get");
                            Logger.d(new Gson().toJson(events));
                            Logger.d(new Gson().toJson(event));
                            return events.code == event;
                        }
                    })   //过滤 根据code判断返回事件
                    .subscribe(onNext, onError == null ? new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.d("err");
                            throwable.printStackTrace();
                        }
                    } : onError);
        }
        public SubscriberBuilder onNext(Action1<? super Events<?>> action) {
            this.onNext = action;
            return this;
        }

        public SubscriberBuilder onError(Action1<Throwable> action) {
            this.onError = action;
            return this;
        }
    }
}
