package com.example.rxjava2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rxjava2.retrofit.CartMegerActivity;
import com.example.rxjava2.retrofit.LoginActivity;
import com.example.rxjava2.rxbinding.ButtonClickActivity;
import com.example.rxjava2.rxbinding.TextChangeActivity;

/**
 * RxJava2 编程需要注意代码结构简介、生命周期绑定和线程的控制。
 */
public class MainActivity extends AppCompatActivity {

    private static final String[] strs = new String[]{
            "RxJava2 Demo",
            "模拟用户登陆获取用户数据",
            "模拟合并本地与服务器购物车列表",
            "优化搜索请求",
            "优化点击请求"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switchActivity(position);
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void switchActivity(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, RxJava2Activity.class));
                break;
            case 1:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, CartMegerActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, TextChangeActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, ButtonClickActivity.class));
                break;
            default:
                break;
        }
    }
}
