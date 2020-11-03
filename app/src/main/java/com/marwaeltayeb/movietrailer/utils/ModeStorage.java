package com.marwaeltayeb.movietrailer.utils;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class ModeStorage {

    public static void setLightMode(Context context, boolean isLightModeOn){
        SharedPreferences sharedpreferences = context.getSharedPreferences("lightMode_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("lightMode", isLightModeOn);
        editor.apply();
    }

    public static boolean isLightModeOn(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("lightMode_data", Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean("lightMode", false);
    }

    public static void getMode(Context context){
        if (isLightModeOn(context)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
