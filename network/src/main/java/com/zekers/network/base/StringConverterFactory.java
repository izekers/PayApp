package com.zekers.network.base;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * Retrofit   String类型转换器
 * Created by Jun on 2015/11/4.
 */
public class StringConverterFactory extends Converter.Factory {

    private static final String TAG = "StringConverterFactory";

    public static StringConverterFactory create() {
        return new StringConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Type token = new TypeToken<String>(){}.getType();
        if (type == token) {
            return new StringConverter();
        }
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    /**
     * 获取返回数据里的字符值
     */
    private class StringConverter implements Converter {

        @Override
        public Object convert(Object value) throws IOException {
            if (value != null) {
                if (value instanceof ResponseBody) {
                    return ((ResponseBody) value).string();
                }
            }
            return null;
        }
    }
}

