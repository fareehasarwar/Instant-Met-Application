package com.example.hifza.instantmet.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hifza.instantmet.FansActivity;
import com.example.hifza.instantmet.MainActivity;
import com.example.hifza.instantmet.R;
import com.example.hifza.instantmet.json.GetJsonData;
import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.RootObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fareeha on 1/9/2018.
 */

public class Login_Activity extends AppCompatActivity {
    EditText email_edt, password_edt;
    boolean check=false;
    String email_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        email_edt = findViewById(R.id.edt_email);
        password_edt = findViewById(R.id.edt_password);
    }
    public void loginWithEmail(View view) {

        getJsonArray();

    }
        public void getJsonArray()
        {

        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetJsonData service = retrofit.create(GetJsonData.class);

            Call<RootObject> call = service.getAllDetails();

        call.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                try {
                    RootObject data=response.body();

                    ArrayList<Data> StudentData = (ArrayList<Data>)data.getDataarray();

                    //Toast.makeText(MainActivity.this,StudentData.toString(),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Login.this,StudentData.toString(),Toast.LENGTH_SHORT).show();
                    email_txt=email_edt.getText().toString();

                    for (int i = 0; i < StudentData.size(); i++) {


                        if (StudentData.get(i).getEmail().equals(email_txt)) {
                            check = true;
                            Toast.makeText(Login_Activity.this,"successfully",Toast.LENGTH_SHORT).show();

                            Session.getInstance(getApplicationContext()).userLoginDetail(StudentData.get(i).getApi_token(),StudentData.get(i).getId(),StudentData.get(i).getName(), StudentData.get(i).getEmail(), StudentData.get(i).getPassword(), StudentData.get(i).getAge(), StudentData.get(i).getDp(), StudentData.get(i).getGender(), StudentData.get(i).getCountry());
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }
                    }

                    if(check==false)
                    {
                        Toast.makeText(Login_Activity.this,"wrong credentials",Toast.LENGTH_SHORT).show();
                    }







                }catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {

            }


        });


    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Login_Activity.this,Registration_Activity.class);
        startActivity(intent);
    }
}
