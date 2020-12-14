package com.marwaeltayeb.movietrailer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.marwaeltayeb.movietrailer.utils.OnNetworkListener;

public class NetworkChangeReceiver extends BroadcastReceiver {

    /** The absence of a connection type. */
    private static final int NO_CONNECTION_TYPE = -1;

    /** The last processed network type. */
    private static int sLastType = NO_CONNECTION_TYPE;

    OnNetworkListener onNetworkListener;

    public void setOnNetworkListener(OnNetworkListener onNetworkListener) {
        this.onNetworkListener = onNetworkListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        final int currentType = activeNetworkInfo != null ? activeNetworkInfo.getType() : NO_CONNECTION_TYPE;

        // Avoid handling multiple broadcasts for the same connection type
        if (sLastType != currentType) {
            if (activeNetworkInfo != null) {
                boolean isConnectedOrConnecting = activeNetworkInfo.isConnectedOrConnecting();
                boolean isWiFi = ConnectivityManager.TYPE_WIFI == currentType;
                boolean isMobile = ConnectivityManager.TYPE_MOBILE == currentType;

                // Connected
                if(isConnectedOrConnecting && isWiFi || isConnectedOrConnecting && isMobile){
                    onNetworkListener.onNetworkConnected();
                }
            } else {
                // Disconnected
                onNetworkListener.onNetworkDisconnected();
            }

            sLastType = currentType;
        }
    }
}
