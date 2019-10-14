package com.marwaeltayeb.movietrailer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static com.marwaeltayeb.movietrailer.activities.MainActivity.showSnackBar;
import static com.marwaeltayeb.movietrailer.activities.MainActivity.snack;


public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            if (!isOnline(context)) {
                snack.show();
            }else {
                snack.dismiss();
            }
    }


    private boolean isOnline(Context context) {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default database network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            showSnackBar();
            return false;
        }
    }



}
