package com.marwaeltayeb.movietrailer.utils;

import android.app.Application;

import static com.marwaeltayeb.movietrailer.utils.ModeStorage.getMode;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        getMode(this);
    }
}
