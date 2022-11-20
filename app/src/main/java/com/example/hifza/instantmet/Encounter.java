package com.example.hifza.instantmet;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hifza.instantmet.adapter.Main_Profile_Top_Adapter;
import com.example.hifza.instantmet.adapter.Random_Profile_Adapter;
import com.example.hifza.instantmet.json.ProfileData;
import com.example.hifza.instantmet.json.ProfileJsonParsing;
import com.example.hifza.instantmet.login.Registration_Activity;
import com.example.hifza.instantmet.login.Session;
import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.Pojo;
import com.example.hifza.instantmet.model.RootObject;
import com.example.hifza.instantmet.transformImage.RoundedCornersTransform;
import com.example.hifza.instantmet.uploadselfie.CustomGallery_Activity;
import com.facebook.FacebookSdk;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fareeha on 2/7/2018.
 */

public class Encounter  extends Activity implements NavigationView.OnNavigationItemSelectedListener {
    CircleImageView dp_img;
    TextView user_name;
    ImageView post_img,like_img,next_img;
int pos=0;
    //int  position;

    ArrayList<Data> data;
    Session session;
    boolean login;
    DrawerLayout drawer;
    CircleImageView drawer_img;
    TextView name_v;
    String name,dp;
    ArrayList<Data> arrayList;
     ImageView Ad_Img;
     String ad_ul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          setContentView(R.layout.encouter_main_layout);
        if( getIntent().getExtras() != null)
        {
            data=(ArrayList<Data>)getIntent().getSerializableExtra("data");
            arrayList=(ArrayList<Data>)getIntent().getSerializableExtra("data1");
            Collections.shuffle(data);
        }
        session = new Session(Encounter.this);

          dp_img = findViewById(R.id.profile_img);
          user_name = findViewById(R.id.name_of_user);
          post_img = findViewById(R.id.post_image);
          next_img = findViewById(R.id.next_img);
          like_img = findViewById(R.id.like_img);

        next_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextImage();
            }
        });
        assignData(data);checkMainPostLikes(data.get(0).getId());

          // Drawer intialize
          Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
          drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
          ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                  this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
         drawer.addDrawerListener(toggle);
          toggle.setHomeAsUpIndicator(R.drawable.drawer);
          toggle.syncState();

          NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
          navigationView.setNavigationItemSelectedListener(Encounter.this);
          // Navigation Header
          View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

          drawer_img = headerView.findViewById(R.id.imageView);
          name_v = headerView.findViewById(R.id.textView2);
          Ad_Img=navigationView.findViewById(R.id.ad_img);
          // status_v=headerView.findViewById(R.id.textView);
          login = session.isLoggedIn();
          if (login == true) {
              name = session.getName();
              dp = session.getDp();
              name_v.setText(name);
              Picasso.with(this).load(dp).into(drawer_img);


          }
        Picasso.with(Encounter.this).load(arrayList.get(0).getPic()).into(Ad_Img);
        ad_ul=arrayList.get(0).getCode();
        drawer_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Encounter.this,ProfileActivity.class));
            }
        });
        Ad_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(ad_ul));
                startActivity(i);
            }
        });
      }
    public void getJsonArray() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
        Session session=new Session(Encounter.this);
        final String login_id=session.getUserId();

        final String   api_token=session.getApiToken();
        String url = "http://selfie.todayworlds.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
