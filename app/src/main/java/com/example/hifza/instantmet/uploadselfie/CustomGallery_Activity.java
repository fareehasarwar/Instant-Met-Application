package com.example.hifza.instantmet.uploadselfie;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hifza.instantmet.R;
import com.example.hifza.instantmet.adapter.Image_Gallery_Adapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Fareeha on 1/10/2018.
 */

public class CustomGallery_Activity extends AppCompatActivity {
    private static final String TAG ="tag" ;
    String mCurrentPhotoPath;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static Button selectImages;
    private static GridView galleryImagesGridView;
    private static ArrayList<String> galleryImageUrls;
    private static Image_Gallery_Adapter imagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customgallery_activity);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(CustomGallery_Activity.this));
        isStoragePermissionGranted();
        initViews();
        fetchGalleryImages();
        setUpGridView();
        galleryImagesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView=view.findViewById(R.id.galleryImageView);

              //  Bitmap image= imageView.getDrawingCache();
                String uri="file://" + galleryImageUrls.get(position);
                //Toast.makeText(CustomGallery_Activity.this,"clicked",Toast.LENGTH_SHORT).show();
                //  String result = MediaStore.Images.Media.insertImage(getContentResolver(), image, "", "");

                //  Uri  imageFileUri = Uri.parse(result);
                // Toast.makeText(CustomGallery_Activity.this,uri,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(CustomGallery_Activity.this,Upload_Iimage.class);
                intent.putExtra("image",uri);
                intent.putExtra("string", "Gallery");
                startActivity(intent);

            }
        });

    }
    public void OpenCamera(View view)
    {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("tag", "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, 2);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK && reqCode == 2) {
            Bitmap mImageBitmap = null;
            Intent intent = new Intent(CustomGallery_Activity.this, Upload_Iimage.class);
            intent.putExtra("image", mCurrentPhotoPath);
            intent.putExtra("string", "Camera");
            startActivity(intent);

            try {

                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // imageView.setImageBitmap(mImageBitmap);
        }
    }



    private void initViews() {

        galleryImagesGridView = (GridView) findViewById(R.id.galleryImagesGridView);

    }
    private void fetchGalleryImages() {
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};//get all columns of type images
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;//order data by date
        Cursor imagecursor = managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");//get all data in Cursor by sorting in DESC order

        galleryImageUrls = new ArrayList<String>();//Init array


        //Loop to cursor count
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);//get column index
            galleryImageUrls.add(imagecursor.getString(dataColumnIndex));//get Image from column index
            System.out.println("Array path" + galleryImageUrls.get(i));
        }


    }
    private void setUpGridView() {
        imagesAdapter = new Image_Gallery_Adapter(CustomGallery_Activity.this, galleryImageUrls, true);
        galleryImagesGridView.setAdapter(imagesAdapter);

    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");

                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);

                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.backanimation2, R.anim.backanimation1);
    }

}
