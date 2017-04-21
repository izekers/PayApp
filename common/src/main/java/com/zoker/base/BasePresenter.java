package com.zoker.base;

/**
 * Created by Administrator on 2017/3/9.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
}
