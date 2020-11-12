package com.example.broadcastreceiver_internetstate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imvConnectionState;
    TextView txtConnectionState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addViews();
    }

    private void addViews() {
        imvConnectionState = findViewById(R.id.imvConnectionState);
        txtConnectionState = findViewById(R.id.txtConnectionState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    imvConnectionState.setImageResource(R.drawable.ic_baseline_wifi_24);
                    txtConnectionState.setText("Connected with WIFI");
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    imvConnectionState.setImageResource(R.drawable.ic_baseline_wifi_24);
                    txtConnectionState.setText("Connected with Mobile Data");
                }
            } else {
                imvConnectionState.setImageResource(R.drawable.ic_baseline_wifi_24);
                txtConnectionState.setText("No internet connection");
            }
        }
    };
}