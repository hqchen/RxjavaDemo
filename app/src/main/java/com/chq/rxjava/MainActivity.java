package com.chq.rxjava;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chq.rxjava.entity.BaseEntity;
import com.chq.rxjava.entity.ExpressInfo;
import com.chq.rxjava.entity.LogisticsInfo;
import com.chq.rxjava.http.HttpService;
import com.chq.rxjava.http.MyObserver;

import java.util.ArrayList;

import io.reactivex.Observable;


public class MainActivity extends BaseActivity {
    private EditText mName;
    private EditText mExpressNo;
    private Button mSubmit;
    private static final String TAG = "MainActivity";
    private LogisticsInfo info;
    private LinearLayout mLogisticsView;
    private Context mContext;
    private ImageView mArrowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mContext = this;
        mArrowView = (ImageView) findViewById(R.id.iv_arrow);
        mArrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        mLogisticsView = (LinearLayout) findViewById(R.id.ll_info);
        mName = (EditText) findViewById(R.id.et_express_name);
        mExpressNo = (EditText) findViewById(R.id.et_express_no);
        mSubmit = (Button) findViewById(R.id.btn_confirm);
        mSubmit.setOnClickListener(view -> {
            String name = mName.getText().toString();
            String[] arr = getResources().getStringArray(R.array.express_name);
            for (int i = 0; i < arr.length; i++) {
                if (name.equals(arr[i])) {
                    String type = getResources().getStringArray(R.array.express_id)[i];
                    check(type, mExpressNo.getText().toString());
                }
            }
        });

    }

    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //设置对话框的标题
        dialog.setTitle("请选择快递公司");
        dialog.setItems(R.array.express_name, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = getResources().getStringArray(R.array.express_name)[i];
                mName.setText(name);
            }
        });
        //创建一个列表对话框
        dialog.show();
    }

    /* private void get(String type, String postId) {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("http://www.kuaidi100.com/")
                 .addConverterFactory(GsonConverterFactory.create(new Gson()))
                 .build();
         RetrofitService service = retrofit.create(RetrofitService.class);
         Map<String,String> params=new HashMap<>();
         params.put("type",type);
         params.put("postid",postId);
       //  Call<LogisticsInfo> call = service.getExpress2(type,postId);
         Call<LogisticsInfo> call = service.getExpressInfo(params);
         call.enqueue(new Callback<LogisticsInfo>() {
             @Override
             public void onResponse(Call<LogisticsInfo> call, Response<LogisticsInfo> response) {
                 LogisticsInfo infos=response.body();
                 String state=infos.state;

                 if (state.equals("3")) {
                     initData(infos.data);
                 }else if (state.equals("0")) {
                     mLogisticsView.removeAllViews();
                     TextView errorInfo=new TextView(mContext);
                     errorInfo.setText(infos.message);
                     mLogisticsView.addView(errorInfo);
                 }
             }

             @Override
             public void onFailure(Call<LogisticsInfo> call, Throwable t) {

             }
         });
     }*/

    private void initData(ArrayList<ExpressInfo> infoList) {
        mLogisticsView.removeAllViews();
        for (ExpressInfo item : infoList) {
            View view = LayoutInflater.from(this).inflate(R.layout.express_info_item, null);
            TextView mTime = (TextView) view.findViewById(R.id.tv_time);
            TextView mDesc = (TextView) view.findViewById(R.id.tv_desc);
            mTime.setText(item.ftime);
            mDesc.setText(item.context);
            mLogisticsView.addView(view);
        }
    }


    private void check(String userId, String password) {
        Observable<BaseEntity<ArrayList<ExpressInfo>>> observable = HttpService.getInstance()
                .getExpress(userId, password);
        observable.compose(compose(this.<BaseEntity<ArrayList<ExpressInfo>>>bindToLifecycle()))
                .subscribe(new MyObserver<ArrayList<ExpressInfo>>(this) {
                    @Override
                    protected void onHandleSuccess(ArrayList<ExpressInfo> data) {
                        initData(data);
                    }

                    @Override
                    protected void onHandleError(String message) {
                        Toast.makeText(mContext,message,Toast.LENGTH_LONG).show();
                    }
                });
               /* .subscribe(new Observer<BaseEntity<ArrayList<ExpressInfo>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEntity<ArrayList<ExpressInfo>> arrayListBaseEntity) {
                          if (arrayListBaseEntity.isSuccess()){
                              initData(arrayListBaseEntity.getData());
                          }else{
                              Toast.makeText(mContext,arrayListBaseEntity.getMsg(),Toast.LENGTH_LONG).show();
                          }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }

    private void test() {
        // Observable.just("hello world").subscribe(s ->Toast.makeText(this,"我是测试",Toast.LENGTH_LONG).show());
        //使用just()，将为你创建一个Observable并自动为你调用onNext( )发射数据：
      /*  Observable.just(1, 2, 3, 4).repeat(3)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe();
        //使用from()，遍历集合，发送每个item：
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        Observable observable = Observable.fromArray(list);*/

       /* //使用defer()，有观察者订阅时才创建Observable，并且为每个观察者创建一个新的Observable：
        Observable.defer(new Callable<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> call() throws Exception {
                return Observable.just("deferObservable");
            }
        });
       //使用interval( ),创建一个按固定时间间隔发射整数序列的Observable，可用作定时器：
        Observable.interval(1, TimeUnit.SECONDS);
        //使用range( ),创建一个发射特定整数序列的Observable，第一个参数为起始值，第二个为发送的个数，如果为0则不发送，负数则抛异常：
        Observable.range(0,5);
*/
/**
 *  ObservableEmitter： Emitter是发射器的意思，那就很好猜了，这个就是用来发出事件的，它可以发出三种类型的事件
 *  通过调用emitter的onNext(T value)、onComplete()和onError(Throwable error)就可以分别发出next事件、
 *  complete事件和error事件。
 *
 *  上游可以发送无限个onNext, 下游也可以接收无限个onNext.
 当上游发送了一个onComplete后, 上游onComplete之后的事件将会继续发送, 而下游收到onComplete事件之后将不再继续接收事件.
 当上游发送了一个onError后, 上游onError之后的事件将继续发送, 而下游收到onError事件之后将不再继续接收事件.
 上游可以不发送onComplete或onError.
 最为关键的是onComplete和onError必须唯一并且互斥, 即不能发多个onComplete, 也不能发多个onError, 也不能先发一个onComplete, 然后再发一个onError, 反之亦然

 */
       /* Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "subscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Toast.makeText(MainActivity.this, "测试" + integer.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "error");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        });

    } */

        /*Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {

            }
        });*/

//        PublishSubject<String> publishSubject = PublishSubject.create();
//        publishSubject.onNext("as Observable");
//        publishSubject.onComplete();
    }
}
