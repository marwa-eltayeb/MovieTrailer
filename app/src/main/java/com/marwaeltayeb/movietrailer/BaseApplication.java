package com.marwaeltayeb.movietrailer;

import static com.marwaeltayeb.movietrailer.utils.ModeStorage.getMode;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        getMode(this);
    }
}
