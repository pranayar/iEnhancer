package com.pranay.ienhancer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class Gallery extends AppCompatActivity {
    private ImageView imageView ;
    private Bitmap img;
    private Button tbutton;
    String[] arr1={"Remove blur","Remove scratch","Enhance resolution","Denoise","Colorize"};
    /*
        b2=blur removal
        b3=Scratch Removal
        b4=Resolution Enhancement
        b5 = denoising
        b6=colorization
         */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Intent intent1 = getIntent();

//      int value = Integer.valueOf(option.getStringExtra("message_key"));
        String value =intent1.getStringExtra("key");
        Log.i("info",""+value);

        tbutton=(Button) findViewById(R.id.tbutton);
        tbutton.setText(arr1[Integer.valueOf(value)-2]);
        imageView = (ImageView) findViewById(R.id.imageView2);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            imageView.setImageURI(data.getData());

            Uri uri = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}