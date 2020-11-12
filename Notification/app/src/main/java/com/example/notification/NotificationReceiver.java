package com.example.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("NOTIFICATION");
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
