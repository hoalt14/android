package com.example.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Song;
import com.example.sqliteexample2.MainActivity;
import com.example.sqliteexample2.R;

import java.util.List;

public class SongAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<Song> songList;

    public SongAdapter(MainActivity context, int layout, List<Song> songList) {
        this.context = context;
        this.layout = layout;
        this.songList = songList;
    }

    @Override
    public int getCount() {
        return songList.size();
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
        final ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtSongCode = view.findViewById(R.id.txtSongCode);
            holder.txtSongName = view.findViewById(R.id.txtSongName);
            holder.txtSinger = view.findViewById(R.id.txtSinger);
            holder.imvDislike = view.findViewById(R.id.imvDislike);
            holder.imvFavourite = view.findViewById(R.id.imvFavourite);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final Song song = songList.get(i);
        holder.txtSongCode.setText(String.valueOf(song.getSongCode()));
        holder.txtSongName.setText(song.getSongName());
        holder.txtSinger.setText(song.getSinger());
        if(song.getFavourite() == 1){
            holder.imvFavourite.setVisibility(View.VISIBLE);
            holder.imvDislike.setVisibility(View.INVISIBLE);
        }else{
            holder.imvFavourite.setVisibility(View.INVISIBLE);
            holder.imvDislike.setVisibility(View.VISIBLE);
        }

        holder.imvFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("Favourite", 0);
                int flag = MainActivity.database.update(MainActivity.TABLE_NAME,
                        values, "Id=?", new String[]{song.getId() + ""});
                if(flag > 0){
                    holder.imvFavourite.setVisibility(View.INVISIBLE);
                    holder.imvDislike.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Removed the song from favourite list successful!", Toast.LENGTH_LONG).show();
                    if(MainActivity.selectedTab == 2){
                        context.favouriteSongArrayList.remove(song);
                        context.favouriteSongAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        holder.imvDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("Favourite", 1);
                int flag = MainActivity.database.update(MainActivity.TABLE_NAME,
                        values, "Id=?", new String[]{song.getId() + ""});
                if(flag > 0){
                    holder.imvFavourite.setVisibility(View.VISIBLE);
                    holder.imvDislike.setVisibility(View.INVISIBLE);
                    Toast.makeText(context, "Add the song successful!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private static class ViewHolder{
        TextView txtSongCode, txtSongName, txtSinger;
        ImageView imvDislike, imvFavourite;
    }
}
