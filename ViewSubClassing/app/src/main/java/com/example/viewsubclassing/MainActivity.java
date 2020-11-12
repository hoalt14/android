package com.example.viewsubclassing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnShow_click(View view) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView txtMsg = new TextView(this);
        txtMsg.setText("Chao mung ban den voi khoa hoc");
        txtMsg.setTextSize(24);
        txtMsg.setLayoutParams(params);
        linearLayout.addView(txtMsg);

        Button btnBack = new androidx.appcompat.widget.AppCompatButton(this) {
            @Override
            public boolean performClick() {
                setContentView(R.layout.activity_main);
                return super.performClick();
            }
        };

        btnBack.setText("Back");
        btnBack.setLayoutParams(params);
        linearLayout.addView(btnBack);

        setContentView(linearLayout);
    }
}