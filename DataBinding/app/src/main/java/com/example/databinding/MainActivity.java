package com.example.databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.databinding.databinding.ActivityMainBinding;
import com.example.model.Product;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    Product p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        p = new Product(1, "Tiger");
        mainBinding.setProduct(p);
    }

    public void change(View view) {
        p.setProductCode(2);
        p.setProductName("Saporo");
        String info = p.getProductCode() + " - " + p.getProductName();
        Toast.makeText(MainActivity.this, info, Toast.LENGTH_LONG).show();
    }

    public void show(View view) {
        String info = p.getProductCode() + " - " + p.getProductName();
        Toast.makeText(MainActivity.this, info, Toast.LENGTH_LONG).show();
    }
}