package com.example.senddataviaintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity3 extends AppCompatActivity {

    EditText edtPhone;
    Button btnDial;
    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        linkViews();
        addEvents();
    }

    private void linkViews() {
        edtPhone = findViewById(R.id.edtPhone);
        btnDial = findViewById(R.id.btnDial);
        btnCall = findViewById(R.id.btnCall);
    }

    private void addEvents() {
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyQuaySo();
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyGoi();
            }
        });
    }

    private void xulyQuaySo() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri uri = Uri.parse("tel:" + edtPhone.getText().toString());
        intent.setData(uri);
        startActivity(intent);
    }

    private void xulyGoi() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + edtPhone.getText().toString());
        intent.setData(uri);
        startActivity(intent);
    }

    public void openSite(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("https://binance.com");
        intent.setData(uri);
        startActivity(intent);
    }
}