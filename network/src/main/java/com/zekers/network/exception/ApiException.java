package com.zekers.network.exception;

/**
 * 网络请求错误
 * * Created by Zekers on 2016/12/23.
 */
public class ApiException extends RuntimeException{
    public static final int TokenFailure = 373;
    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code) {
            case TokenFailure:
                message="Token失效";
                break;
            default:
                message = "未知错误";
        }
        return message;
    }
}
