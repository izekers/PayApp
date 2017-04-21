package com.zekers.network.base;

import android.content.Context;

import com.google.gson.Gson;
import com.zekers.network.data.DataWrapper;
import com.zekers.network.exception.ApiException;
import com.zekers.utils.cache.CacheManager;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.network.NetworkUtil;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zekers.utils.rx.RxHelper;

import java.lang.reflect.Type;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Rx相关,添加了网络请求相关封装
 * Created by zekers on 2017/1/24.
 */
public class RxNetHelper extends RxHelper {
    /**
     * 处理请求
     */
    public static  Observable.Transformer<DataWrapper, DataWrapper> handleDataWrapper() {
        return new Observable.Transformer<DataWrapper, DataWrapper>() {
            @Override
            public Observable<DataWrapper> call(Observable<DataWrapper> tObservable) {
                return tObservable.flatMap(new Func1<DataWrapper, Observable<DataWrapper>>() {
                    @Override
                    public Observable<DataWrapper> call(DataWrapper tDataWrapper) {
                        if (tDataWrapper == null)
                            return Observable.error(new ApiException("未知错误"));
                        if (tDataWrapper.success()) {
                            return createData(tDataWrapper);
                        } else if (tDataWrapper.accountFail()){
                            RxBus.getInstance().send(Events.LOGIN_MISS,null);
                            return Observable.error(new ApiException("登录失效，请重新登录"));
                        } else {
                            if (tDataWrapper.getMessage() == null)
                                return Observable.error(new ApiException("未知错误"));
                            else
                                return Observable.error(new ApiException(tDataWrapper.getMessage()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 处理请求
     */
    public static <T> Observable.Transformer<DataWrapper<T>, T> handleResult() {
        return new Observable.Transformer<DataWrapper<T>, T>() {
            @Override
            public Observable<T> call(Observable<DataWrapper<T>> tObservable) {
                return tObservable.flatMap(new Func1<DataWrapper<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(DataWrapper<T> tDataWrapper) {
                        if (tDataWrapper == null)
                            return Observable.error(new ApiException("未知错误"));
                        if (tDataWrapper.success()) {
                            return createData(tDataWrapper.getResult());
                        }else if (tDataWrapper.accountFail()){
                            RxBus.getInstance().send(Events.LOGIN_MISS,null);
                            return Observable.error(new ApiException("登录失效，请重新登录"));
                        }  else {
                            if (tDataWrapper.getMessage() == null)
                                return Observable.error(new ApiException("未知错误"));
                            else
                                return Observable.error(new ApiException(tDataWrapper.getMessage()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 创建处理完数据的请求
     */
    public static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 缓存加载者
     * 可以通过设置重写该加载者修改加载机制
     */
    public interface ICacheLoad<T> {

        T load(Context context, String cacheKey);

        void save(String cacheKey, T t);
    }

    /**
     * 缓存加载者
     * 可以通过设置重写该加载者修改加载机制
     */
    public static abstract class CacheLoad<T> implements ICacheLoad<T> {
        private CacheManager.CacheModel cacheModel = CacheManager.CacheModel.CONFIG_CACHE_MODEL_LONG;
        private CacheManager.CacheStrategy cacheStrategy;

        public abstract Type getType();

        public CacheLoad<T> setCacheModel(CacheManager.CacheModel cacheModel) {
            this.cacheModel = cacheModel;
            return this;
        }

        public CacheLoad<T> setCacheStrategy(CacheManager.CacheStrategy cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }

        public T load(Context context, String cacheKey) {
            String cacheBody = null;
            if (cacheStrategy != null) {
                cacheBody = CacheManager.load(context, cacheKey, cacheStrategy);
                Logger.d("loadfromcache:cacheStrategy:" + cacheBody);
            } else if (cacheModel != null) {
                cacheBody = CacheManager.load(context, cacheKey, cacheModel);
                Logger.d("loadfromcache:cacheModel:" + cacheBody);
            }

            if (cacheBody != null && getType() != null) {
                return new Gson().fromJson(cacheBody, getType());
            }
            return null;
        }

        public void save(String cacheKey, T t) {
            Logger.d("savefromsave");
            CacheManager.save(cacheKey, t);
        }
    }

    /**
     * 缓存优先加载
     *
     * @param context      上下文
     * @param cacheKey     保存路径
     * @param forceRefresh 是否缓存
     * @param fromNetWork  网络请求
     * @param cacheLoad    缓存设置
     */
    public static <T> Observable<T> preCache(final Context context, final String cacheKey, boolean forceRefresh, Observable<T> fromNetWork, final ICacheLoad<T> cacheLoad) {
        Observable<T> fromCache = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                T cache = cacheLoad.load(context, cacheKey);
                if (cache != null) {
                    subscriber.onNext(cache);
                } else {
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        /**
         * 这里的fromNetWork 不需要指定schedule,在handleRequest中已经变换了
         */
        fromNetWork = fromNetWork.map(new Func1<T, T>() {
            @Override
            public T call(T result) {
                cacheLoad.save(cacheKey, result);
                return result;
            }
        });

        if (forceRefresh) {
            return fromNetWork;
        } else
            return Observable.concat(fromCache, fromNetWork).first();
    }

    /**
     * 网络优先加载
     */
    public static <T> Observable<T> netCache(final Context context, final String cacheKey, final boolean isFresh, final Observable<T> fromNetWork, final ICacheLoad<T> cacheLoad) {
        return fromNetWork.map(new Func1<T, T>() {
            @Override
            public T call(T result) {
                cacheLoad.save(cacheKey, result);
                return result;
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                Logger.e(throwable.getMessage(), throwable);
                if (!NetworkUtil.checkNetwork(context)) {
                    if (cacheLoad != null && isFresh)
                        return createData(cacheLoad.load(context, cacheKey));
                }
                return Observable.error(throwable);
            }
        });
    }

    /**
     * 隐藏式下载
     * 最后显示的都是在缓存中的数据
     */
    public static <T> Observable<T> hidCache(final Context context, final String cacheKey, boolean isFresh, Observable<T> fromNetWork, final ICacheLoad<T> cacheLoad) {
        Observable<T> fromCache = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                T cache = cacheLoad.load(context, cacheKey);
                if (cache != null) {
                    subscriber.onNext(cache);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        /**
         * 这里的fromNetWork 不需要指定schedule,在handleRequest中已经变换了
         */
        fromNetWork = fromNetWork.map(new Func1<T, T>() {
            @Override
            public T call(T result) {
                Logger.d("loadfromNet");
                cacheLoad.save(cacheKey, result);
                T cache = cacheLoad.load(context, cacheKey);
                return cache;
            }
        });

        if (!isFresh) {
            return fromNetWork;
        } else
            return Observable.merge(fromCache, fromNetWork);
    }

    /**
     * 打log
     * @param <T>
     */
    public static class LogFunc<T> implements Func1<T,T>{
        @Override
        public T call(T t) {
            Logger.d(new Gson().toJson(t));
            return t;
        }
    }
}
