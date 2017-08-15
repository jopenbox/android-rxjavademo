package com.example.rxjava1;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.RadioGroup;

import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;

import com.example.rxjava1.fragment.AutoTextFragment;
import com.example.rxjava1.fragment.CheckboxFragment;
import com.example.rxjava1.fragment.SimpleClickFragment;

import rx.functions.Action1;

/**
 * RxBinding1 Demo
 */
public class RxBindingActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup mRadioGroup;
    private Toolbar rxBindingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);

        initToolbar();

        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    private void initToolbar() {
        rxBindingToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(rxBindingToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮

        // Toolbar的item点击
        RxToolbar.itemClicks(rxBindingToolbar).subscribe((menuItem -> {
            Snackbar.make(rxBindingToolbar, "toolbar " + menuItem.getTitle(), Snackbar.LENGTH_SHORT).show();
        }));

        // Toolbar的导航栏点击
        RxToolbar.navigationClicks(rxBindingToolbar).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Snackbar.make(rxBindingToolbar, "toolbar navigationClicks", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rxbinding_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.simple_click_btn:
                setFragment(SimpleClickFragment.getInstance());
                break;
            case R.id.auto_text_btn:
                setFragment(AutoTextFragment.getInstance());
                break;
            case R.id.checkbox_btn:
                setFragment(CheckboxFragment.getInstance());
                break;
        }
    }

    private void setFragment(Fragment instance) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.layout, instance)
                .commit();
    }
}
