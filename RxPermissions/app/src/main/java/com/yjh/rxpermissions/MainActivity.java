package com.yjh.rxpermissions;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private RxPermissions mRxPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRxPermission = new RxPermissions(this);

        basicRequestPermission();

//        onTextViewClickRequestPermission();
//
//        requestMultiplyPermissions();
//
//        requestEachPermissions();
    }

    // basic usage
    private void basicRequestPermission() {
        mRxPermission
                .request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        // Always true pre-M
                        if (granted) {
                            Toast.makeText(MainActivity.this, "granted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // trigger the permission request from a specific event with rxBinding
    private void onTextViewClickRequestPermission() {
        RxView.clicks(findViewById(R.id.text_view))
                .compose(mRxPermission.ensure(Manifest.permission.CALL_PHONE))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        // Always true pre-M
                        if (granted) {
                            Toast.makeText(MainActivity.this, "granted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // request multiply mRxPermission
    private void requestMultiplyPermissions() {
        mRxPermission
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            Toast.makeText(MainActivity.this,
                                    "all granted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "at least one permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // a detailed result with requestEach or ensureEach
    private void requestEachPermissions() {
        mRxPermission
                .requestEach(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                               @Override
                               public void accept(Permission permission) throws Exception {
                                   if (permission.granted) {
                                       Toast.makeText(MainActivity.this,
                                               "granted", Toast.LENGTH_SHORT).show();
                                   } else if (permission.shouldShowRequestPermissionRationale) {
                                       // Denied permission without ask never again
                                       Toast.makeText(MainActivity.this,
                                               "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
                                   } else {
                                       // Denied permission with ask never again
                                       Toast.makeText(MainActivity.this,
                                               "denied", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }
                );
    }
}
