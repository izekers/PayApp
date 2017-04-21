package com.zekers.ui.listener;

/**
 * 抽象类集合
 * Created by Zekers on 2017/1/24.
 */
public class Method {
    public interface Action {
        void invoke();
    }

    public interface Action1<T1> {
        void invoke(T1 p);
    }

    public interface IDialog{
        void success(final CharSequence message, final Method.Action completeAction);
        void error(final CharSequence message, final Method.Action errorAction);
    }
}
