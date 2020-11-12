package com.example.customdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class MyCustomDialog extends Dialog {

    Activity context;
    ImageView imgOk, imgCancel;


    public MyCustomDialog(@NonNull Context context) {
        super(context);
        this.context = (Activity) context;
        setContentView(R.layout.custom_dialog);
        linkViews();
        setCanceledOnTouchOutside(false);
        addEvents();
    }

    private void linkViews() {
        imgOk = findViewById(R.id.imgOk);
        imgCancel = findViewById(R.id.imgCancel);
    }

    private void addEvents() {
        imgOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
