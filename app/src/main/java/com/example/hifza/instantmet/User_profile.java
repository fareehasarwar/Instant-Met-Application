package com.example.hifza.instantmet;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fareeha on 1/4/2018.
 */

public class User_profile extends Activity {
  //  int[] mResIds = new int[]{R.mipmap.women_1, R.mipmap.women_2, R.mipmap.women_3, R.mipmap.women_4, R.mipmap.women_7, R.mipmap.women_8,R.mipmap.women_12, R.mipmap.women_2, R.mipmap.women_3, R.mipmap.women_4, R.mipmap.women_7, R.mipmap.women_8};
   String id,api_token, user_detail_url,user_follow_url,login_id,check_follow_url;
   Session session;
    TextView following,fan,leader_board,name_tv,status_txt;
    CircleImageView dp_img;
    ArrayList<Data> user_detail;
    ArrayList<Data> following_one;
    ArrayList<Data> followers;
    Random_Profile_Adapter adapter;
    RecyclerView mRecyclerprofile;
    TextView follow_tv,num_of_post;
    ImageView ticmark_img;
    RelativeLayout follow_btn;
    TextView status_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.fans);
        LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.follow_layout);

        mRecyclerprofile = (RecyclerView) findViewById(R.id.recycler_profile);
        follow_tv=findViewById(R.id.following_txt);
        ticmark_img=findViewById(R.id.ticmark_img);
        follow_btn=findViewById(R.id.follow_btn);
        status_txt=findViewById(R.id.text_status);
        following=findViewById(R.id.num_of_followers);
        num_of_post=findViewById(R.id.num_of_posts);
        fan=findViewById(R.id.num_of_likes);
        dp_img=findViewById(R.id.profile_img_1);
        name_tv=findViewById(R.id.name_tv);
       status_tv=findViewById(R.id.text_status);
        if( getIntent().getStringExtra("id") != null) {
            Intent intent=getIntent();
        id=intent.getStringExtra("id");
        }

        session = new Session(User_profile.this);
       api_token= session.getApiToken();
        login_id=session.getUserId();
        check_follow_url="api/v1/checkfollow/"+login_id+"/"+id+"?api_token="+api_token;
       user_detail_url="api/v1/userprofile/"+id+"?api_token="+api_token;

        getDetail();
        checkFollower();

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent leader= new Intent(User_profile.this,FansActivity.class);
                leader.putExtra("hint","user_followers");
                leader.putExtra("user_id",id);
               // Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
              startActivity(leader);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent leader= new Intent(User_profile.this,FansActivity.class);
                leader.putExtra("hint","user_fans");

                leader.putExtra("user_id",id);
              //  Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
            startActivity(leader);
            }
        });





        mRecyclerprofile.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));

        //madapter = new FanAdapter(User_profile.this,mResIds);
      //  mRecyclerprofile.setAdapter(madapter);
    }
    public  void getDetail()
    {
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(user_detail_url);

        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                try {

                   Pojo object=response.body();
                    Dataarray data=object.getDataarray();
                    ArrayList<Data> userPosts= (ArrayList<Data>) data.getUser_post();

                    user_detail= (ArrayList<Data>) data.getUser_detail();

                    following_one= (ArrayList<Data>) data.getFollowing();
                    followers= (ArrayList<Data>) data.getFollowers();
                    Picasso.with(User_profile.this).load(user_detail.get(0).getDp()).resize(80,90).into(dp_img);

                   // Toast.makeText(User_profile.this,user_detail.get(0).getDp(),Toast.LENGTH_SHORT).show();
                    adapter=new Random_Profile_Adapter(User_profile.this,R.layout.item_profile,userPosts,user_detail,6);
                    mRecyclerprofile.setAdapter(adapter);
                    num_of_post.setText(String.valueOf(userPosts.size()));
                    following.setText(following_one.get(0).getFollowing());
                    fan.setText(followers.get(0).getFollowers());
                    name_tv.setText(user_detail.get(0).getName());
                    status_txt.setText(user_detail.get(0).getU_status());
                    if(user_detail.get(0).getU_status()!=null)
                    {
                        status_tv.setVisibility(View.VISIBLE);
                        status_tv.setText(user_detail.get(0).getU_status());
                    }

                   // Toast.makeText(User_profile.this,userPosts.size(),Toast.LENGTH_SHORT).show();

                }catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(User_profile.this,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void followMe()
    {
      user_follow_url="api/v1/follow/"+login_id+"/"+id+"?api_token="+api_token;
        getFollower();
    }
    public  void backButton(View view)
    {
     onBackPressed();

    }
    public  void getFollower()
    {
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(user_follow_url);

        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                try {

                    Pojo object=response.body();
                    if(object.getResponse().toString().equals("true"))
                    {
             follow_tv.setText("Following");
             ticmark_img.setVisibility(View.VISIBLE);


                    }
                    else if(  object.getStatus().toString().equals("fail"))
                    {
                        follow_tv.setText("Following");
                        ticmark_img.setVisibility(View.VISIBLE);
                    }

                   // Toast.makeText(User_profile.this,object.getStatus().toString(),Toast.LENGTH_SHORT).show();
                        }catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(User_profile.this,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    public  void checkFollower()
    {
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(check_follow_url);

        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                try {

                    Pojo object=response.body();
                    if(object.getData().toString().equals("Yes"))
                    {
                        follow_tv.setText("Following");
                        ticmark_img.setVisibility(View.VISIBLE);


                    }
                    else
                    {
                     follow_btn.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             followMe();
                         }
                     });
                    }

                  //  Toast.makeText(User_profile.this,object.getStatus().toString(),Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(User_profile.this,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.backanimation2, R.anim.backanimation1);
    }
}
