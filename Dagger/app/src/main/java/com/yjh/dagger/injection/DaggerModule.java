package com.yjh.dagger.injection;

import com.yjh.dagger.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class DaggerModule {
    private int value;

    public DaggerModule(int s) {
        this.value = s;
    }

    @Singleton
    @Provides
    int provideValue() {
        return value;
    }

    @Singleton
    @Provides
    Repository provideRepository(int value) {
        return new Repository(value);
    }
}
