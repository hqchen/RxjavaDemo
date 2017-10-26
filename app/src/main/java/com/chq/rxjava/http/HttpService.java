package com.chq.rxjava.http;

import android.util.Log;

import com.chq.rxjava.entity.ExpressInfo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by chq18099 on 2017/9/19.
 */

public class HttpService {
    private static final long TIMEOUT = 30;
    private static OkHttpClient client;
    private static RetrofitService retrofitService;

   /* private static OkHttpClient httpClient = new OkHttpClient.Builder()
            // 添加通用的Header
//            .addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request.Builder builder = chain.request().newBuilder();
//                    // 替换为自己的token
//                    builder.addHeader("token", "123");
//                    return chain.proceed(builder.build());
//                }
//            })

            .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("message", message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BASIC))
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build();*/

   /* private static RetrofitService retrofitService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            // 添加Gson转换器
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            // .addConverterFactory(GsonConverterFactory.create())
            // 添加Retrofit到RxJava的转换器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(RetrofitService.class);*/

    public static RetrofitService getInstance() {
        if (retrofitService == null) {
            synchronized (HttpService.class) {
                if (retrofitService == null) {
                    client = new OkHttpClient.Builder()
                            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .build();
                    retrofitService = new Retrofit.Builder()
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build()
                            .create(RetrofitService.class);
                }
            }
        }
        return retrofitService;
    }

    private static Gson buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                // 此处可以添加Gson 自定义TypeAdapter
                .registerTypeAdapter(ExpressInfo.class, new UserTypeAdapter())
                .create();
    }
}
