package com.example.hifza.instantmet.uploadselfie;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.example.hifza.instantmet.MainActivity;
import com.example.hifza.instantmet.R;
import com.example.hifza.instantmet.json.EditImageApi;
import com.example.hifza.instantmet.json.GetJsonData;
import com.example.hifza.instantmet.json.PostLoginData;
import com.example.hifza.instantmet.json.PostUploadImage;
import com.example.hifza.instantmet.login.Login_Activity;
import com.example.hifza.instantmet.login.Registration_Activity;
import com.example.hifza.instantmet.login.Session;
import com.example.hifza.instantmet.login.Signup_Activity;
import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.RootObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Fareeha on 1/10/2018.
 */

public class Upload_Iimage extends AppCompatActivity {
    Button open_gallery;
    int h,w,height,width;
    Bitmap bitmap;
    ProgressBar progressBar;
    Uri uri;
    Button upload_btn,cancel_btn;
    ImageView imageView_post;
    Boolean check;
    CircleImageView dp_img;
    TextView name_tv;
   EditText status_edt;
    MultipartBody.Part fileToUpload;
    String image_from;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    String email_txt,uid,api_token,description,dp,name,id,desc,edt_dp;
    SharedPreferences pref;
    Session   session;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image_layout);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(Upload_Iimage.this));
        imageView_post=findViewById(R.id.image_view);
        status_edt=findViewById(R.id.editText);
        upload_btn=findViewById(R.id.openCustomGallery);
        cancel_btn=findViewById(R.id.cancelImagey);
        dp_img=findViewById(R.id.fan_img);
        name_tv=findViewById(R.id.name_tv_post);
        progressBar=findViewById(R.id.progress_bar);
        session = new Session(getApplicationContext());
        uid=session.getUserId();
        dp=session.getDp();
        name=session.getName();
        api_token=session.getApiToken();
        email_txt=session.getEmail();
        name_tv.setText(name);

        Glide.with(Upload_Iimage.this).load(dp).into(dp_img);


        if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {
            // do your stuff..
        }

        if( getIntent().getStringExtra("string") != null) {

            //do here
            upload_btn.setVisibility(View.GONE);
            imageView_post.setVisibility(View.VISIBLE);
            Intent intent=getIntent();

            uri = Uri.parse(intent.getStringExtra("image"));
            Glide.with(this).load(uri).into(imageView_post);
            image_from = intent.getStringExtra("string");

            //  textView.setText(txt);


            cancel_btn.setVisibility(View.VISIBLE);

        }
        if( getIntent().getStringExtra("id") != null) {

            //do here
            upload_btn.setVisibility(View.GONE);
            imageView_post.setVisibility(View.VISIBLE);
            Intent intent=getIntent();

             edt_dp = intent.getStringExtra("image");
            Glide.with(this).load(edt_dp).into(imageView_post);
           desc = intent.getStringExtra("status");
           id=intent.getStringExtra("id");
            image_from=intent.getStringExtra("from");
            status_edt.setText(desc);
            //  textView.setText(txt);


            cancel_btn.setVisibility(View.VISIBLE);

        }




    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void OpenGallery(View view)
    {
        Intent intent = new Intent(Upload_Iimage.this, CustomGallery_Activity.class);

        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(this, R.anim.animation1, R.anim.animation2).toBundle();
      startActivity(intent, bndlanimation);

    }
    public void CancelImage(View view)
    {
        imageView_post.setImageDrawable(null);
        imageView_post.setVisibility(View.INVISIBLE);
        upload_btn.setVisibility(View.VISIBLE);
        cancel_btn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(Upload_Iimage.this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }

    }

