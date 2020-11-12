package com.example.sqliteexample3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.adapter.ProductAdapter;
import com.example.model.Product;

import java.util.ArrayList;

public class Products extends AppCompatActivity {

    ListView lvProduct;
    ArrayList<Product> productArrayList;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        linkViews();
        getDataFromDB();
    }

    private void linkViews() {
        lvProduct = findViewById(R.id.lvProduct);
        productArrayList = new ArrayList<>();
        adapter = new ProductAdapter(this, R.layout.item_row, productArrayList);
        lvProduct.setAdapter(adapter);
    }

    private void getDataFromDB() {
        Cursor cursor = MainActivity.database.getData("SELECT * FROM Product");
        while (cursor.moveToNext()){
            productArrayList.add(new Product(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2), cursor.getBlob(3)));
        }
        cursor.close();
    }
}
