package com.yjh.dagger;


import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Repository {
    private static final String TAG = Repository.class.getSimpleName();

    @Inject
    public int count = 3;

    public Repository(int s) {
        Log.e(TAG, "constructor");
        this.count = s;
    }
}
