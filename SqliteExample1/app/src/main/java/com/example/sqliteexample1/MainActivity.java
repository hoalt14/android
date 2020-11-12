package com.example.sqliteexample1;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.adapter.TaskAdapter;
import com.example.model.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Databases databases;
    ListView lvTask;
    ArrayList<Task> taskArrayList;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addViews();
        PrepareDB();
        GetData();
    }

    private void addViews() {
        lvTask = findViewById(R.id.lvTask);
        taskArrayList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, R.layout.item_row, taskArrayList);
        lvTask.setAdapter(taskAdapter);
    }

    private void PrepareDB() {
        // create database
        databases = new Databases(this, "note_db.sqlite", null, 1);
        // create table
        databases.QueryData("CREATE TABLE IF NOT EXISTS Works(Id INTEGER PRIMARY KEY AUTOINCREMENT, " + "WorkName VARCHAR(200))");
        // insert data
//        databases.QueryData("INSERT INTO Works VALUES(null, 'Fix bugs')");
//        databases.QueryData("INSERT INTO Works VALUES(null, 'Coding')");
//        databases.QueryData("INSERT INTO Works VALUES(null, 'Meeting')");
//        databases.QueryData("INSERT INTO Works VALUES(null, 'Walking')");
    }

    private void GetData() {
        Cursor c = databases.GetData("SELECT * FROM Works");
        taskArrayList.clear();
        while (c.moveToNext()) {
            int taskId = c.getInt(0);
            String taskName = c.getString(1);
            // log.i ("Works: ", taskId + " - " + taskName);
            taskArrayList.add(new Task(taskId, taskName));
        }
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_task, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mnAddTask) {
            openDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCanceledOnTouchOutside(false);

        final EditText edtTaskName = dialog.findViewById(R.id.edtTask);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = edtTaskName.getText().toString();
                if (taskName.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter task name!", Toast.LENGTH_SHORT).show();
                } else {
                    databases.QueryData("INSERT INTO Works VALUES(null, '" + taskName + "')");
                    Toast.makeText(MainActivity.this, "Added task successful!", Toast.LENGTH_LONG).show();
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

    public void openDialogEditTask(final int taskID, String taskName) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_edit);

        final EditText edtTaskName = dialog.findViewById(R.id.edtEditTask);
        Button btnEditTask = dialog.findViewById(R.id.btnEdit);
        Button btnCancel = dialog.findViewById(R.id.btnCancelEdit);

        edtTaskName.setText(taskName);

        btnEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTaskName = edtTaskName.getText().toString();
                databases.QueryData("UPDATE Works SET WorkName = '" + newTaskName + "' WHERE Id = " + taskID);
                Toast.makeText(MainActivity.this, "Edited task successful!", Toast.LENGTH_LONG).show();
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

    public void deleteTask(final int taskId, String taskName) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure you want to delete this task; " + taskName + "?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databases.QueryData("DELETE FROM Works WHERE Id = " + taskId);
                Toast.makeText(MainActivity.this, "Deleted task successful!", Toast.LENGTH_LONG).show();
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