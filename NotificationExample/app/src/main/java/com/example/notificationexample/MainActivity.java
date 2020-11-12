package com.example.notificationexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.notificationexample.App.CHANNEL_1;
import static com.example.notificationexample.App.CHANNEL_2;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManagerCompat;
    private EditText edtTitle, edtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addViews();
        notificationManagerCompat = NotificationManagerCompat.from(this);
    }

    private void addViews() {
        edtTitle = findViewById(R.id.edtTitle);
        edtMessage = findViewById(R.id.edtMessage);
    }

    public void sendOnChannel1(View view) {

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Intent broadcastReceiver = new Intent(this, NotificationReceiver.class);
        broadcastReceiver.putExtra("NOTIFICATION", edtMessage.getText().toString());
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastReceiver, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle(edtTitle.getText().toString())
                .setContentText(edtMessage.getText().toString())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setColor(Color.GREEN)
                .addAction(R.mipmap.ic_launcher, "Show", actionIntent)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    public void sendOnChannel2(View view) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle(edtTitle.getText().toString())
                .setContentText(edtMessage.getText().toString())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(2, notification);
    }
}
