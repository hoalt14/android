package com.example.listviewbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DrinksActivity extends AppCompatActivity {

    String [] drinks = {"Cocacola", "Pepsi", "Panda", "Orange"};
    ArrayAdapter<String> adapter;
    ListView lsvDrinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        linkViews();
    }

    private void linkViews() {
        lsvDrinks = findViewById(R.id.lsvDrinks);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drinks);
        lsvDrinks.setAdapter(adapter);
    }
}