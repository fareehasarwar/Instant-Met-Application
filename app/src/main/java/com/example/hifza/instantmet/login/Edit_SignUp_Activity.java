package com.example.hifza.instantmet.login;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.hifza.instantmet.MainActivity;
import com.example.hifza.instantmet.R;
import com.example.hifza.instantmet.json.PostEditedProfileData;
import com.example.hifza.instantmet.json.PostLoginData;
import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.RootObject;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

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
 * Created by Fareeha on 2/27/2018.
 */

public class Edit_SignUp_Activity extends AppCompatActivity {
    CircleImageView profilePictureView;
    EditText name_tv,email_tv,gender_tv,dob_tv,password_tv,status_tv,country_tv;
    Session session;

    String fromuser_txt,name_txt,email_txt,birthday_txt,gender_txt,password_txt,country_txt,status_txt,api_token_txt,id_txt,url;
    String fromuser,name,email,birthday,gender,password,country,api_token,id,img_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_signup);
        session=new Session(Edit_SignUp_Activity.this);

        name_tv = findViewById(R.id.edt_name);
        email_tv = findViewById(R.id.edt_email);
        password_tv = findViewById(R.id.edt_password);
        gender_tv = findViewById(R.id.edt_gender);
        status_tv=findViewById(R.id.edt_status);
        country_tv=findViewById(R.id.edt_country);
        dob_tv = findViewById(R.id.edt_age);
        gender_tv .setInputType(InputType.TYPE_NULL);

        gender_tv.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(Edit_SignUp_Activity.this,gender_tv);
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
                                Edit_SignUp_Activity.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method

         name_txt=session.getName();
         email_txt=session.getEmail();
         password_txt=session.getPassword();
         gender_txt=session.getGender();
         birthday_txt=session.getAge();
         country_txt=session.getCountry();
         id_txt=session.getUserId();
         api_token_txt=session.getApiToken();
         url=session.getDp();
         // assign value
        name_tv.setText(name_txt);
        email_tv.setText(email_txt);
        password_tv.setText(password_txt);
        gender_tv.setText(gender_txt);
        dob_tv.setText(birthday_txt);
        country_tv.setText(country_txt);


    }
    // post Updated Profile
    public void UploadSignUpData(View view) {


        if (name_tv.getText().toString().trim().length() > 0 && email_tv.getText().toString().trim().length() > 0 && password_tv.getText().toString().trim().length() > 0&& gender_tv.getText().toString().trim().length() > 0&& dob_tv.getText().toString().trim().length() > 0&& country_tv.getText().toString().trim().length()>0&& status_tv.getText().toString().trim().length() > 0) {



                name = name_tv.getText().toString();
                email = email_tv.getText().toString();
                gender = gender_tv.getText().toString();
                password = password_tv.getText().toString();
                birthday = dob_tv.getText().toString();
                country=country_tv.getText().toString();
                status_txt=status_tv.getText().toString();



                    sendEditedRegisterData(id_txt,api_token_txt,name_txt, email_txt, password_txt, "Pakistan", birthday_txt, gender_txt,status_txt);


        }
        else
        {
            Toast.makeText(Edit_SignUp_Activity.this,"one of your field is empty",Toast.LENGTH_SHORT).show();
        }
    }








    public void sendEditedRegisterData(String user_id,String apitoken,String name, String email, String password, String country, String dob,  String gender, String desc) {


        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);


        RequestBody id_req = RequestBody.create(MediaType.parse("text/plain"),user_id);
        RequestBody token_req = RequestBody.create(MediaType.parse("text/plain"), apitoken);
        RequestBody name_req = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody email_req = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody password_req = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody country_req = RequestBody.create(MediaType.parse("text/plain"),country);
        RequestBody status_req = RequestBody.create(MediaType.parse("text/plain"),desc);
        RequestBody dob_req = RequestBody.create(MediaType.parse("text/plain"), dob);
        RequestBody gender_req = RequestBody.create(MediaType.parse("text/plain"), gender);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://selfie.todaylike.club/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostEditedProfileData uploadImage = retrofit.create(PostEditedProfileData.class);
        Call<RootObject> fileUpload = uploadImage.uploadEditedDetail(id_req,token_req,name_req, email_req,password_req,country_req, dob_req,  gender_req,status_req);
        fileUpload.enqueue(new Callback<RootObject>() {


            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {

                RootObject studentdata=response.body();
                ArrayList<Data> data= (ArrayList<Data>) studentdata.getDataarray();

                Session.getInstance(getApplicationContext()).userLoginDetail(data.get(0).getApi_token(),data.get(0).getId(), data.get(0).getName(),data.get(0).getEmail(),data.get(0).getPassword(),data.get(0).getAge(), data.get(0).getDp(),data.get(0).getGender(), data.get(0).getCountry());


                Toast.makeText(Edit_SignUp_Activity.this, "Success " + studentdata.getData().getId(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Edit_SignUp_Activity.this, MainActivity.class);

                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Edit_SignUp_Activity.this, R.anim.animation1, R.anim.animation2).toBundle();
                startActivity(intent, bndlanimation);

            }
            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                Toast.makeText(Edit_SignUp_Activity.this,t.toString(),Toast.LENGTH_LONG).show();
                Log.d("log", "Error " + t.getMessage());

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void backToActivity(View view)
    {
        Intent intent = new Intent(Edit_SignUp_Activity.this, MainActivity.class);

        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Edit_SignUp_Activity.this, R.anim.animation1, R.anim.animation2).toBundle();
        startActivity(intent, bndlanimation);

    }


}
