package com.example.hifza.instantmet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hifza.instantmet.adapter.Random_Profile_Adapter;
import com.example.hifza.instantmet.json.ProfileJsonParsing;
import com.example.hifza.instantmet.login.Session;
import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.Dataarray;
import com.example.hifza.instantmet.model.Pojo;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fareeha on 1/4/2018.
 */

public class FansActivity extends Activity{

    Button logout;
    Session session_obj;
    String user_id;
    String id,api_token,fans_url,user_profile_url;
    RecyclerView recyclerView;
    Random_Profile_Adapter adapter;
   RelativeLayout back_img;
    TextView data;
    int val;
   // int[] mResIds = new int[]{R.mipmap.selfee, R.mipmap.selfee, R.mipmap.selfee, R.mipmap.selfee, R.mipmap.selfee, R.mipmap.selfee,R.mipmap.selfee, R.mipmap.selfee, R.mipmap.selfee, R.mipmap.selfee, R.mipmap.selfee, R.mipmap.selfee};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.fans_layout);
        back_img=findViewById(R.id.back_button);
        data=findViewById(R.id.activity_data);
        logout=findViewById(R.id.logout_btn);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

if(getIntent().getStringExtra("hint")!=null) {
String hint=getIntent().getStringExtra("hint");

    if (hint.equals("myProfile_fans")) {

        session_obj = new Session(FansActivity.this);
        id = session_obj.getUserId();
        api_token = session_obj.getApiToken();
        fans_url = "api/v1/whofollowme/" + id + "?api_token=" + api_token;
        val=3;
        getFans();
        data.setText("Fans");



    }

    else if (hint.equals("user_fans")) {
        Toast.makeText(this,"fans of users",Toast.LENGTH_SHORT).show();
        session_obj = new Session(FansActivity.this);

        api_token = session_obj.getApiToken();
        user_id = getIntent().getStringExtra("user_id");
        fans_url = "api/v1/whofollowme/" + user_id + "?api_token=" + api_token;
val=3;
        getFans();
        data.setText("Fans");


    }
    else if (hint.equals("my_followers")) {
        session_obj = new Session(FansActivity.this);
        id = session_obj.getUserId();
        api_token = session_obj.getApiToken();
        fans_url = "api/v1/following/" + id + "?api_token=" + api_token;
val=5;
        getFans();
        data.setText("Followers");


    }
    else if (hint.equals("user_followers")) {
        session_obj = new Session(FansActivity.this);

        api_token = session_obj.getApiToken();
        user_id = getIntent().getStringExtra("user_id");
        fans_url = "api/v1/following/" +user_id+ "?api_token=" + api_token;
val=5;
        getFans();
        data.setText("Followers");

    }




    }


        recyclerView.  setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (AccessToken.getCurrentAccessToken() == null) {
                    Intent intent=new Intent(FansActivity.this,MainActivity.class);
                    startActivity(intent);
                    // already logged out
                }*/
                new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {



                        LoginManager.getInstance().logOut();
                        Session.getInstance(FansActivity.this).logout();
                        finish();
                        startActivity(new Intent(FansActivity.this, MainActivity.class));


                    }
                }).executeAsync();
               /* Intent intent=new Intent(Detail_Activity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/





            }
        });
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           backTo();
            }
        });
            }
public void getFans()
{
    String url = "http://selfie.todayworlds.com/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

    Call<List<Data>> call = service.getFansDetail(fans_url);

    call.enqueue(new Callback<List<Data>>() {
        @Override
        public void onResponse(Call<List<Data>>call, Response<List<Data>> response) {
            try {


                ArrayList<Data> data= (ArrayList<Data>) response.body();

          adapter=new Random_Profile_Adapter(FansActivity.this,R.layout.fans_layout_item,data,val);
              recyclerView.setAdapter(adapter);
                //Toast.makeText(FansActivity.this,data.toString(),Toast.LENGTH_SHORT).show();
            }catch (Exception e) {
                Log.d("onResponse", e.toString());
                e.printStackTrace();
            }

        }
        @Override
        public void onFailure(Call<List<Data>> call, Throwable t) {
            Log.d("onFailure", t.toString());
            Toast.makeText(FansActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
        }
    });

}

    public void backTo()
    {
        if(user_id==null) {
            finish();
            Intent intent= new Intent(FansActivity.this,ProfileActivity.class);
            startActivity(intent);

        }
        else
        {


            finish();
            Intent intent= new Intent(FansActivity.this,User_profile.class);
            intent.putExtra("id",user_id);
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(user_id==null) {
            finish();
        Intent intent= new Intent(FansActivity.this,ProfileActivity.class);
        startActivity(intent);

        }
        else
        {


            finish();
            Intent intent= new Intent(FansActivity.this,User_profile.class);
            intent.putExtra("id",user_id);
            startActivity(intent);
        }
       //overridePendingTransition(R.anim.backanimation2, R.anim.backanimation1);
    }
}
