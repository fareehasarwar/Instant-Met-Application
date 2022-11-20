package com.example.hifza.instantmet.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hifza.instantmet.FansActivity;
import com.example.hifza.instantmet.MainActivity;
import com.example.hifza.instantmet.R;
import com.example.hifza.instantmet.json.PostJsonArray;
import com.example.hifza.instantmet.json.PostLoginData;
import com.example.hifza.instantmet.model.RootObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fareeha on 1/9/2018.
 */

public class Signup_Activity extends AppCompatActivity{
   CircleImageView profilePictureView;
    MultipartBody.Part fileToUpload;
    SharedPreferences sharedpreferences;

    EditText name_tv,email_tv,gender_tv,dob_tv,password_tv;
    String fromuser_txt,name_txt,email_txt,birthday_txt,gender_txt,password_txt,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        isStoragePermissionGranted();
        profilePictureView = findViewById(R.id.dp_img);

        name_tv = findViewById(R.id.edt_name);
        email_tv = findViewById(R.id.edt_email);
        password_tv = findViewById(R.id.edt_password);
        gender_tv = findViewById(R.id.edt_gender);

        dob_tv = findViewById(R.id.edt_age);
       gender_tv .setInputType(InputType.TYPE_NULL);
       gender_tv.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
             PopupMenu popup = new PopupMenu(Signup_Activity.this,gender_tv);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .   inflate(R.menu.drop_down, popup.getMenu());
              //  popup.getMenu().setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
              //  popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                       switch (item.getItemId()) {
                            case R.id.male:
                                gender_tv.setText("male");
                                return true;
                            case R.id.female:
                        gender_tv.setText("female");
                                return true;


                        }
                        Toast.makeText(
                                Signup_Activity.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method

      sharedpreferences = getSharedPreferences("MyPref",
                0);

        if (sharedpreferences.contains("name")) {
            name_tv.setText(sharedpreferences.getString("name", ""));
            //Toast.makeText(NextActivity.this,sharedpreferences.getString("name", ""),Toast.LENGTH_SHORT).show();
        }
        if (sharedpreferences.contains("fromuser")) {
            fromuser_txt=sharedpreferences.getString("fromuser", "");
            //Toast.makeText(NextActivity.this,sharedpreferences.getString("name", ""),Toast.LENGTH_SHORT).show();
        }

        if (sharedpreferences.contains("email")) {
            email_tv.setText(sharedpreferences.getString("email", ""));


        }
        if (sharedpreferences.contains("gender")) {
            gender_tv.setText(sharedpreferences.getString("gender", ""));
        }

        if (sharedpreferences.contains("password")) {
            password_tv.setText(sharedpreferences.getString("password", ""));
        }
        if (sharedpreferences.contains("uri")) {
            Glide.with(this).load(sharedpreferences.getString("uri", ""))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(profilePictureView);
            url = sharedpreferences.getString("uri", "");
        }

    }
    public void UploadSignUpData(View view) {


        if (name_tv.getText().toString().trim().length() > 0 && email_tv.getText().toString().trim().length() > 0 && password_tv.getText().toString().trim().length() > 0&& gender_tv.getText().toString().trim().length() > 0&& dob_tv.getText().toString().trim().length() > 0) {

if(profilePictureView.getDrawable() == null)
{
    Toast.makeText(Signup_Activity.this,"Upload Image",Toast.LENGTH_SHORT).show();
}
else {
    name_txt = name_tv.getText().toString();
    email_txt = email_tv.getText().toString();
    gender_txt = gender_tv.getText().toString();
    password_txt = password_tv.getText().toString();
    birthday_txt = dob_tv.getText().toString();


    if (fromuser_txt != null) {


        sendPost(name_txt, email_txt, password_txt, "Pakistan", birthday_txt, url, gender_txt, "social");


    } else {

        sendLoginData(name_txt, email_txt, password_txt, "Pakistan", birthday_txt, gender_txt, "kik");

    }
}
        }
        else
        {
            Toast.makeText(Signup_Activity.this,"one of your field is empty",Toast.LENGTH_SHORT).show();
        }
    }


    public void sendPost( String name, String email, String password, String country, String dob, String dp, String gender,String fromuser) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://selfie.todaylike.club/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // RxJava\
        PostJsonArray postAraayRetrofit = retrofit.create(PostJsonArray.class);
        postAraayRetrofit.savePost(  name, email,password,country, dob,  dp, gender,fromuser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RootObject>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(Signup_Activity.this, "Complete", Toast.LENGTH_SHORT).show();
                        /*SharedPreferences prefrences=getApplicationContext().getSharedPreferences("pref_state",0);
                        SharedPreferences.Editor editor = prefrences.edit();
                        editor.putBoolean("state",true);
                        editor.commit();
                        editor.apply();*/
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(Signup_Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(RootObject post) {

                        if (post.toString()!=null) {
                            Toast.makeText(Signup_Activity.this,post.toString(),Toast.LENGTH_SHORT).show();
                            RootObject studentdata=post;
                            Session.getInstance(getApplicationContext()).userLoginDetail(studentdata.getData().getApi_token(),studentdata.getData().getId(), studentdata.getData().getName(),studentdata.getData().getEmail(), studentdata.getData().getPassword(), studentdata.getData().getAge(), studentdata.getData().getDp(), studentdata.getData().getGender(), studentdata.getData().getCountry());
                           // Toast.makeText(Signup_Activity.this,studentdata.getData().getId(),Toast.LENGTH_SHORT).show();
                          //  Toast.makeText(Signup_Activity.this,studentdata.getData().getApi_token(),Toast.LENGTH_SHORT).show();
                            //starting profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }


                    }
                });

    }

    public void sendLoginData(String name, String email, String password, String country, String dob,  String gender,String fromuser) {
        Bitmap bitmap = ((BitmapDrawable)profilePictureView.getDrawable()).getBitmap();

        Uri uri_img=getImageUri(this,bitmap);
        String filePath = getRealPathFromURIPath(uri_img,Signup_Activity.this);
        File file = new File(filePath);

        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        fileToUpload = MultipartBody.Part.createFormData("imagefile", file.getName(), mFile);
        RequestBody name_req = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody email_req = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody password_req = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody country_req = RequestBody.create(MediaType.parse("text/plain"),country);
        RequestBody dob_req = RequestBody.create(MediaType.parse("text/plain"), dob);
        RequestBody gender_req = RequestBody.create(MediaType.parse("text/plain"), gender);
        RequestBody fromuser_req = RequestBody.create(MediaType.parse("text/plain"), fromuser);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://selfie.todaylike.club/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostLoginData uploadImage = retrofit.create(PostLoginData.class);
        Call<RootObject> fileUpload = uploadImage.uploadLoginDetail(name_req, email_req,password_req,country_req, dob_req,  gender_req,fromuser_req,fileToUpload);
        fileUpload.enqueue(new Callback<RootObject>() {


            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {

                RootObject studentdata=response.body();

                Session.getInstance(getApplicationContext()).userLoginDetail(studentdata.getData().getApi_token(),studentdata.getData().getId(), studentdata.getData().getName(),studentdata.getData().getEmail(), studentdata.getData().getPassword(), studentdata.getData().getAge(), studentdata.getData().getDp(), studentdata.getData().getGender(), studentdata.getData().getCountry());


                Toast.makeText(Signup_Activity.this, "Success " + studentdata.getData().getId(), Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();


            }
            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                Toast.makeText(Signup_Activity.this,t.toString(),Toast.LENGTH_LONG).show();
                Log.d("log", "Error " + t.getMessage());

            }
        });
    }



    public void uploadImage(View view)
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
                profilePictureView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Signup_Activity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }

    }
    private String getRealPathFromURIPath(Uri contentURI, Signup_Activity activity) {
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
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 70, bytes);
        //Bitmap bm= getResizedBitmap(inImage,250,300);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),inImage, "Title", null);
        return Uri.parse(path);
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)  {
                Log.v("tag", "Permission is granted");

                return true;
            } else {

                Log.v("tag", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("tag", "Permission is granted");
            return true;
        }


    }
   public void backToActivity(View view)
    {
        Intent intent=new Intent(Signup_Activity.this,Registration_Activity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Signup_Activity.this,Registration_Activity.class);
        startActivity(intent);
    }
}
