package com.example.rxjava1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * RxJava1 的操作符
 */
public class RxJavaTest2 {
    @Test
    public void test1() {
        Observable<String> observable=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onCompleted();
            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }};
        observable.subscribe(observer);
    }
    public void getData() {
        List<Integer> list = new ArrayList<>();
        for(int i=0; 1<10; i++) {
            list.add(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}