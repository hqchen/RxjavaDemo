package com.chq.rxjava.http;

import com.chq.rxjava.entity.BaseEntity;
import com.chq.rxjava.entity.ExpressInfo;
import com.chq.rxjava.entity.LogisticsInfo;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RetrofitService {


    //http://www.kuaidi100.com/query?type=shunfeng&postid=784501050381&id=1
    @FormUrlEncoded
    @POST("query")
    Observable<BaseEntity<ArrayList<ExpressInfo>>> getExpress(
            @Field("type") String type,
            @Field("postid") String postid);


  /*  @GET("query")
    Observable<BaseEntity<LogisticsInfo>> getExpress(
            @Query("type") String type, @Query("postid") String postid);*/
//    @GET("query")
//    Call<BaseEntity<LogisticsInfo>> getExpressInfo(
//            @Query("type") String type, @Query("postid") String postid);

    @FormUrlEncoded
    @POST("query")
    Call<LogisticsInfo> getExpressInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("query")
    Call<LogisticsInfo> getExpress2(@Field("type") String type, @Field("postid") String postid);


  /*  @GET("video/getUrl")
    Observable<BaseEntity<VideoUrl>> getVideoUrl(
            @Query("id") long id
    );

    @FormUrlEncoded
    @POST("user/addVideo")
    Observable<BaseEntity<Boolean>> addVideo(
            @FieldMap Map<String, Object> map
    );*/
}
