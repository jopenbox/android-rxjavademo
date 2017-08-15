package com.example.rxjava1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * RxJava1 的线程控制
 *
 * 调度器 Scheduler: 用于控制操作符和被观察者所执行的线程，不同的调度器对应不同的线程
 * Schedulers.immediate(): 默认线程
 * Schedulers.newThread(): 新建线程
 * Schedulers.io(): 适用于I/O操作(线程池)
 * Schedulers.computation(): 适用于计算工作(线程池)
 * Schedulers.trampoline(): 当前线程，列队执行
 *
 * 如何进行线程控制？
 * 使用 subscribeOn() 和 observeOn() 操作符
 * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程，事件产生的线程。
 * observeOn(): 指定 Subscriber 所运行在的线程，事件消费的线程。
 *
 * 特点: subscribeOn()只能调用一次，observeOn()可以调用多次
 */
public class RxJavaActivity extends AppCompatActivity{
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        mTextView = (TextView) findViewById(R.id.textview);
        //test1();
        test2();
    }

    public void test1() {
        Observable.just(getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // 指定Android的UI线程
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        // 更新UI
                        mTextView.setText(integers.toString());
                    }
                });
    }

    public void test2() {
        Observable.from(getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        // 更新UI
                        mTextView.setText(integer.toString());
                    }
                });
    }

    /**
     * 模拟网络请求等耗时操作
     */
    private List<Integer> getData() {
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<10; i++) {
            list.add(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
