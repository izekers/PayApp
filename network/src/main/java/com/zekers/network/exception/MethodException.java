package com.zekers.network.exception;

/**
 *
 * Created by Zekers on 2016/12/23.
 */
public class MethodException extends RuntimeException{
    public static String API_NULL="api_is_null";
    public MethodException(String detailMessage) {
        super(detailMessage);
    }
}
