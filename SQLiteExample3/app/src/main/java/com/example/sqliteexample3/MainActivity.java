package com.example.sqliteexample3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    EditText edtProductName, edtDescription;
    Button btnCapture, btnSave, btnCancel;
    ImageView imgPhoto;
    LinearLayout llOpenCamera, llOpenGallery;
    BottomSheetDialog bottomSheetDialog;

    private final int REQUEST_CODE_CAMERA = 1;
    private final int REQUEST_CODE_PHOTO = 2;

    public static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkViews();
        addEvents();
        createBottomSheetDialog();

        createDB();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgPhoto.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            if(uri != null){
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgPhoto.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void linkViews() {
        edtProductName = findViewById(R.id.edtProductName);
        edtDescription = findViewById(R.id.edtDescription);
        btnCapture = findViewById(R.id.btnCapture);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        imgPhoto = findViewById(R.id.imgPhoto);
    }

    private void addEvents() {
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show();
            }
        });
        
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, des;
                name = edtProductName.getText().toString();
                des = edtDescription.getText().toString();
                if(!name.equals("") && !des.equals("")){
                    database.insertData(name.trim(), des.trim(), convertPhoto());
                    Toast.makeText(MainActivity.this, "Insert data successful!", Toast.LENGTH_LONG).show();
                    //Redirect to product list activity
                    startActivity(new Intent(MainActivity.this, Products.class));
                }else{
                    Toast.makeText(MainActivity.this, "Please enter data", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtProductName.setText("");
                edtProductName.requestFocus();
                edtDescription.setText("");
                //imgPhoto.setImageDrawable(null);
                imgPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_insert_photo_carrot_24dp));
            }
        });
    }

    private byte[] convertPhoto() {
        BitmapDrawable drawable = (BitmapDrawable) imgPhoto.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    private void createDB() {
        database = new Database(this, "warranty", null, 1);
        database.queryData("CREATE TABLE IF NOT EXISTS Product(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name VARCHAR(150), Description VARCHAR(250), Photo BLOB)");
    }

    private void createBottomSheetDialog(){
        if(bottomSheetDialog == null){
            View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null);
            llOpenCamera = view.findViewById(R.id.llOpenCamera);
            llOpenCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(MainActivity.this, "Camera", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CODE_CAMERA);
                    bottomSheetDialog.dismiss();
                }
            });
            llOpenGallery = view.findViewById(R.id.llOpenGallery);
            llOpenGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(MainActivity.this, "Photo", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_PHOTO);
                    bottomSheetDialog.dismiss();
                }
            });
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(view);
        }
    }
}
