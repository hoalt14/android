package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baitapsqlite.MainActivity;
import com.example.baitapsqlite.R;
import com.example.model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Product> productList;

    public ProductAdapter(MainActivity context, int layout, List<Product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtProductName = view.findViewById(R.id.txtProductName);
            holder.txtProductManufacturer = view.findViewById(R.id.txtProductManufacturer);
            //holder.txtProductPrice = view.findViewById(R.id.txtProductPrice);
            holder.imvDelete = view.findViewById(R.id.imvDelete);
            holder.imvEdit = view.findViewById(R.id.imvEdit);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Product p = productList.get(i);
        holder.txtProductName.setText(p.getProductName());
        holder.txtProductManufacturer.setText(p.getProductManufacturer());
        //holder.txtProductPrice.setText(p.getProductPrice());

        holder.imvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.openDialogEditProduct(p.getProductId(), p.getProductName());
                //context.openDialogEditProduct(p.getProductId(), p.getProductName(), p.getProductManufacturer(), p.getProductPrice());
            }
        });

        holder.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.deleteProduct(p.getProductId(), p.getProductName());
        }
        });

        return view;
    }

    private static class ViewHolder {
        TextView txtProductName, txtProductManufacturer, txtProductPrice;
        ImageView imvDelete, imvEdit;
    }
}
