package com.marwaeltayeb.movietrailer.utils;

import android.net.ParseException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marwa on 8/7/2019.
 */

public class Utility {

    // Format the date received from guardian JSON
    public static String formatDate(String currentDate) {
        // This is the time format from guardian JSON "2017-10-29T06:00:20Z"
        // will be changed to 29-10-2017 8pm format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newDate = null;
            try {
                newDate = format.parse(currentDate);
                format = new SimpleDateFormat("dd-MM-yyyy");
                currentDate = format.format(newDate);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

        }catch (ParseException e) {
            Log.e("Adapter", "Problem with parsing the date format");
        }
        return currentDate;
    }
}


