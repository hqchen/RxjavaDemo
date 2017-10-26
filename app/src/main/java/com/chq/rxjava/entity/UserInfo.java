package com.chq.rxjava.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chq18099 on 2017/9/19.
 */

public class UserInfo {
    @SerializedName("id")
    private long id;
    @SerializedName("token")
    private String token;
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("nickname")
    private String nickname;

}
