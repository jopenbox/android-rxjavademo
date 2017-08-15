package com.example.rxjava2.rxbinding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;

import com.example.rxjava2.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * RxBinding2 Demo 优化点击请求
 */
public class ButtonClickActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_click);
        button = (Button) findViewById(R.id.button);

        RxView.clicks(button).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Object o) {
                        Log.i("JAVA", "onClick");
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }
}
