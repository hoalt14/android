package com.example.viewbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgViewPhoto;
    ImageButton btnChangeImage, btnCloseImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkViews();
        addEvents();
    }

    private void linkViews() {
        imgViewPhoto = findViewById(R.id.imgViewPhoto);
        btnChangeImage = findViewById(R.id.btnChangeImage);
        btnCloseImage = findViewById(R.id.btnCloseImage);
    }

    // ----------- cach 4

    private void addEvents() {
        btnChangeImage.setOnClickListener(this);
        btnCloseImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnChangeImage) {
            if (imgViewPhoto.getTag() == null || imgViewPhoto.getTag().equals("android")) {
                imgViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ios));
                imgViewPhoto.setTag("ios");
            } else {
                imgViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.android));
                imgViewPhoto.setTag("android");
            }
        } else if (view.getId() == R.id.btnCloseImage) {
            finish();
        }
    }


    // ----------------- cach 3

//    private void addEvents() {
//        btnChangeImage.setOnClickListener(myClick);
//        btnCloseImage.setOnClickListener(myClick);
//    }
//
//    View.OnClickListener myClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            if (view.getId() == R.id.btnChangeImage) {
//                if (imgViewPhoto.getTag() == null || imgViewPhoto.getTag().equals("android")) {
//                    imgViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ios));
//                    imgViewPhoto.setTag("ios");
//                } else {
//                    imgViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.android));
//                    imgViewPhoto.setTag("android");
//                }
//            } else if (view.getId() == R.id.btnCloseImage) {
//                finish();
//            }
//        }
//    };

    // ------------ cach 2
//    private void addEvents() {
//        btnChangeImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (imgViewPhoto.getTag() == null || imgViewPhoto.getTag().equals("android")) {
//                    imgViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ios));
//                    imgViewPhoto.setTag("ios");
//                } else {
//                    imgViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.android));
//                    imgViewPhoto.setTag("android");
//                }
//            }
//        });
//
//        btnChangeImage.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Toast.makeText(MainActivity.this, "Hello my name is tofu", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });
//    }

    // --------- cach 1 -------------

//    public void changeImage(View view) {
//        if (imgViewPhoto.getTag() == null || imgViewPhoto.getTag().equals("android")) {
//            imgViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ios));
//            imgViewPhoto.setTag("ios");
//        } else {
//            imgViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.android));
//            imgViewPhoto.setTag("android");
//        }
//    }

//    public void closeImage(View view) {
//        finish();
//    }
}