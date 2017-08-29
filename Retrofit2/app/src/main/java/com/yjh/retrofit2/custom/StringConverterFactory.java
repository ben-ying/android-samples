package com.yjh.retrofit2.custom;


import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class StringConverterFactory extends Converter.Factory {
    private static final StringConverterFactory INSTANCE = new StringConverterFactory();

    public static StringConverterFactory create() {
        return INSTANCE;
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
            Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return StringConverter.create();
        }
        return null;
    }
}
