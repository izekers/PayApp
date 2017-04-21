package com.zekers.utils.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.zekers.pjutils.BaseApplication;

/**
 * SP记录类
 * Created by zekers on 2016/10/20.
 */
public abstract class BaseRecord<T> {
    private SharedPreferences mSharedPreferences;
    protected BaseRecord() {
        mSharedPreferences = BaseApplication.Instance.getSharedPreferences(SPName(), Context.MODE_PRIVATE);
    }

    public  boolean save(T t) {
        if (mSharedPreferences == null) {
            return false;
        }
        clearInfo();
        return Save(getEditor(), t);
    }

    public  T load() {
        if (mSharedPreferences == null) {
            return null;
        }
        return load(mSharedPreferences);
    }

    public SharedPreferences.Editor getEditor() {
        if (mSharedPreferences == null) {
            return null;
        }
        return mSharedPreferences.edit();
    }

    /**
     * 清除相关数据
     *
     * @return
     */
    public void clearInfo() {
        if (mSharedPreferences == null) {
            return;
        }
        mSharedPreferences.edit().clear().apply();
    }

    protected abstract  boolean Save(SharedPreferences.Editor editor, T t);

    protected abstract  T load(SharedPreferences sharedPreferences);

    protected abstract String SPName();
}
