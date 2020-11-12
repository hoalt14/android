package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.SanPham;
import com.example.recyclerview.R;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    ArrayList<SanPham> dsSanPham;
    Context context;

    public SanPhamAdapter(ArrayList<SanPham> dsSanPham, Context context) {
        this.dsSanPham = dsSanPham;
        this.context = context;
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View customItemView = layoutInflater.inflate(R.layout.custom_item, parent, false);
        return new ViewHolder(customItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.ViewHolder holder, int position) {
        holder.imgPhoto.setImageResource(dsSanPham.get(position).getHinh());
        holder.txtInfo.setText(
                dsSanPham.get(position).getTen() + " - " + String.format("%.0f", dsSanPham.get(position).getGia()) + "VNĐ"
        );
    }

    @Override
    public int getItemCount() {
        return dsSanPham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView txtInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            txtInfo = itemView.findViewById(R.id.txtInfo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RemoveItem(getAdapterPosition());
                    Toast.makeText(context, "Remove" + txtInfo.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void RemoveItem(int pos) {
        dsSanPham.remove(pos);
    }
}
