package com.example.viewbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imgViewPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkViews();
    }

    private void linkViews() {
        imgViewPhoto = findViewById(R.id.imgViewPhoto);
    }

    public void changeImage(View view) {
        if (imgViewPhoto.getTag() == null || imgViewPhoto.getTag().equals("android")) {
            imgViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ios));
            imgViewPhoto.setTag("ios");
        }
        else {
            imgViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.android));
            imgViewPhoto.setTag("android");
        }
    }

    public void closeImage(View view) {
        finish();
    }
}