package com.marwaeltayeb.movietrailer.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    public static void setInsertState(Context context, boolean isReminderRunning){
        SharedPreferences sharedpreferences = context.getSharedPreferences("insert_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("isMovieInserted", isReminderRunning);
        editor.apply();
    }

    public static boolean getInsertState(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("insert_data", Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean("isMovieInserted", false);
    }
}
