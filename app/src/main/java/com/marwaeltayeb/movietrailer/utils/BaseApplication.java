package com.marwaeltayeb.movietrailer.utils;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

import static com.marwaeltayeb.movietrailer.utils.ModeStorage.getMode;

@HiltAndroidApp
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        getMode(this);
    }
}
