package com.example.hifza.instantmet;

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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hifza.instantmet.adapter.Random_Profile_Adapter;
import com.example.hifza.instantmet.json.ProfileJsonParsing;
import com.example.hifza.instantmet.login.Registration_Activity;
import com.example.hifza.instantmet.login.Session;
import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.RootObject;
import com.example.hifza.instantmet.uploadselfie.CustomGallery_Activity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fareeha on 2/12/2018.
 */

public class Latest_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Session session;
    RecyclerView mrecyclerview;
    Random_Profile_Adapter madapter;
    Boolean login;
    ArrayList<Data> data,main_data,arrayList;
    DrawerLayout drawer;
    CircleImageView drawer_img;
    TextView name_v;
    String name,dp;
    String ad_ul;
    ImageView Ad_Img;
    LinearLayout linear,upper,home_linear,popular_linear,latest_linear;
    TextView home_tv,popular_tv,popula_linear,latest,home_tv_visible,popular_tv_visible,latest_visible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.latest_main_layout);
        session = new Session(Latest_Activity.this);
        if( getIntent().getExtras() != null)
        {
            main_data=(ArrayList<Data>)getIntent().getSerializableExtra("data");
            arrayList=(ArrayList<Data>)getIntent().getSerializableExtra("data1");

        }
        linear=(LinearLayout)findViewById(R.id.upper_menu_visible);
        upper=(LinearLayout)findViewById(R.id.upper_menu);
        popular_linear=(LinearLayout)findViewById(R.id.popular_linea);
        latest_linear=(LinearLayout)findViewById(R.id.latest_linear);
        home_linear=(LinearLayout)findViewById(R.id.homeee);
        home_tv=findViewById(R.id.home);
        popular_tv=findViewById(R.id.popular);
        latest=findViewById(R.id.latest);
        home_tv_visible=findViewById(R.id.home_visible);
        popular_tv_visible=findViewById(R.id.popular_visisble);
        latest_visible=findViewById(R.id.latest_visisble);
        mrecyclerview=(RecyclerView)findViewById(R.id.recycler_vie);


        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(Latest_Activity.this,LinearLayoutManager.VERTICAL,false);

        mrecyclerview.setLayoutManager(linearLayoutManager);
        mrecyclerview.setAdapter(madapter);

        home_tv=findViewById(R.id.home);
        popular_tv=findViewById(R.id.popular);
        latest=findViewById(R.id.latest);

        // visibility of button
        home_tv.setVisibility(View.VISIBLE);
        popular_tv.setVisibility(View.VISIBLE);
        latest_linear.setVisibility(View.VISIBLE);

        getLatestData();


        // Drawer Part
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setHomeAsUpIndicator(R.drawable.drawer);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(Latest_Activity.this);
        // Navigation Header
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        drawer_img = headerView.findViewById(R.id.imageView);
        name_v = headerView.findViewById(R.id.textView2);
        Ad_Img=navigationView.findViewById(R.id.ad_img);
        toolbar.setNavigationIcon(R.drawable.drawer_icon);
        login = session.isLoggedIn();
        if (login == true) {
            name = session.getName();
            dp = session.getDp();
            name_v.setText(name);
            Picasso.with(this).load(dp).into(drawer_img);


        }
        Picasso.with(this).load(arrayList.get(0).getPic()).into(Ad_Img);
        ad_ul=arrayList.get(0).getCode();
        drawer_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Latest_Activity.this,ProfileActivity.class));
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
    public  void getLatestData()
    {
        String  api_token=session.getApiToken();

        String popular_ul=   "api/v1/latest/?api_token="+api_token;;
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<RootObject> call = service.getPopularData(popular_ul);

        call.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                try {

                    RootObject object=response.body();
                    data= (ArrayList<Data>) object.getDataarray();
                    madapter=new Random_Profile_Adapter(Latest_Activity.this,R.layout.item_post,data,1);
                    mrecyclerview.setAdapter(madapter);
                }catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(Latest_Activity.this,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void clickPopupar(View view)
    {

        if(data!=null) {
            linear.setVisibility(View.VISIBLE);
            home_linear.setVisibility(View.INVISIBLE);
            popular_linear.setVisibility(View.VISIBLE);
            popular_tv.setVisibility(View.INVISIBLE);
            latest_linear.setVisibility(View.INVISIBLE);



            Intent intent = new Intent(Latest_Activity.this, Popular_Activity.class);
            intent.putExtra("data", main_data);
            intent.putExtra("data1", arrayList);
             startActivity(intent);
        }
    }
    public void clickHome(View view)
    {
        linear.setVisibility(View.VISIBLE);

        home_linear.setVisibility(View.VISIBLE);
        popular_linear.setVisibility(View.INVISIBLE);
        latest_linear.setVisibility(View.INVISIBLE);
        home_tv.setVisibility(View.INVISIBLE);
        startActivity(new Intent(Latest_Activity.this, MainActivity.class));

    }
    public void clickLatest(View view)
    {

        if(data!=null) {
            latest.setVisibility(View.INVISIBLE);
            linear.setVisibility(View.VISIBLE);
            home_linear.setVisibility(View.INVISIBLE);
            popular_linear.setVisibility(View.INVISIBLE);

         /*   latest_linear.setVisibility(View.VISIBLE);
            Intent intent=new Intent(Latest_Activity.this, Latest_Activity.class);
            intent.putExtra("data", main_data);
            intent.putExtra("data1",arrayList);
            startActivity(intent);*/
        }


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

            Intent leader= new Intent(Latest_Activity.this,MainActivity.class);


            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
            startActivity(leader,bndlanimation);

        }
        if(id==R.id.leader_board)
        {
            Intent leader= new Intent(Latest_Activity.this,LeaderActivity.class);


            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
            startActivity(leader,bndlanimation);

        }

        if (id == R.id.nav_camera) {
            login = session.isLoggedIn();
            if(login==true) {

                Intent intent = new Intent(Latest_Activity.this,CustomGallery_Activity.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent,bndlanimation);
            }
            else
            {
                Intent intent = new Intent(Latest_Activity.this, Registration_Activity.class);
                startActivity(intent);
            }
            // Handle the camera action
        }
        else if(id==R.id.nav_games)
        {
            login = session.isLoggedIn();
            if(login==true) {
                Intent leader= new Intent(Latest_Activity.this,Encounter.class);

                leader.putExtra("data", main_data);
                leader.putExtra("data1", arrayList);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
                startActivity(leader,bndlanimation);
            }
            else
            {
                Intent leader= new Intent(Latest_Activity.this,Registration_Activity.class);


                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
                startActivity(leader,bndlanimation);
            }

        }
        else if (id == R.id.nav_account) {
            login = session.isLoggedIn();
            if(login==true) {
                Intent leader= new Intent(Latest_Activity.this,ProfileActivity.class);


                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
                startActivity(leader,bndlanimation);
            }
            else
            {
                Intent leader= new Intent(Latest_Activity.this,Registration_Activity.class);


                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
                startActivity(leader,bndlanimation);
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void onBackPressed() {

        Intent intent = new Intent(Latest_Activity.this, MainActivity.class);

        startActivity(intent);



    }

}
