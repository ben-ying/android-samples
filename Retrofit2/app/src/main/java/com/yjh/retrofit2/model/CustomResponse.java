package com.yjh.retrofit2.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class CustomResponse<T> {
    public static final int SUCCESS_CODE = 200;

    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private int code;
    @SerializedName("result")
    private ListResponseResult<T> result;

    public boolean isSuccessful() {
        return code == SUCCESS_CODE;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ListResponseResult<T> getResult() {
        return result;
    }

    public void setResult(ListResponseResult<T> result) {
        this.result = result;
    }
}
