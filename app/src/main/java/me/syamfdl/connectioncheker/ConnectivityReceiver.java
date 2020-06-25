package me.syamfdl.connectioncheker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener mConnectivityReceiverListener;

    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (mConnectivityReceiverListener != null) {
            mConnectivityReceiverListener.onConnectionChanged(isConnected());
        }
    }

    //Method for manually click on button to try again the connection
    public static boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) MyApp.
                getInstance().
                getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    //Interface
    public interface ConnectivityReceiverListener {
        void onConnectionChanged(boolean isConnected);
    }
}
