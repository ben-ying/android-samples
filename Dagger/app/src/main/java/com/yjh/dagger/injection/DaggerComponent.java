package com.yjh.dagger.injection;

import com.yjh.dagger.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DaggerModule.class)
public interface DaggerComponent {
    void inject(MainActivity activity);
}
