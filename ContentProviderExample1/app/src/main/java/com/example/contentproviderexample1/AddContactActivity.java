package com.example.contentproviderexample1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

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

                intent.putExtra(ContactsContract.Intents.Insert.NAME, edtFirstName.getText()
                        + " " + edtLastName.getText());
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, edtPhone.getText());
                startActivity(intent);
                finish();
            }
        });
        btnSave2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri addContactsUri = ContactsContract.Data.CONTENT_URI;
                //Add an empty contact and get the generated Id
                long rowContactId = getRawContactId();
                //Add contact name data
                insertContactDisplayName(addContactsUri, rowContactId,
                        edtFirstName.getText() + " " + edtLastName.getText());
                //Add phone number
                insertContactPhoneNumber(addContactsUri, rowContactId, edtPhone.getText().toString());
                Toast.makeText(AddContactActivity.this,
                        "Save contact successful!", Toast.LENGTH_LONG).show();
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
        //Insert an empty contact
        ContentValues values = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        return ContentUris.parseId(rawContactUri);
    }

    // Insert newly created contact display name
    private void insertContactDisplayName(Uri addContactsUri, long rawContactId, String displayName)
    {
        ContentValues values = new ContentValues();
        // Each contact must has an id to avoid java.lang.IllegalArgumentException: raw_contact_id is required error.
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mime-type is required error.
        values.put(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // Put contact display name value.
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName);
        getContentResolver().insert(addContactsUri, values);

    }

    private void insertContactPhoneNumber(Uri addContactsUri, long rawContactId, String phoneNumber)
    {
        ContentValues values = new ContentValues();
        // Each contact must has an id to avoid java.lang.IllegalArgumentException: raw_contact_id is required error.
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mime-type is required error.
        values.put(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // Put phone number value.
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);
        getContentResolver().insert(addContactsUri, values);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}
