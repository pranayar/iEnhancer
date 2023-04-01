package com.pranay.ienhancer;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    /*
    b2=blur removal
    b3=Scratch Removal
    b4=Resolution Enhancement
    b5 = denoising
    b6=colorization
     */
    Button b2,b3,b4,b5,b6;
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b2 = (Button) findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        b5=(Button)findViewById(R.id.button5);
        b6=(Button)findViewById(R.id.button6);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }

            public void openGallery() {
                Intent intent = new Intent(MainActivity.this,Gallery.class);
                intent.putExtra("key", "2");
                startActivity(intent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }

            public void openGallery() {
                Intent intent = new Intent(MainActivity.this,Gallery.class);
                intent.putExtra("key", "3");
                startActivity(intent);
            }

        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }

            public void openGallery() {
                Intent intent = new Intent(MainActivity.this,Gallery.class);
                intent.putExtra("key", "4");
                startActivity(intent);
            }

        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }

            public void openGallery() {
                Intent intent = new Intent(MainActivity.this,Gallery.class);
                intent.putExtra("key", "5");
                startActivity(intent);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }

            public void openGallery() {
                Intent intent = new Intent(MainActivity.this,Gallery.class);
                intent.putExtra("key", "6");
                startActivity(intent);
            }
        });
    }
}