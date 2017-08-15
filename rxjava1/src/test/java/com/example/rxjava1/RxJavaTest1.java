package com.example.rxjava1;

import org.junit.Test;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * RxJava1 基础
 */
public class RxJavaTest1 {
    @Test
    public void test1() {
        // 被观察者
        Observable<String> observable=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onCompleted();
            }
        });
        // 观察者
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
        // 订阅
        observable.subscribe(observer);
    }

    @Test
    public void test2() {
        // 被观察者
        Observable<String> observable=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onCompleted();
            }
        });
        // 订阅者
        Subscriber<String> subscriber = new Subscriber<String>() {
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
        // 订阅
        observable.subscribe(subscriber);
    }

}