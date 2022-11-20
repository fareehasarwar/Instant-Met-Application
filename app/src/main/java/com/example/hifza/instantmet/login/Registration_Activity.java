package com.example.hifza.instantmet.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hifza.instantmet.FansActivity;
import com.example.hifza.instantmet.MainActivity;
import com.example.hifza.instantmet.R;
import com.example.hifza.instantmet.json.GetJsonData;
import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.RootObject;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fareeha on 1/9/2018.
 */

public class Registration_Activity extends AppCompatActivity {
    CallbackManager callbackManager;
    String name, email, birthday, id, gender, profilePicUrl, country;
    Boolean check = false;

   Session session;
    boolean login;
    SharedPreferences pref;
    TextView info;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.registration_layout);
        final ImageView imageView=findViewById(R.id.imageView5);
        loginButton = findViewById(R.id.login_button);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        // pref.edit().remove("shared_pref_key").commit();

        session = new Session(getApplicationContext());
        login=session.isLoggedIn();

        if(login==true)
        {
            Intent intent=new Intent(Registration_Activity.this,MainActivity.class);
            startActivity(intent);
        }
      //  info = findViewById(R.id.info);
       // loginButton = findViewById(R.id.facebook_Login);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.imageView5) {
                    loginButton.performClick();
                }
            }
        });

    }

    public void signup(View view) {
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        pref.edit().clear().commit();
        Intent intent = new Intent(Registration_Activity.this, Signup_Activity.class);
        startActivity(intent);




    }
    public void loginWith(View view) {
        Intent intent = new Intent(Registration_Activity.this,Login_Activity.class);
        startActivity(intent);
    }
    public void LoginWithFacebook(View view)
    {

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
       loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                // Application code
                                try {
                                    //  Toast.makeText(MainActivity.this,object.toString(),Toast.LENGTH_SHORT).show();
                                    id = object.getString("id");

                                    name = object.getString("name");
                                    email = object.getString("email");

                                    gender = object.getString("gender");

                                    // country = object.getJSONObject("location").getJSONObject("location").getString("country");


                                    profilePicUrl = "https://graph.facebook.com/" + id + "/picture?type=large";

                                    // info.setText("name:"+name+"\nemail:"+email+"\n gender:"+gender+"\nurl:"+profilePicUrl);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("name", name);
                                    editor.putString("password", "123456");
                                    editor.putString("gender", gender);
                                    editor.putString("uri", profilePicUrl);
                                    editor.putString("email", email);
                                    editor.putString("fromuser","social");


                                    editor.commit();
                                    editor.apply();
                                    getJsonArray();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        });

                ImageView imageView=findViewById(R.id.imageView5);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   LoginManager.getInstance().logInWithReadPermissions(Registration_Activity.this, Arrays.asList("public_profile", "user_friends"));
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();



            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText(e.toString());
                Toast.makeText(Registration_Activity.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void getHashKey()
    {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.example.hifza.instantmet", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("vivz", something);
                Toast.makeText(Registration_Activity.this,something,Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        callbackManager.onActivityResult(requestCode, resultCode, data);



    }
    public void getJsonArray() {

        String url = "http://selfie.todaylike.club";
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
RootObject object=response.body();
              ArrayList<Data>      StudentData = (ArrayList<Data>) object.getDataarray();
                    //Toast.makeText(MainActivity.this,StudentData.toString(),Toast.LENGTH_SHORT).show();


                    for (int i = 0; i < StudentData.size(); i++) {

                        if (StudentData.get(i).getEmail().equals(email)) {
                            check = true;
                            finish();
                            Session.getInstance(getApplicationContext()).userLoginDetail(StudentData.get(i).getApi_token(),StudentData.get(i).getId(), StudentData.get(i).getName(), StudentData.get(i).getEmail(), StudentData.get(i).getPassword(), StudentData.get(i).getAge(), StudentData.get(i).getDp(), StudentData.get(i).getGender(), StudentData.get(i).getCountry());
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }
                    if(check==false) {
                        finish();
                        startActivity(new Intent(getApplicationContext(),Signup_Activity.class));
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
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
