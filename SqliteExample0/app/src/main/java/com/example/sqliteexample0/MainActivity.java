package com.example.sqliteexample0;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.model.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "product_db.db";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;

    ListView lvProduct;
    ArrayAdapter<Product> adapter;

    DecimalFormat currency = new DecimalFormat("###.###");

    public static Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyDataBase();
        addViews();
        addEvents();
        //loadDataFromDB();
    }

    private void addEvents() {
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProduct = adapter.getItem(i);
            }
        });
        lvProduct.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProduct = adapter.getItem(i);
                return false;
            }
        });
    }

    private void loadDataFromDB() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM Product WHERE ProductId = ? OR ProductId = ?",
//                new String[] {"2", "3"});
        Cursor cursor = database.query("Product", null,
                null, null,
                null, null, null);
        adapter.clear();
        int productId;
        String productName;
        double productPrice;
        while(cursor.moveToNext()){
            productId = cursor.getInt(0);
            productName = cursor.getString(1);
            productPrice = cursor.getDouble(2);
            Product p = new Product(productId, productName, productPrice + "");
            adapter.add(p);
        }
        cursor.close();
    }

    private void addViews() {
        lvProduct = findViewById(R.id.lvProduct);
        adapter = new ArrayAdapter<Product>(MainActivity.this, android.R.layout.simple_list_item_1);
        lvProduct.setAdapter(adapter);

        registerForContextMenu(lvProduct);
    }

    private void copyDataBase(){
        try{
            File dbFile = getDatabasePath(DATABASE_NAME);
            if(!dbFile.exists()){
                if(CopyDBFromAsset()){
                    Toast.makeText(MainActivity.this,
                            "Copy database successful!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Copy database fail!", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Log.e("Error: ", e.toString());
        }
    }

    private boolean CopyDBFromAsset() {
        String dbPath = getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
        try {
            InputStream inputStream = getAssets().open(DATABASE_NAME);
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(dbPath);
            byte[] buffer = new byte[1024];
            int length;
            while((length=inputStream.read(buffer))>0){
                outputStream.write(buffer,0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return  true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mnAddProduct){
            Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.mnInfo){
            //.......
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mnEdit){
            if(selectedProduct != null){
                Intent intent = new Intent(MainActivity.this, EditProductActivity.class);
                startActivity(intent);
            }
        }else if(item.getItemId() == R.id.mnDelete){
            if(selectedProduct != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirm delete product");
                builder.setMessage("Are you sure you want to delete this product: " + selectedProduct + "?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int flag = database.delete("Product", "ProductId=?",
                                new String[]{selectedProduct.getProductId() + ""});
                        if(flag > 0)
                            loadDataFromDB();
                        else
                            Toast.makeText(MainActivity.this, "Delete fail!", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.create().show();
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataFromDB();
    }
}
