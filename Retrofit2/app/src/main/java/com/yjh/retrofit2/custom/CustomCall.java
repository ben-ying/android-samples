package com.yjh.retrofit2.custom;


import java.io.IOException;

import retrofit2.Call;

public class CustomCall<T> {
    private final Call<T> call;

    public CustomCall(Call<T> call) {
        this.call = call;
    }

    public T get() throws IOException {
        return call.execute().body();
    }
}
