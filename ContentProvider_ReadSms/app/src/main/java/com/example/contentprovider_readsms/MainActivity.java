package com.example.contentprovider_readsms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView lvSms;
    ArrayAdapter<String> adapter;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addViews();
        readSms();
    }

    private void addViews() {
        lvSms = findViewById(R.id.lvSms);
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
        lvSms.setAdapter(adapter);
    }

    private void readSms() {
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(
            uri,null,null,null,null
        );
        assert cursor != null;
        int phoneColumnIdx = cursor.getColumnIndex("address");
        int timeColumnIdx = cursor.getColumnIndex("date");
        int bodyColumnIdx = cursor.getColumnIndex("body");
        while (cursor.moveToNext()){
            String displayName = getContactName(getApplicationContext(), cursor.getString(phoneColumnIdx));
            if(!displayName.equals(""))
                displayName += " - ";
            adapter.add(displayName + cursor.getString(phoneColumnIdx) + "\n" +
                    dateFormat.format(new Date(cursor.getLong(timeColumnIdx))) + "\n" +
                    cursor.getString(bodyColumnIdx)
            );
        }
        cursor.close();
    }

    public String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri,
                new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME },
                null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = "";
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }
}
