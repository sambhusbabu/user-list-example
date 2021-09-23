package com.example.userslist.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class InternetConBroadcastReceiver extends BroadcastReceiver {
    InternetConnection internetConnection = new InternetConnection();

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            boolean noConnectivity = intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
            );
            if (noConnectivity) {
                if (!internetConnection.isConnected(context)) {
                    Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
                    if (onConnection != null) {
                        onConnection.lossInternetConnection();
                    }
                }
            } else
                onConnection.onConnected();
        }
    }

    public interface OnConnection {
        void lossInternetConnection();

        void onConnected();
    }

    OnConnection onConnection;

    public void setOnConnection(OnConnection onConnection) {
        this.onConnection = onConnection;
    }
}
