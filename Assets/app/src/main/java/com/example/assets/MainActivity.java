package com.example.assets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtContent;
    ListView lsvFonts;
    ArrayAdapter<String> fontAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addViews();
        addEvents();
    }

    private void addViews() {
        txtContent = findViewById(R.id.txtContent);
        lsvFonts = findViewById(R.id.lsvFonts);
        fontAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
        lsvFonts.setAdapter(fontAdapter);
        try {
            AssetManager assetManager = getAssets();
            String[] arrFonts = assetManager.list("fonts");
            fontAdapter.addAll(arrFonts);
        }catch (Exception e) {
            Log.e("Error: ", e.toString());
        }
    }

    private void addEvents() {
        lsvFonts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fontAdapter.getItem(i));
                txtContent.setTypeface(typeface);
                playAudio();
            }
        });
    }

    private void playAudio() {
        try {
            AssetFileDescriptor assetFileDescriptor = getAssets().openFd("musics/ting.mp3");
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(
                    assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength()
            );
            assetFileDescriptor.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
        }
    }
}