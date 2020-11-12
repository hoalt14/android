package com.example.contentproviderexample1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.model.Contact;

public class MainActivity extends AppCompatActivity {

    ListView lvPhoneBook;
    ArrayAdapter<Contact> contactAdapter;

    public static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addViews();
        loadContacts();
        addEvents();
    }

    private void addViews() {
        lvPhoneBook = findViewById(R.id.lvPhoneBook);
        contactAdapter = new ArrayAdapter<Contact>(MainActivity.this,
                android.R.layout.simple_list_item_1);
        lvPhoneBook.setAdapter(contactAdapter);
    }

    private void loadContacts() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,null,
                null,null,null);
        contactAdapter.clear();
        assert cursor != null;
        while (cursor.moveToNext()){
            //Get Name
            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cursor.getString(nameIndex);
            //Get Phone Number
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String phone = cursor.getString(phoneIndex);

            Contact contact = new Contact(name, phone);
            contactAdapter.add(contact);
        }
        cursor.close();
    }

    private void addEvents() {
        lvPhoneBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = contactAdapter.getItem(i);
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri uri = Uri.parse("tel:" + contact.getPhone());
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mnAddContacts){
            Intent intent = new Intent(MainActivity.this,  AddContactActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            loadContacts();
        }
    }
}
