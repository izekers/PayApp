package com.zekers.network.data;

/**
 * Created by Administrator on 2017/3/10.
 */

public class DataWrapper2<T> {
    private int StateCode;
    private String Message;
    private T Result;

    public int getStateCode() {
        return StateCode;
    }

    public void setStateCode(int stateCode) {
        StateCode = stateCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }
}