public void postImage(View view)
{
   // Toast.makeText(Upload_Iimage.this,"clicked",Toast.LENGTH_SHORT).show();
    progressBar.bringToFront();
   progressBar.setVisibility(View.VISIBLE);
   if(id!=null)
   {
       editImage();
   }
   else {
       UploadImage();
   }

}
public void UploadImage()
{

    Bitmap bitmap = ((GlideBitmapDrawable)imageView_post.getDrawable()).getBitmap();

    Uri uri_img=getImageUri(this,bitmap);
    String filePath = getRealPathFromURIPath(uri_img,Upload_Iimage.this);
    File file = new File(filePath);

    //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
    RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

    fileToUpload = MultipartBody.Part.createFormData("postimage", file.getName(), mFile);
    description=status_edt.getText().toString();





    RequestBody uid_req = RequestBody.create(MediaType.parse("text/plain"), uid);
    RequestBody status_req = RequestBody.create(MediaType.parse("text/plain"), description);
    RequestBody from_req = RequestBody.create(MediaType.parse("text/plain"), image_from);
    RequestBody token_req = RequestBody.create(MediaType.parse("text/plain"),api_token);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://selfie.todaylike.club/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    PostUploadImage uploadImage = retrofit.create(PostUploadImage.class);
    Call<RootObject> fileUpload = uploadImage.uploadImageDetail(token_req, uid_req,status_req,from_req,fileToUpload);
    fileUpload.enqueue(new Callback<RootObject>() {


        @Override
        public void onResponse(Call<RootObject> call, Response<RootObject> response) {

            RootObject studentdata=response.body();
            if(studentdata.getStatus().toString().equals("success"))
            {
                progressBar.setVisibility(View.INVISIBLE);
                finish();
                Intent intent=new Intent(Upload_Iimage.this,MainActivity.class);
                startActivity(intent);
            }
            Toast.makeText(Upload_Iimage.this, studentdata.getStatus() , Toast.LENGTH_LONG).show();
          //  startActivity(new Intent(getApplicationContext(), MainActivity.class));

           /* Session.getInstance(getApplicationContext()).userLoginDetail(studentdata.getData().getApi_token(),studentdata.getData().getId(), studentdata.getData().getName(),studentdata.getData().getEmail(), studentdata.getData().getPassword(), studentdata.getData().getAge(), studentdata.getData().getDp(), studentdata.getData().getGender(), studentdata.getData().getCountry());


            Toast.makeText(Upload_Iimage.this, "Success " + response.body(), Toast.LENGTH_LONG).show();

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
*/

        }
        @Override
        public void onFailure(Call<RootObject> call, Throwable t) {
            Toast.makeText(Upload_Iimage.this,t.toString(),Toast.LENGTH_LONG).show();
            Log.d("log", "Error " + t.getMessage());

        }
    });
}
    public void editImage()
    {

        Bitmap bitmap = ((GlideBitmapDrawable)imageView_post.getDrawable()).getBitmap();

        Uri uri_img=getImageUri(this,bitmap);
        String filePath = getRealPathFromURIPath(uri_img,Upload_Iimage.this);
        File file = new File(filePath);

        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        fileToUpload = MultipartBody.Part.createFormData("postimage", file.getName(), mFile);
        description=status_edt.getText().toString();





        RequestBody uid_req = RequestBody.create(MediaType.parse("text/plain"), uid);
        RequestBody status_req = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody from_req = RequestBody.create(MediaType.parse("text/plain"), image_from);
        RequestBody token_req = RequestBody.create(MediaType.parse("text/plain"),api_token);
        RequestBody id_req = RequestBody.create(MediaType.parse("text/plain"),id);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://selfie.todaylike.club/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EditImageApi uploadImage = retrofit.create(EditImageApi.class);
        Call<RootObject> fileUpload = uploadImage.editImageDetail(token_req, uid_req,id_req,status_req,from_req,fileToUpload);
        fileUpload.enqueue(new Callback<RootObject>() {


            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {

                RootObject studentdata=response.body();
                ArrayList<Data> array_data= (ArrayList<Data>) studentdata.getDataarray();
                if(studentdata.getStatus().toString().equals("success"))
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    finish();
                    Intent intent=new Intent(Upload_Iimage.this,MainActivity.class);
                    startActivity(intent);
                }
                Toast.makeText(Upload_Iimage.this, studentdata.getStatus() , Toast.LENGTH_LONG).show();
                //  startActivity(new Intent(getApplicationContext(), MainActivity.class));

           /* Session.getInstance(getApplicationContext()).userLoginDetail(studentdata.getData().getApi_token(),studentdata.getData().getId(), studentdata.getData().getName(),studentdata.getData().getEmail(), studentdata.getData().getPassword(), studentdata.getData().getAge(), studentdata.getData().getDp(), studentdata.getData().getGender(), studentdata.getData().getCountry());


            Toast.makeText(Upload_Iimage.this, "Success " + response.body(), Toast.LENGTH_LONG).show();

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
*/

            }
            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                Toast.makeText(Upload_Iimage.this,t.toString(),Toast.LENGTH_LONG).show();
                Log.d("log", "Error " + t.getMessage());

            }
        });
    }
    private String getRealPathFromURIPath(Uri contentURI, Upload_Iimage activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {

         w = inImage.getWidth();
         h = inImage.getHeight();


if(w>=800 && h>=1200 ) {
  h = h / 2;
   w = w / 2;
}


        bitmap= Bitmap.createScaledBitmap(inImage, w, h, true);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        //Bitmap bm= getResizedBitmap(inImage,250,300);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),inImage, "Title", null);
        return Uri.parse(path);
    }
    public void gotoBack(View view)
    {

finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.backanimation2, R.anim.backanimation1);
    }

}
