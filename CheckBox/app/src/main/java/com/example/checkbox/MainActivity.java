package com.example.checkbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CheckBox chkK, chkFilm, chkClipTV;
    Button btnConfirm;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkViews();
    }

    private void linkViews() {
        chkK = findViewById(R.id.chkK);
        chkFilm = findViewById(R.id.chkFilm);
        chkClipTV = findViewById(R.id.chkClipTV);
        btnConfirm = findViewById(R.id.btnConfirm);
        txtResult = findViewById(R.id.txtResult);
    }

    public void Confirm(View view) {
        String s = "Bạn đã chọn:";
        if(chkK.isChecked())
            s += chkK.getText().toString() + ", ";
        if(chkFilm.isChecked())
            s += chkFilm.getText().toString() + ", ";
        if(chkClipTV.isChecked())
            s += chkClipTV.getText().toString() + ", ";

        txtResult.setText(s);
    }
}