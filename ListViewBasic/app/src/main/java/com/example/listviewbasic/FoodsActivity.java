package com.example.listviewbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FoodsActivity extends AppCompatActivity {

    String [] foods;
    ArrayAdapter<String> adapter;
    ListView lsvFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);

        listViews();
    }

    private void listViews() {
        lsvFoods = findViewById(R.id.lsvFoods);
        // lay du lieu tu file res/values/strings.xml
        foods = getResources().getStringArray(R.array.arrFoods);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foods);
        lsvFoods.setAdapter(adapter);
    }
}