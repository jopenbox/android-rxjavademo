package com.example.rxjava1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * RxJava 编程需要注意代码结构简介、生命周期绑定和线程的控制。
 */
public class MainActivity extends AppCompatActivity {

    private static final String[] strs = new String[]{
            "RxJava1 Demo",
            "RxAndroid1 Demo",
            "RxBinding1 Demo",
            "Retrofit2-RxJava1 Demo",
            "RxPermissions1",
            "DataBinding1"
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
                startActivity(new Intent(this, RxJavaActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, RxAndroidActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, RxBindingActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, RxPermissionsActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, DataBindingActivity.class));
                break;
            default:
                break;
        }
    }
}
