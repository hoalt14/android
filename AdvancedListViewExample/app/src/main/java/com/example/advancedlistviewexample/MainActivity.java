package com.example.advancedlistviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.SanPhamAdapter;
import com.example.model.SanPham;

public class MainActivity extends AppCompatActivity {

    ListView lvSanPham;
    SanPhamAdapter sanPhamAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addViews();
        initData();
        addEvents();
    }

    private void initData() {
        sanPhamAdapter.add(new SanPham(R.drawable.tiger, "Tiger", 14000));
        sanPhamAdapter.add(new SanPham(R.drawable.heiniken, "Heineken", 18000));
        sanPhamAdapter.add(new SanPham(R.drawable.saigon, "Sài Gòn", 15000));
        sanPhamAdapter.add(new SanPham(R.drawable.larue, "Larue", 12000));
        sanPhamAdapter.add(new SanPham(R.drawable.sapporo, "Sapporo", 17000));
        sanPhamAdapter.add(new SanPham(R.drawable.hanoi, "Hà Nội", 16000));
        sanPhamAdapter.add(new SanPham(R.drawable.beer333, "Bia 333", 15000));
    }

    private void addViews() {
        lvSanPham = findViewById(R.id.lvSanPham);
        sanPhamAdapter = new SanPhamAdapter(MainActivity.this, R.layout.item_list);
        lvSanPham.setAdapter(sanPhamAdapter);
    }

    private void addEvents() {
        lvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SanPham sp = sanPhamAdapter.getItem(i);
                Toast.makeText(MainActivity.this, sp.getTen(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
