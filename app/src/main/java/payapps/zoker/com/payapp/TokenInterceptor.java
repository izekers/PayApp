package payapps.zoker.com.payapp;

import com.zekers.network.interceptor.ABSTokenInterceptor;

import payapps.zoker.com.payapp.control.record.TokenRecord;
import payapps.zoker.com.payapp.model.User;

/**
 *
 * Created by Administrator on 2017/3/9.
 */
public class TokenInterceptor extends ABSTokenInterceptor{
    @Override
    protected String getNewToken() {
//        TokenRecord tokenRecord=new TokenRecord();
//        String user=tokenRecord.load();
        return null;
    }
}
