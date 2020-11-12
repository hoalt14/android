package com.example.broadcastreceiver_readsms2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SmsReceiver extends BroadcastReceiver {
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault());

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        Object[] arrMessages = (Object[]) bundle.get("pdus");
        assert arrMessages != null;
        String phone, time, content;
        Date date;
        byte[] bytes;
        for (int i = 0; i < arrMessages.length; i++) {
            bytes = (byte[]) arrMessages[i];
            SmsMessage message = SmsMessage.createFromPdu(bytes);
            phone = message.getDisplayOriginatingAddress();
            date = new Date(message.getTimestampMillis());
            time = dateFormat.format(date);
            content = message.getMessageBody();
            Toast.makeText(context, "Phone: " + phone + "\nTime: " + time + "\nContent: " + content, Toast.LENGTH_LONG).show();
        }
    }
}
