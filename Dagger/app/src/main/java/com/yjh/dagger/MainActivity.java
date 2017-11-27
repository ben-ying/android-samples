package com.yjh.dagger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yjh.dagger.injection.DaggerDaggerComponent;
import com.yjh.dagger.injection.DaggerModule;

import java.util.Map;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerDaggerComponent.builder().daggerModule(new DaggerModule(6)).build().inject(this);
        Log.e(TAG, "count: " + repository.count);
    }
}
