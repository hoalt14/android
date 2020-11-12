package com.example.contentprovider_calllog;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView lvCallLogs;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addViews();
        readCallLogs();
    }

    private void addViews() {
        lvCallLogs = findViewById(R.id.lvCallLogs);
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
        lvCallLogs.setAdapter(adapter);
    }

    private void readCallLogs() {
        String[] projection = new String[]{CallLog.Calls.DATE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};
        Cursor cursor = getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                CallLog.Calls.DATE + " Desc"
        );
        adapter.clear();
        assert cursor != null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        while (cursor.moveToNext()){
            Date date = new Date(cursor.getLong(0));
            String sDate = dateFormat.format(date);
            String number = cursor.getString(1);
            long duration = cursor.getLong(2);
            adapter.add("Time: " + sDate + "\nPhone: " + number + "\nDuration: " + duration + " s");
        }
        cursor.close();
    }
}
