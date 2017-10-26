package com.chq.rxjava.entity;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chq18099 on 2017/9/19.
 */

public class BaseEntity<T> {

    @SerializedName("message")
    private String message;
    @SerializedName("state")
    private String state;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private T data;

    public boolean isSuccess() {
        return TextUtils.equals(status,"200");
    }

    public String getMsg() {
        return message;
    }

    public T getData() {
        return data;
    }
}

