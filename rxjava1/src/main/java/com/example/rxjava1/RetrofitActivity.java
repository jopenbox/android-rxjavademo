package com.example.rxjava1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import com.example.rxjava1.bean.NetBean;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Retrofit2-RxJava1 Demo
 */
public class RetrofitActivity extends AppCompatActivity {
    public static final String API_URL = "https://raw.githubusercontent.com/";
    private Retrofit retrofit;
    private Subscription subscription;

    //https://raw.githubusercontent.com/smartbetter/android-rxjavademo/master/jsondata.json
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        // 初始化 Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        // 发送请求，返回数据的 Observable
        subscription = retrofit.create(GetGithub.class)
                .getData("smartbetter", "android-rxjavademo")
                .subscribeOn(Schedulers.io())
                // 进行类型转换
                .flatMap(new Func1<List<NetBean>, Observable<NetBean>>() {
                    @Override
                    public Observable<NetBean> call(List<NetBean> list) {
                        return Observable.from(list); // 将List中每一个item依次发送出来
                    }
                })
                // 订阅
                .subscribe(new Action1<NetBean>() {
                    @Override
                    public void call(NetBean s) {
                        Log.i("JAVA", s.getName());
                    }
                });
    }

    public interface GetGithub {
        @GET("/{owner}/{txt}/master/website/static/jsondata.json")
        Observable<List<NetBean>> getData (
                @Path("owner") String owner,
                @Path("txt") String repo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消订阅
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

}
