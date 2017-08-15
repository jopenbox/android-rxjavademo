package com.example.rxjava1;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * RxLifecycle1 Demo
 */
public class RxLifecycleActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_lifecycle);
    }
}
