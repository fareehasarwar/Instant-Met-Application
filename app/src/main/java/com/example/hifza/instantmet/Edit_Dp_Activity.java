package com.example.hifza.instantmet;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hifza.instantmet.login.Session;
import com.example.hifza.instantmet.login.Signup_Activity;
import com.example.hifza.instantmet.uploadselfie.CustomGallery_Activity;
import com.example.hifza.instantmet.uploadselfie.Upload_Iimage;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Fareeha on 2/28/2018.
 */

public class Edit_Dp_Activity extends AppCompatActivity {
    ImageView dp_image;
    String url,uid;
    RelativeLayout back,edit_img;
    Session session;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_dp_activity);
        dp_image=findViewById(R.id.dp_img);
        session=new Session(Edit_Dp_Activity.this);
        uid=session.getUserId();
        if(getIntent().getStringExtra("dp")!=null)
        {
         url= getIntent().getStringExtra("dp");
       Glide.with(this).load(url).into(dp_image);
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void editImage(View view)
    {
        captureImageFromGallery();
    }
    public void captureImageFromGallery()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK && reqCode == 1) {
            try {
                final Uri imageUri = data.getData();

                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                dp_image.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Edit_Dp_Activity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }

    }
    public void backToActivity(View view)
    {

Intent intent=new Intent(Edit_Dp_Activity.this,ProfileActivity.class);
startActivity(intent);
    }

}
