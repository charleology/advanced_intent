package com.example.advanced_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class StudentInquiry extends AppCompatActivity {

    Button btnCamera, btnGallery, btnSend;
    ImageView ivAvatar;
    EditText etNum, etName;
    int cameraAccess = 1, galleryPick = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_inquiry);

        btnCamera = (Button) findViewById(R.id.btnCamera);
        btnGallery = (Button) findViewById(R.id.btnGallery);
        ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
        btnSend = (Button) findViewById(R.id.btnSendInquiry);
        etName = (EditText) findViewById(R.id.etStudname);
        etNum = (EditText) findViewById(R.id.etStudnum);

        ivAvatar.setImageDrawable(getResources().getDrawable(R.drawable.placeholder));

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, cameraAccess);
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, galleryPick);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNum.getText().toString().equals("") || etName.getText().toString().equals("")){
                    Toast.makeText(StudentInquiry.this, "Both fields cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String tempNum = etNum.getText().toString();
                    String tempName = etName.getText().toString();
                    Intent intent = new Intent(StudentInquiry.this, SendInquiry.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == cameraAccess && resultCode == RESULT_OK) {
            Bitmap bmImage = (Bitmap) data.getExtras().get("data");
            ivAvatar.setImageBitmap(bmImage);
        }
        else if (requestCode == galleryPick && resultCode == RESULT_OK){
            Uri imgUri = data.getData();
            ivAvatar.setImageURI(imgUri);
        }
    }
}