package com.example.baitapsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.ProductAdapter;
import com.example.model.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Databases databases;
    ListView lvProduct;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addViews();
        PrepareDB();
        GetData();
    }

    private void addViews() {
        lvProduct = findViewById(R.id.lvProduct);
        productArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, R.layout.item_row, productArrayList);
        lvProduct.setAdapter(productAdapter);
    }

    private void PrepareDB() {
        databases = new Databases(this, "product.sqlite", null, 1);

        databases.QueryData("CREATE TABLE IF NOT EXISTS Products(Id INTEGER PRIMARY KEY AUTOINCREMENT, " + "ProductName VARCHAR(200), " + "ProductManufacturer VARCHAR(200))");
        //databases.QueryData("CREATE TABLE IF NOT EXISTS Products(Id INTEGER PRIMARY KEY AUTOINCREMENT, " + "ProductName VARCHAR(200), " + "ProductManufacturer VARCHAR(200), " + "ProductPrice INTEGER)");
    }

    private void GetData() {
        Cursor c = databases.GetData("SELECT * FROM Products");
        productArrayList.clear();
        while (c.moveToNext()) {
            int productId = c.getInt(0);
            String productName = c.getString(1);
            String productManufacturer = c.getString(2);
            //int productPrice = c.getInt(3);

            productArrayList.add(new Product(productId, productName, productManufacturer));
            //productArrayList.add(new Product(productId, productName, productManufacturer, productPrice));
        }
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_product, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnAddProduct) {
            openDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCanceledOnTouchOutside(false);

        final EditText edtProductName = dialog.findViewById(R.id.edtProductName);
        final EditText edtProductManufacturer = dialog.findViewById(R.id.edtProductManufacturer);
        //final EditText edtProductPrice = dialog.findViewById(R.id.edtProductPrice);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = edtProductName.getText().toString();
                String productManufacturer = edtProductManufacturer.getText().toString();
                //String productPrice = edtProductPrice.getText().toString();
                //if (productName.equals("") || productManufacturer.equals("") || productPrice.equals("")) {
                if (productName.equals("") || productManufacturer.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter something!", Toast.LENGTH_SHORT).show();
                } else {
                    databases.QueryData("INSERT INTO Products VALUES(null, '" + productName + "', '" + productManufacturer + "')");
                    //databases.QueryData("INSERT INTO Products VALUES(null, '" + productName + "', '" + productManufacturer + "', '" + productPrice + "')");
                    Toast.makeText(MainActivity.this, "Added product successful!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    GetData();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //public void openDialogEditProduct(final int productId, String productName, String productManufacturer, Integer productPrice) {
    public void openDialogEditProduct(final int productId, String productName) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_edit);

        final EditText edtProductName = dialog.findViewById(R.id.edtEditProductName);
        //final EditText edtProductManufacturer = dialog.findViewById(R.id.edtEditProductManufacturer);
        //final EditText edtProductPrice = dialog.findViewById(R.id.edtEditProductPrice);
        Button btnEditProduct = dialog.findViewById(R.id.btnEdit);
        Button btnCancel = dialog.findViewById(R.id.btnCancelEdit);

        edtProductName.setText(productName);
        //edtProductManufacturer.setText(productManufacturer);
        //edtProductPrice.setTextSize(productPrice);

        btnEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newProductName = edtProductName.getText().toString();
                //String newProductManufacturer = edtProductManufacturer.getText().toString();
                //String newProductPrice = edtProductPrice.getText().toString();
                //databases.QueryData("UPDATE Products SET ProductName = '" + newProductName + "', ProductManufacturer = '" + newProductManufacturer + "', ProductPrice = '" + newProductPrice + "' WHERE Id = " + productId);
                databases.QueryData("UPDATE Products SET ProductName = '" + newProductName + "' WHERE Id = " + productId);
                Toast.makeText(MainActivity.this, "Edited product successful!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                GetData();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void deleteProduct(final int productId, String productName) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure you want to delete this product; " + productName + "?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databases.QueryData("DELETE FROM Products WHERE Id = " + productId);
                Toast.makeText(MainActivity.this, "Deleted product successful!", Toast.LENGTH_LONG).show();
                GetData();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.show();
    }
}