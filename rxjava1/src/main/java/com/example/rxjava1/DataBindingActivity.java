package com.example.rxjava1;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.rxjava1.databinding.ActivityDatabindingBinding;

/**
 * DataBinding1 Demo
 */
public class DataBindingActivity extends AppCompatActivity{
    // activity_databinding.xml中的layout, 生成的类名是 ActivityDatabindingBinding
    private ActivityDatabindingBinding binding;
    private static final String[] strs = new String[]{"1", "2", "3", "4", "5", "6"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
        setSupportActionBar(binding.toolbar);

        binding.listview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs));
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("JAVA", "点到了");
            }
        });
    }
}
