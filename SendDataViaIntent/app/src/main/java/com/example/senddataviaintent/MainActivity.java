package com.example.senddataviaintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtNumber;
    Button btnSend;
    TextView txtResult;

    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkViews();
        addEvents();
    }

    private void linkViews() {
        edtNumber = findViewById(R.id.edtNumber);
        btnSend = findViewById(R.id.btnSend);
        txtResult = findViewById(R.id.txtResult);
    }

    private void addEvents() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSecondActivity();
            }
        });
    }

    private void openSecondActivity() {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("N", edtNumber.getText().toString());
        startActivityForResult(intent, REQUEST_CODE);
        //startActivity(intent);
    }

    // go onActivi... se hien ra o duoi
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<Integer> ds = data.getIntegerArrayListExtra("DashSachUocSo");
            for (int i : ds) {
                txtResult.append(i + "\n");
            }
        }
    }
}