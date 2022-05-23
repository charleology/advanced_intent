package com.example.advanced_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendInquiry extends AppCompatActivity {

    EditText etContact, etMessage;
    Button btnSendMessage, btnSelect;
    int contactAccess=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_inquiry);

        etContact = (EditText) findViewById(R.id.etContact);
        etMessage = (EditText) findViewById(R.id.etMessage);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnSendMessage = (Button) findViewById(R.id.btnSendMessage);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(pickContact, contactAccess);
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" +etContact.getText().toString()));
                intent.putExtra("sms_body", etMessage.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == contactAccess && resultCode == RESULT_OK){
            switch (requestCode) {
                case 1:
                    pickedContact(data);
                    break;
            }
        }
        else{
            Toast.makeText(this, "Contact picking failed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickedContact (Intent data){
        Cursor cur = null;
        try{
            String phoneNum = null;
            Uri uri = data.getData();
            cur = getContentResolver().query(uri, null, null, null, null);
            cur.moveToFirst();
            int phoneIndex = cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNum = cur.getString(phoneIndex);

            etContact.setText(phoneNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}