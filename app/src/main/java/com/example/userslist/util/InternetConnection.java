package com.example.userslist.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnection {

    public static boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo ();

        if (netinfo != null && netinfo.isConnectedOrConnecting ()) {
            NetworkInfo wifi = cm.getNetworkInfo (ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo (ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }

}
