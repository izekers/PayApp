package com.zekers.network.base;

import com.zekers.network.R;
import com.zekers.network.exception.ApiException;
import com.zekers.pjutils.BaseApplication;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.network.NetworkUtil;
import rx.Subscriber;

/**
 * 包装过的访问
 * Created by Zeker on 2017/1/24.
 */
public abstract class RxSubscribe<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Logger.e(e.getMessage(), e);
        if (!NetworkUtil.checkNetwork(BaseApplication.Instance)) {
            onError(BaseApplication.Instance.getResources().getString(R.string.error_no_network));
        } else if (e instanceof ApiException) {
            onError(e.getMessage());
        } else {
            onError(BaseApplication.Instance.getResources().getString(R.string.error_unknow));
        }
    }

    public abstract void onError(String message);

}
