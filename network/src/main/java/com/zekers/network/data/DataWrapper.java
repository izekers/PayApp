package com.zekers.network.data;

import com.google.gson.annotations.SerializedName;
import com.google.zxing.Result;

/**
 * 数据封装器
 * Created by Administrator on 2017/1/24.
 */
public class DataWrapper<T> {
    @SerializedName("StateCode")
    private int StateCode;
    @SerializedName("Message")
    private String Message;
    @SerializedName("Result")
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

    public boolean success() {
        return StateCode == 1;
    }

    //登录失效或密码错误
    public boolean accountFail(){
        return StateCode == 403;
    }

}
