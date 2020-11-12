package com.example.senddataviaintent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    TextView txtNumber;
    Button btnProcess;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        linkViews();
        addEvents();
    }

    private void linkViews() {
        txtNumber = findViewById(R.id.txtNumber);
        btnProcess = findViewById(R.id.btnProcess);

        intent = getIntent();
        txtNumber.setText(intent.getStringExtra("N"));
    }

    private void addEvents() {
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimUocSo();
            }
        });
    }

    private void TimUocSo() {
        int n = Integer.parseInt(txtNumber.getText().toString());
        //Tim uoc so
        ArrayList<Integer> dsUocSo = new ArrayList<>();
        dsUocSo.add(1);
        for (int i = 2; i <= n; i++) {
            if (n % i == 0)
                dsUocSo.add(i);
        }

        intent.putExtra("DanhSachUocSo", dsUocSo);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}