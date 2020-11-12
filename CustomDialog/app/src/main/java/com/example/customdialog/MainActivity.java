package com.example.customdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button btnOpenDialog, btnOpenDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addViews();
        addEvents();
    }

    private void addViews() {
        btnOpenDialog = findViewById(R.id.btnOpenDialog);
        btnOpenDialog2 = findViewById(R.id.btnOpenDialog2);
    }

    private void addEvents() {
        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setCanceledOnTouchOutside(false);

                ImageView imgOk = dialog.findViewById(R.id.imgOk);
                imgOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                ImageView imgCancel = dialog.findViewById(R.id.imgCancel);
                imgCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        btnOpenDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCustomDialog myCustomDialog = new MyCustomDialog(MainActivity.this);
                myCustomDialog.show();
            }
        });
    }


}
