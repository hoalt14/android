package com.example.sqliteexample2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.adapter.SongAdapter;
import com.example.model.Song;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "songs.db";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static final String TABLE_NAME = "song";
    public static SQLiteDatabase database = null;

    TabHost tabHost;
    public static int selectedTab = 1;

    ListView lvSongs, lvFavouriteSongs;
    public SongAdapter songAdapter, favouriteSongAdapter;
    public ArrayList<Song> songArrayList, favouriteSongArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyDataBase();
        showTabHost();
        addViews();
        loadDataFromDB();
        addEvents();
    }

    private void addEvents() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if(s.equals("tab1")){
                    loadDataFromDB();
                    selectedTab = 1;
                }else{
                    loadFavouriteSongs();
                    selectedTab = 2;
                }
            }
        });
    }

    public void loadFavouriteSongs() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Favourite = ?",
                new String[] {"1"});
        favouriteSongArrayList.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            int songCode = cursor.getInt(1);
            String songName = cursor.getString(2);
            String singer = cursor.getString(3);
            int favourite = cursor.getInt(4);
            Song s = new Song(id, songCode, songName, singer, favourite);
            favouriteSongArrayList.add(s);
        }
        favouriteSongAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private void showTabHost() {
        tabHost = findViewById(R.id.host);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("All Songs");
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Favourite Songs");
        tabHost.addTab(tab2);
    }

    private void addViews() {
        lvSongs = findViewById(R.id.lvSongs);
        songArrayList = new ArrayList<>();
        songAdapter = new SongAdapter(MainActivity.this, R.layout.item_row, songArrayList);
        lvSongs.setAdapter(songAdapter);

        lvFavouriteSongs = findViewById(R.id.lvFavouriteSongs);
        favouriteSongArrayList = new ArrayList<>();
        favouriteSongAdapter = new SongAdapter(MainActivity.this, R.layout.item_row, favouriteSongArrayList);
        lvFavouriteSongs.setAdapter(favouriteSongAdapter);
    }

    private void copyDataBase(){
        try{
            File dbFile = getDatabasePath(DATABASE_NAME);
            if(!dbFile.exists()){
                if(copyDBFromAsset()){
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

    private boolean copyDBFromAsset() {
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

    private void loadDataFromDB() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME,
                null);
        songArrayList.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            int songCode = cursor.getInt(1);
            String songName = cursor.getString(2);
            String singer = cursor.getString(3);
            int favourite = cursor.getInt(4);
            Song s = new Song(id, songCode, songName, singer, favourite);
            songArrayList.add(s);
        }
        songAdapter.notifyDataSetChanged();
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem mnSearch = menu.findItem(R.id.mnSearch);
        SearchView searchView = (SearchView) mnSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchSong(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchSong(String s) {
        Cursor cursor;
        Song ss;
        if(selectedTab == 1){
            cursor = database.rawQuery(
                    "SELECT * FROM " + TABLE_NAME + " WHERE SongCode LIKE ? OR SongName LIKE ? OR Singer LIKE ?",
                    new String[]{"%" + s + "%", "%" + s + "%", "%" + s + "%"});
            songArrayList.clear();
            while(cursor.moveToNext()){
                int id = cursor.getInt(0);
                int songCode = cursor.getInt(1);
                String songName = cursor.getString(2);
                String singer = cursor.getString(3);
                int favourite = cursor.getInt(4);
                ss = new Song(id, songCode, songName, singer, favourite);
                songArrayList.add(ss);
            }
            songAdapter.notifyDataSetChanged();
            cursor.close();
        }else if(selectedTab == 2){
            cursor = database.rawQuery(
                    "SELECT * FROM " + TABLE_NAME + " WHERE Favourite = ? AND (SongCode LIKE ? OR SongName LIKE ? OR Singer LIKE ?)",
                    new String[]{"1", "%" + s + "%", "%" + s + "%", "%" + s + "%"});
            favouriteSongArrayList.clear();
            while(cursor.moveToNext()){
                int id = cursor.getInt(0);
                int songCode = cursor.getInt(1);
                String songName = cursor.getString(2);
                String singer = cursor.getString(3);
                int favourite = cursor.getInt(4);
                ss = new Song(id, songCode, songName, singer, favourite);
                favouriteSongArrayList.add(ss);
            }
            favouriteSongAdapter.notifyDataSetChanged();
            cursor.close();
        }
    }
}
