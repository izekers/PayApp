package com.zekers.utils.rx;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * Created by Zoker on 2017/1/24.
 */
public class RxHelper {
    /**
     * 数据加载调度
     * @return 返回服务
     */
    public static <T> Observable.Transformer<T, T> applySchedulers() {

        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 当前线程调度
     * @return 返回服务
     */
    public static <T> Observable.Transformer<T, T> applyImmediate() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.immediate())
                        .observeOn(Schedulers.immediate());
            }
        };
    }


}
