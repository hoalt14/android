package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    EditText edtFirstName, edtLastName, edtPhone;
    Button btnSave1, btnSave2, btnCancel;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        addViews();
        addEvents();
    }

    private void addViews() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtPhone = findViewById(R.id.edtPhone);
        btnSave1 = findViewById(R.id.btnSave1);
        btnSave2 = findViewById(R.id.btnSave2);
        btnCancel = findViewById(R.id.btnCancel);
        intent = getIntent();
    }

    private void addEvents() {
        btnSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                intent.putExtra(ContactsContract.Intents.Insert.NAME, edtFirstName.getText() + " " + edtLastName.getText());
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, edtPhone.getText());
                startActivity(intent);
                finish();
            }
        });

        btnSave2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri addContactsUri = ContactsContract.Data.CONTENT_URI;

                // Add an empty contact and get the generated Id
                long rawContactId = getRawContactId();

                // Add contact name data
                insertContactDisplayName(addContactsUri, rawContactId, edtFirstName.getText() + " " + edtLastName.getText());
                Toast.makeText(AddContactActivity.this, "Save contact successful!", Toast.LENGTH_LONG).show();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    private long getRawContactId() {
        // Insert an empty contact
        ContentValues values = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        return ContentUris.parseId(rawContactUri);
    }

    // Insert newly created contact display name
    private void insertContactDisplayName(Uri addContactsUri, long rawContactId, String displayName) {
        ContentValues values = new ContentValues();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName);
        getContentResolver().insert(addContactsUri, values);
    }
}