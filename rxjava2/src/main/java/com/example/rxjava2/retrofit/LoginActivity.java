package com.example.rxjava2.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.rxjava2.ApiService;
import com.example.rxjava2.R;
import com.example.rxjava2.bean.NetBean;
import com.example.rxjava2.bean.UserBean;
import com.example.rxjava2.bean.UserParam;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit2 Demo 模拟用户登陆获取用户数据
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 构建Retrofit
        ApiService apiService = new Retrofit.Builder()
                .baseUrl("http://httpbin.org/")
                .addConverterFactory(GsonConverterFactory.create()) // RxJava2与Gson混用
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava2与Retrofit混用
                .build()
                .create(ApiService.class);

        // 构建RxJava
        UserParam param = new UserParam("zhangsan", "123");
        // 发送param参数
        Observable.just(param)
                // flatMap方法是用于数据格式转换的方法，参数一表示原数据，
                // 参数二表示转换的数据，那么就是通过发送网络参数，转换成网络返回的数据，调用Retrofit
                .flatMap(new Function<UserParam, ObservableSource<NetBean>>() {
                    @Override
                    public ObservableSource<NetBean> apply(@NonNull UserParam userParam)
                            throws Exception {
                        // 1.发送网络请求，获取NetBean
                        return apiService.getUserInfo(userParam.getParam1(), userParam.getParam2());
                    }
                })
                .flatMap(new Function<NetBean, ObservableSource<UserBean>>() {
                    @Override
                    public ObservableSource<UserBean> apply(@NonNull NetBean netBean)
                            throws Exception {
                        UserBean user = new UserBean(netBean.getForm().getUsername(),
                                netBean.getForm().getPassword());
                        // 2.转换NetBean数据为我们需要的UserBean数据
                        return Observable.just(user);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserBean>() {
                    @Override
                    public void accept(@NonNull UserBean userBean) throws Exception {
                        Log.i("JAVA", "" + "用户名:" + userBean.getUsername()
                                + ", 密码:" + userBean.getPassword());
                    }
                });
    }
}
