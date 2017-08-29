package com.yjh.retrofit2.custom;


import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class StringConverter implements Converter<ResponseBody, String> {
    private static final StringConverter INSTANCE = new StringConverter();

    public static StringConverter create() {
        return INSTANCE;
    }

    @Override
    public String convert(@NonNull ResponseBody value) throws IOException {
        return value.string();
    }
}
