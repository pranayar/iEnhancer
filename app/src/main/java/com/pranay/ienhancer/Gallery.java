package com.pranay.ienhancer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pranay.ienhancer.ml.LandscapeImageColorization128;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Gallery extends AppCompatActivity {
    private ImageView imageView ;
    private Bitmap img;
    private Button tbutton;
    String value;
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
        try {
            value = intent1.getStringExtra("key");
            Log.i("info", "" + value);
            int x=Integer.valueOf(value);
            tbutton = (Button) findViewById(R.id.tbutton);
            tbutton.setText(arr1[x- 2]);
            imageView = (ImageView) findViewById(R.id.imageView2);
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 100);

        if(x==6)
        {
            Log.i("IF","if has been entered");
            tbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    colorise();
                }

            });
     }
        }
        catch(Exception e)
        {
            Log.i("THADOMAL",e.toString());
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {

            imageView.setImageURI(data.getData());

            Uri uri = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

            } catch (Exception e) {
                Log.i("TSEC",e.toString());
            }

        }
//        Bitmap bitmap=(Bitmap)data.getExtras().get("data");
//        img=bitmap;


    }
//    public void colorise() {
//        img=Bitmap.createScaledBitmap(img,128, 128,true);
//
//        try {
//            LandscapeImageColorization128 model = LandscapeImageColorization128.newInstance(getApplicationContext());
//
//            // Creates inputs for reference.
//            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 128, 128, 3}, DataType.UINT8);
//
//            TensorImage tensorImage=new TensorImage(DataType.UINT8);
//            tensorImage.load(img);
//            ByteBuffer byteBuffer=tensorImage.getBuffer();
//            inputFeature0.loadBuffer(byteBuffer);
//
//             // Runs model inference and gets result.
//            LandscapeImageColorization128.Outputs outputs = model.process(inputFeature0);
//            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
//
//            Bitmap bitmap_output = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888);
//            bitmap_output.copyPixelsFromBuffer(outputFeature0.getBuffer());
//
//            imageView.setImageBitmap(bitmap_output);
//
//            tbutton.setText("DONE");
//            // Releases model resources if no longer used.
//            model.close();
//        } catch (Exception e) {
//            Log.i("colorise exception",e.toString()+img.getHeight()+"--"+img.getWidth());
//            // TODO Handle the exception
//        }
//    }
public void colorise() {
    try {
        LandscapeImageColorization128 model = LandscapeImageColorization128.newInstance(getApplicationContext());

        // Create a scaled version of the bitmap with 128x128 size
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(img, 128, 128, true);

        // Convert the scaled bitmap to a byte buffer
        ByteBuffer byteBuffer= ByteBuffer.allocateDirect(4*128*128*3);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intvalues=new int[128*128];
        scaledBitmap.getPixels(intvalues,0,scaledBitmap.getWidth(),0,0,scaledBitmap.getWidth(),scaledBitmap.getHeight());
        int pixel=0;
        for(int i=0;i<128;i++)
        {
            for(int j=0;j<128;j++) {
                int val=intvalues[pixel++];
                byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f /255));
                byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f/255));
                byteBuffer.putFloat((val & 0xFF) * (1.f/255));
            }
        }

        // Create the input tensor
        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 128, 128, 3}, DataType.FLOAT32);
        inputFeature0.loadBuffer(byteBuffer);

        // Runs model inference and gets result.
        LandscapeImageColorization128.Outputs outputs = model.process(inputFeature0);
        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

        // Create the output bitmap from the output tensor
        Bitmap bitmap_output = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888);
        float[] outputValues = outputFeature0.getFloatArray();
        int pixelIndex = 0;
        for (int y = 0; y < 128; y++) {
            for (int x = 0; x < 128; x++) {
                int r = (int) (outputValues[pixelIndex++] * 255);
                int g = (int) (outputValues[pixelIndex++] * 255);
                int b = (int) (outputValues[pixelIndex++] * 255);
                int a = 255;
                bitmap_output.setPixel(x, y, (a << 24) | (r << 16) | (g << 8) | b);
            }
        }

        imageView.setImageBitmap(bitmap_output);
        tbutton.setText("DONE");

        // Releases model resources if no longer used.
        model.close();
    } catch (Exception e) {
        Log.i("colorise exception",e.toString());
        // TODO Handle the exception
    }
}

}