package com.example.rxjava1;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;

import com.jakewharton.rxbinding.view.RxView;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.IOException;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * RxPermissions1 Demo
 */
public class RxPermissionsActivity extends AppCompatActivity {
    private Camera camera;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true); // 打印log

        setContentView(R.layout.activity_rx_permissions);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        RxView.clicks(findViewById(R.id.enableCamera))
                // compose操作符: 变化类操作符，把一个被观察者变成另外一个新的被观察者
                .compose(rxPermissions.ensureEach(Manifest.permission.CAMERA))
                // 订阅
                .subscribe(new Action1<Permission>() {
                               @Override
                               public void call(Permission permission) {
                                   if (permission.granted) {
                                       releaseCamera();
                                       camera = Camera.open(0);
                                       try {
                                           camera.setPreviewDisplay(surfaceView.getHolder());
                                           camera.startPreview();
                                       } catch (IOException e) {
                                           Log.e("JAVA", "Error", e);
                                       }
                                   } else if (permission.shouldShowRequestPermissionRationale) {
                                       // 显示相关权限说明
                                   } else {
                                       // 获取权限失败
                                   }
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable t) {
                                Log.e("JAVA", "onError", t);
                            }
                        },
                        new Action0() {
                            @Override
                            public void call() {
                                Log.e("JAVA", "OnComplete");
                            }
                        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
}
