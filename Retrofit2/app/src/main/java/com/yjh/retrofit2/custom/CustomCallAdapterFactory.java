package com.yjh.retrofit2.custom;


import android.support.annotation.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class CustomCallAdapterFactory extends CallAdapter.Factory {
    private static final CustomCallAdapterFactory INSTANCE = new CustomCallAdapterFactory();

    public static CustomCallAdapterFactory create() {
        return INSTANCE;
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType,
                                 @Nullable Annotation[] annotations,
                                 @Nullable Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);

        if (rawType == CustomCall.class && returnType instanceof ParameterizedType) {
            Type callReturnType = getParameterUpperBound(0, (ParameterizedType) returnType);
            return new CustomCallAdapter(callReturnType);
        }
        return null;
    }
}
