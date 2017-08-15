package com.example.rxjava2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava2 Demo
 */
public class RxJava2Activity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCodeClick();
            }
        });

        rxjavaTest1();
        //rxjavaTest2();
    }

    public void rxjavaTest1() {
        // 创建被观察者
        Observable.create(new ObservableOnSubscribe<String>() {
            // 默认在主线程里执行该方法
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("Hello");
                e.onNext("World");
                // 结束标识
                e.onComplete();
            }
        })
//        // 发送多个数据
//        Observable.just("Hello", "World");
//        // 发送数组
//        Observable.fromArray("Hello", "World");
//        // 发送一个数据
//        Observable.fromCallable(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                return "Hello";
//            }
//        });
        // 将被观察者切换到子线程
        .subscribeOn(Schedulers.io())
        // 将观察者切换到主线程
        .observeOn(AndroidSchedulers.mainThread())
        // 创建观察者并订阅
        .subscribe(new Observer<String>() {
            private Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }
            @Override
            public void onNext(String s) {
                Log.i("JAVA", "被观察者向观察者发送的数据:" + s);
                if (s == "-1") {   // "-1" 时为异常数据，解除订阅
                    disposable.dispose();
                }
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }
        });
    }

    public void rxjavaTest2() {
        Observable.create(new ObservableOnSubscribe<String>() {
            // 默认在主线程里执行该方法
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("Hello");
                e.onNext("World");
                getData();
                // 结束标识
                e.onComplete();
            }
        })
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.i("JAVA", "被观察者向观察者发送的数据:" + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {

            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {

            }
        });
    }

    /**
     * 模拟发送验证码倒计时效果
     */
    public void onCodeClick() {
        final long count = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        button.setEnabled(false);
                        button.setTextColor(Color.GRAY);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Long aLong) {
                        button.setText(aLong + "秒后重发");
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                        button.setEnabled(true);
                        button.setTextColor(Color.RED);
                        button.setText("发送验证码");
                    }
                });
    }

    /**
     * 模拟网络请求等耗时操作
     */
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