String encounter_url="api/v1/encounter/?api_token="+api_token;
        ProfileJsonParsing service = retrofit.create( ProfileJsonParsing.class);

        Call<RootObject> call = service.getEncounterData(encounter_url);

        call.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                try {

                    RootObject     profile_data =  response.body();
               data= (ArrayList<Data>) profile_data.getDatarray();
                    // profile=profile_data.getStatus();
                   // Toast.makeText(Encounter.this,data.toString(),Toast.LENGTH_SHORT).show();
                    assignData(data);
                    checkMainPostLikes(data.get(0).getId());
                }catch (Exception e) {
                   // Toast.makeText(Encounter.this,e.toString(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    Toast.makeText(Encounter.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                if(t instanceof SocketTimeoutException){
                    Toast.makeText(Encounter.this,t.toString(),Toast.LENGTH_SHORT).show();
                }
            }


        });

    }
    public void assignData(List<Data> obj)
    {
        Picasso.with(this).load(obj.get(0).getDp()).into(dp_img);
        Picasso.with(this).load(obj.get(0).getPostimage()).transform(new RoundedCornersTransform(21,1)).into(post_img);
        user_name.setText(obj.get(0).getName());
        dp_img.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                login = session.isLoggedIn();
                if (login == true) {
                    String id = data.get(pos).getUid();
                    Session session = new Session(Encounter.this);
                    final String login_id = session.getUserId();
                    if (login_id.equals(id)) {
                        Intent intent = new Intent(Encounter.this, ProfileActivity.class);
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Encounter.this, R.anim.animation1, R.anim.animation2).toBundle();
                        Encounter.this.startActivity(intent, bndlanimation);
                    } else {
                        Intent intent = new Intent(Encounter.this, User_profile.class);
                        intent.putExtra("id", data.get(pos).getUid());
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Encounter.this, R.anim.animation1, R.anim.animation2).toBundle();
                        Encounter.this.startActivity(intent, bndlanimation);
                    }
                } else {
                    Intent intent = new Intent(Encounter.this, Registration_Activity.class);

                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Encounter.this, R.anim.animation1, R.anim.animation2).toBundle();
                    Encounter.this.startActivity(intent, bndlanimation);
                }
            }
        });
    }
    public void nextImage()
    {
        like_img.setImageResource(R.drawable.tic);

      final int   position = pos + 1;
        user_name.setText(data.get(position).getName());
        Picasso.with(this).load(data.get(position).getDp()).into(dp_img);
        Picasso.with(this).load(data.get(position).getPostimage()).transform(new RoundedCornersTransform(21,1)).into(post_img);
        checkMainPostLikes(data.get(position).getId());

        if (!(position >= data.size()- 1)) {
           pos += 1;

        } else {
          pos = -1;
        }
        dp_img.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                login = session.isLoggedIn();
                if (login == true) {
                    String id = data.get(position).getUid();
                    Session session = new Session(Encounter.this);
                    final String login_id = session.getUserId();
                    if (login_id.equals(id)) {
                        Intent intent = new Intent(Encounter.this, ProfileActivity.class);
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Encounter.this, R.anim.animation1, R.anim.animation2).toBundle();
                        Encounter.this.startActivity(intent, bndlanimation);
                    } else {
                        Intent intent = new Intent(Encounter.this, User_profile.class);
                        intent.putExtra("id", data.get(position).getUid());
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Encounter.this, R.anim.animation1, R.anim.animation2).toBundle();
                        Encounter.this.startActivity(intent, bndlanimation);
                    }
                } else {
                    Intent intent = new Intent(Encounter.this, Registration_Activity.class);

                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Encounter.this, R.anim.animation1, R.anim.animation2).toBundle();
                    Encounter.this.startActivity(intent, bndlanimation);
                }
            }
        });

    }
    public  void checkMainPostLikes( String user_id)
    {

        Session session=new Session(Encounter.this);
        final String login_id=session.getUserId();
        final String id=user_id;
        final String   api_token=session.getApiToken();
        String check_follow_url="api/v1/checklike/"+login_id+"/"+id+"?api_token="+api_token;
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(check_follow_url);

        call.enqueue(new retrofit2.Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                try {
                    // RelativeLayout like_btn=view.findViewById(R.id.like_btn);
                    Pojo object=response.body();
                    // Toast.makeText(context,object.getData().toString(),Toast.LENGTH_SHORT).show();
                    if(object.getData().toString().equals("Yes"))
                    {

                        // int count= Integer.valueOf(like_tv.getText().toString());
                        //  like_tv.setText(String.valueOf(count++));

                        like_img.setImageResource(R.drawable.like_icon_change);
                        // like_tv.setText(total_likes);


                    }
                    else
                    {

                        like_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String   user_like_url="api/v1/like/"+login_id+"/"+id+"?api_token="+api_token;
                                //  Toast.makeText(context,"button clicked",Toast.LENGTH_SHORT).show();
                                getMainPostLikes( user_like_url);
                            }
                        });
                    }


                }catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                    Toast.makeText(Encounter.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                if(t instanceof SocketTimeoutException){
                    Toast.makeText(Encounter.this,t.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public  void getMainPostLikes(String follow_url)
    {

        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<Pojo> call = service.getUserDetail(follow_url);

        call.enqueue(new retrofit2.Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                try {

                    Pojo object=response.body();
                    if(object.getResponse().toString().equals("true"))
                    {



                        like_img.setImageResource(R.drawable.like_icon_change);



                    }


                }catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                    Toast.makeText(Encounter.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.d("onFailure", t.toString());
                if(t instanceof SocketTimeoutException){
                    Toast.makeText(Encounter.this,t.toString(),Toast.LENGTH_SHORT).show();
                }
                //   Toast.makeText(context,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            drawer.setSelected(true);

            Intent leader= new Intent(Encounter.this,MainActivity.class);


            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
            startActivity(leader,bndlanimation);

        }
        if(id==R.id.leader_board)
        {
            Intent leader= new Intent(Encounter.this,LeaderActivity.class);


            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
            startActivity(leader,bndlanimation);

        }

        if (id == R.id.nav_camera) {
            login = session.isLoggedIn();
            if(login==true) {
                startActivity(new Intent(Encounter.this, CustomGallery_Activity.class));
                //Intent intent = new Intent(MainActivity.this, Upload_Iimage.class);
                // startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(Encounter.this, ProfileActivity.class);
                startActivity(intent);
            }
            // Handle the camera action
        }
        else if(id==R.id.nav_games)
        {
            login = session.isLoggedIn();
            if(login==true) {
                Intent leader= new Intent(Encounter.this,Encounter.class);

                leader.putExtra("data", data);
                leader.putExtra("data1", arrayList);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
                startActivity(leader,bndlanimation);
            }
            else
            {
                Intent leader= new Intent(Encounter.this,Registration_Activity.class);


                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
                startActivity(leader,bndlanimation);
            }

        }
        else if (id == R.id.nav_account) {
            login = session.isLoggedIn();
            if(login==true) {
                Intent leader= new Intent(Encounter.this,ProfileActivity.class);


                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
                startActivity(leader,bndlanimation);
            }
            else
            {
                Intent leader= new Intent(Encounter.this,Registration_Activity.class);


                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
                startActivity(leader,bndlanimation);
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}
