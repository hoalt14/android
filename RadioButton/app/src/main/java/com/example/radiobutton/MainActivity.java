package com.example.radiobutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    RadioGroup radgDanhGia;
    Button btnConfirm;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkViews();
    }

    private void linkViews(){
        radgDanhGia = findViewById(R.id.radgDanhGia);
        btnConfirm = findViewById(R.id.btnConfirm);
        txtResult = findViewById(R.id.txtResult);
    }

    public void Confirm(View view) {
        String s = "Bạn đã đánh giá:";
        RadioGroup group = findViewById(R.id.radgDanhGia);
        int id = group.getCheckedRadioButtonId();
        if (id > 0){
            RadioButton radioButton = findViewById(id);
            s += radioButton.getText();
        }
        txtResult.setText(s);
    }
}