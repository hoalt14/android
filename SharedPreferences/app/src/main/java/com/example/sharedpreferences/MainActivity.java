package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    String preferencesName = "my_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void save(View view) {
        SharedPreferences preferences = getSharedPreferences(preferencesName, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("int", 8);
        editor.putFloat("float", 6.8f);
        editor.putLong("long", 36);
        editor.putBoolean("boolean", true);
        editor.putString("string", "Welcome to HUTECH");

        Set<String> ds = new HashSet<>();
        ds.add("Lemon");
        ds.add("Orange");
        ds.add("Mango");
        editor.putStringSet("string_set", ds);

        editor.apply();
    }

    public void load(View view) {
        SharedPreferences preferences = getSharedPreferences(preferencesName, MODE_PRIVATE);
        int i = preferences.getInt("int", 0);
        float f = preferences.getFloat("float", 0.0f);
        long l = preferences.getLong("long", 0);
        boolean b = preferences.getBoolean("boolean", false);
        String s = preferences.getString("string", "");
        Set<String> set = preferences.getStringSet("string_set", null);

        TextView txtContent = findViewById(R.id.txtContent);
        txtContent.setText("");
        txtContent.append("i = " + i + "\n");
        txtContent.append("f = " + i + "\n");
        txtContent.append("l = " + i + "\n");
        txtContent.append("b = " + i + "\n");
        txtContent.append("s = " + i + "\n");

        txtContent.append("String Set: \n");
        StringBuilder builder = new StringBuilder();
        assert set != null;
        for (String x : set) {
            builder.append(x).append("\n");
        }
        txtContent.append(builder.toString());

    }
}