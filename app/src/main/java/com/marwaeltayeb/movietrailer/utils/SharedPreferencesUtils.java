package com.marwaeltayeb.movietrailer.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    public static void setInsertState(Context context, String movieId ,boolean isMovieInserted){
        SharedPreferences sharedpreferences = context.getSharedPreferences("insert_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(movieId, isMovieInserted);
        editor.apply();
    }

    public static boolean getInsertState(Context context, String movieId){
        SharedPreferences sharedpreferences = context.getSharedPreferences("insert_data", Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(movieId, false);
    }

    public static void clearSharedPreferences(Context context){
        context.getSharedPreferences("insert_data", Context.MODE_PRIVATE).edit().clear().apply();
    }
}
