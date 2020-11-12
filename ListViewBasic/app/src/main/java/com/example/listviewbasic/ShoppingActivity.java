package com.example.listviewbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.model.Product;

public class ShoppingActivity extends AppCompatActivity {

    EditText edtName, edtColor;
    Button btnAdd;
    ListView lsvProduct;

    ArrayAdapter<Product> productArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        linksView();
        addEvents();
    }

    private void linksView() {
        edtName = findViewById(R.id.edtProductName);
        edtColor = findViewById(R.id.edtProductColor);
        btnAdd = findViewById(R.id.btnAdd);
        lsvProduct = findViewById(R.id.lsvProduct);

        productArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lsvProduct.setAdapter(productArrayAdapter);
    }

    private void addEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appProduct();
            }
        });

        lsvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product p = productArrayAdapter.getItem(i);
                Toast.makeText(ShoppingActivity.this, p.getName() + " - " + p.getColor(), Toast.LENGTH_SHORT).show();
            }
        });

        lsvProduct.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product p = productArrayAdapter.getItem(i);
                productArrayAdapter.remove(p);
                return false;
            }
        });
    }

    private void appProduct() {
        String name = edtName.getText().toString();
        String color = edtColor.getText().toString();
        productArrayAdapter.add(new Product(name, color));
    }
}