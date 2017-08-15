package com.example.rxjava2.rxbinding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import com.example.rxjava2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RxBinding2 Demo 优化搜索请求
 */
public class TextChangeActivity extends AppCompatActivity{

    private EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_change);
        editText = (EditText) findViewById(R.id.edittext);

        RxTextView.textChanges(editText)
                // 当你敲完字之后停下来的半秒就会执行下面语句
                .debounce(500, TimeUnit.MILLISECONDS)
                // flatMap: 当同时多个数据请求访问的时候，前面的网络数据会覆盖后面的网络数据
                // switchMap: 当同时多个网络请求访问的时候，会以最后一个发送请求为准，前面网络数据会被最后一个覆盖
                .switchMap(new Function<CharSequence, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(
                            @NonNull CharSequence charSequence) throws Exception {
                        // 网络请求操作，获取我们需要的数据
                        List<String> list = new ArrayList<String>();
                        list.add("2017");
                        list.add("2018");
                        return Observable.just(list);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(@NonNull List<String> strings) throws Exception {
                        // 更新UI
                        Log.i("JAVA", strings.toString());
                    }
                });
    }
}
