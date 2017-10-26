package com.chq.rxjava;

import android.widget.Toast;

import com.chq.rxjava.util.Utils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chq18099 on 2017/9/19.
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    /**
     * 线程调度
     */
    protected <T> ObservableTransformer<T, T> compose(final LifecycleTransformer<T> lifecycle) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                // 可添加网络连接判断等
                                if (!Utils.isNetworkAvailable(BaseActivity.this)) {
                                    Toast.makeText(BaseActivity.this, R.string.toast_network_error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(lifecycle);
            }
        };
    }
}
