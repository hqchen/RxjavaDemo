package com.chq.rxjava.http;

import android.content.Context;
import android.util.Log;

import com.chq.rxjava.entity.BaseEntity;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by chq18099 on 2017/9/25.
 */

public abstract class MyObserver<T> implements Observer<BaseEntity<T>> {
    private Context mContext;
    private static final String TAG = "MyObserver";

    public MyObserver(Context context) {
        this.mContext = context.getApplicationContext();

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull BaseEntity<T> tBaseEntity) {
        BaseEntity entity = tBaseEntity;
        if (tBaseEntity.isSuccess()) {
            onHandleSuccess(tBaseEntity.getData());
        } else {
            onHandleError(tBaseEntity.getMsg());
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.i(TAG, "onError");
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onHandleSuccess(T data);

    protected abstract void onHandleError(String message);
}
