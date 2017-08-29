package com.yjh.retrofit2.custom;


import android.support.annotation.NonNull;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

public class CustomCallAdapter<T> implements CallAdapter<T, CustomCall<T>> {
    private final Type responseType;

    public CustomCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public CustomCall<T> adapt(@NonNull Call<T> call) {
        return new CustomCall<>(call);
    }
}
