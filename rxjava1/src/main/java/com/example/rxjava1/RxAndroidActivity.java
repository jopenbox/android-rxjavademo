package com.example.rxjava1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * RxAndroid1 Demo
 */
public class RxAndroidActivity extends AppCompatActivity{
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    /**
     * 发送数据
     */
    private void sendData() {
        initObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        // 主线程更新UI
                        button.setText(s);
                    }
                });
    }

    /**
     * 初始化被观察者
     */
    private Observable<String> initObservable() {
        // Observable.defer: 延迟事件的发送时间
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                // 模拟网络请求等耗时操作
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Observable.just("1", "2", "3");
            }
        });
    }
}
