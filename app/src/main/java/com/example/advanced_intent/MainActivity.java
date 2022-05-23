package com.example.advanced_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etMail, etPass;
    final int minPassLength = 6;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMail = (EditText) findViewById(R.id.etMail);
        etPass = (EditText) findViewById(R.id.etPass);
        btnlogin = (Button) findViewById(R.id.btnLogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {

                    String email = etMail.getText().toString();
                    String password = etPass.getText().toString();

                    Toast.makeText(MainActivity.this,"Login Success",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, StudentInquiry.class);
                    startActivity(intent);
                }
            }
        });
    }

    boolean validateInput() {

        if (etMail.getText().toString().equals("")) {
            etMail.setError("Please Enter Email");
            return false;
        }
        if (etPass.getText().toString().equals("")) {
            etPass.setError("Please Enter Password");
            return false;
        }
        if (!isEmailValid(etMail.getText().toString())) {
            etMail.setError("Please Enter Valid Email");
            return false;
        }
        if (etPass.getText().length() < minPassLength) {
            etPass.setError("Password Length must be more than " + minPassLength + " characters.");
            return false;
        }
        return true;
    }

    boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}