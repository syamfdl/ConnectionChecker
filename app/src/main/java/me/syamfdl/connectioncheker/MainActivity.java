package me.syamfdl.connectioncheker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    Button btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCheck = findViewById(R.id.btnTry);

        // Manually Check Internet Connection
        checkInternetConnection();

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInternetConnection();
            }
        });

    }

    private void checkInternetConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
         showSnackBar(isConnected);

        if (isConnected) {
            changeActivity();
        }

    }

    private void changeActivity() {
        Intent intent = new Intent(this, OfflineActivity.class);
        startActivity(intent);
    }

    private void showSnackBar(boolean isConnected){
        String message;
        int color;

        if (isConnected) {
            message = "You re Connected!";
            color = Color.GREEN;
        } else {
            message = "You re Offline :(";
            color = Color.RED;
        }
        Snackbar snackbar = Snackbar.make(findViewById(R.id.RL), message, Snackbar.LENGTH_LONG);

        View view = snackbar.getView();
        TextView textView =  view.findViewById(com.google.android.material.R.id.snackbar_text);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register Intent Filter
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.EXTRA_CAPTIVE_PORTAL);

        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);

        // Register Connection Status
        MyApp.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onConnectionChanged(boolean isConnected) {
        if (!isConnected){
            changeActivity();
        }
        showSnackBar(isConnected);
    }
}
