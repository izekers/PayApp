package com.zekers.network.api;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 * Created by zekers on 2016/12/23.
 */
public interface ConfigApi{
    @POST("otn/")
    Observable<String> getItem();
}
