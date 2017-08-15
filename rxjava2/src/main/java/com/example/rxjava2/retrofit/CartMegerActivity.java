package com.example.rxjava2.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.rxjava2.ApiService;
import com.example.rxjava2.R;
import com.example.rxjava2.bean.NetBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit2 Demo 模拟合并本地与服务器购物车列表
 */
public class CartMegerActivity extends AppCompatActivity {

    private ApiService apiService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_meger);

        // 构建Retrofit
        apiService = new Retrofit.Builder()
                .baseUrl("http://httpbin.org/")
                .addConverterFactory(GsonConverterFactory.create()) // RxJava2与Gson混用
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava2与Retrofit混用
                .build()
                .create(ApiService.class);
    }

    /**
     * 点击事件
     */
    public void onBtClick(View v) {
        Observable.merge(getDataForLocal(), getDataForNet())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(List<String> strings) {
                        for (String str: strings) {
                            Log.i("JAVA", str);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onComplete() {
                        Log.i("JAVA", "onComplete");
                    }
                });
    }
    /**
     * 获取本地数据
     */
    private Observable<List<String>> getDataForLocal() {
        List<String> list = new ArrayList<>();
        list.add("购物车的商品1");
        list.add("购物车的商品2");
        return Observable.just(list);
    }
    /**
     * 获取网络数据
     */
    private Observable<List<String>> getDataForNet() {
        return Observable.just("shopName")
            // flatMap方法是用于数据格式转换的方法，参数一表示原数据，
            // 参数二表示转换的数据，那么就是通过发送网络参数，转换成网络返回的数据，调用Retrofit
            .flatMap(new Function<String, ObservableSource<NetBean>>() {
                @Override
                public ObservableSource<NetBean> apply(@NonNull String s) throws Exception {
                    // 1.发送网络请求，获取数据
                    return apiService.getCartList(s);
                }
            }).flatMap(new Function<NetBean, ObservableSource<List<String>>>() {
                @Override
                public ObservableSource<List<String>> apply(@NonNull NetBean netBean) throws Exception {
                    // String shop = netBean.get_$Args257().getShopName();
                    String shop = "购物车的商品3";
                    List<String> list = new ArrayList<>();
                    list.add(shop);
                    // 2.转换NetBean数据为我们需要的List<String>数据
                    return Observable.just(list);
                }
            }).subscribeOn(Schedulers.io());
    }
}
