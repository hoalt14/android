package com.example.listviewbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnDrinks, btnFoods, btnShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkViews();
        addEvents();
    }

    private void linkViews() {
        btnDrinks = findViewById(R.id.btnShowDrinks);
        btnFoods = findViewById(R.id.btnShowFoods);
        btnShopping = findViewById(R.id.btnShopping);
    }

    private void addEvents() {
        btnDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DrinksActivity.class);
                startActivity(intent);
            }
        });

        btnFoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FoodsActivity.class);
                startActivity(intent);
            }
        });

        btnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShoppingActivity.class);
                startActivity(intent);
            }
        });
    }
}