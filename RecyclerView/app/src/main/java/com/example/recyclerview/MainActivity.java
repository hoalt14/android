package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.adapter.SanPhamAdapter;
import com.example.model.SanPham;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.rcvBeer);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        //recyclerView.addItemDecoration(dividerItemDecoration);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_divider);
        dividerItemDecoration.setDrawable(drawable);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<SanPham> dsSanPham = new ArrayList<>();
        dsSanPham.add(new SanPham(R.drawable.heineken, "Heineken", 18000));
        dsSanPham.add(new SanPham(R.drawable.hanoi, "Ha noi", 12000));
        dsSanPham.add(new SanPham(R.drawable.beer333, "333", 15000));
        dsSanPham.add(new SanPham(R.drawable.saigon, "Sai gon", 15000));
        dsSanPham.add(new SanPham(R.drawable.sapporo, "Sapporo", 21000));
        dsSanPham.add(new SanPham(R.drawable.tiger, "Tiger", 18000));
        dsSanPham.add(new SanPham(R.drawable.larue, "Larue", 11000));

        SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(dsSanPham, getApplicationContext());
        recyclerView.setAdapter(sanPhamAdapter);
    }
}