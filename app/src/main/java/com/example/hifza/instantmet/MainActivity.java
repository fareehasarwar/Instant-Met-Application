package com.example.hifza.instantmet;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hifza.instantmet.adapter.Main_Profile_Top_Adapter;
import com.example.hifza.instantmet.adapter.Random_Profile_Adapter;
import com.example.hifza.instantmet.json.MainData;
import com.example.hifza.instantmet.json.ProfileData;
import com.example.hifza.instantmet.json.ProfileJsonParsing;
import com.example.hifza.instantmet.login.Edit_SignUp_Activity;
import com.example.hifza.instantmet.login.Login_Activity;
import com.example.hifza.instantmet.login.Registration_Activity;
import com.example.hifza.instantmet.login.Session;
import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.Dataarray;
import com.example.hifza.instantmet.model.Pojo;
import com.example.hifza.instantmet.model.Root;
import com.example.hifza.instantmet.model.RootObject;
import com.example.hifza.instantmet.uploadselfie.CustomGallery_Activity;
import com.example.hifza.instantmet.uploadselfie.Upload_Iimage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.hifza.instantmet.uploadselfie.Upload_Iimage.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView mRecyclerView,mrecyclerview;
    Context context;
    Main_Profile_Top_Adapter adapter;
    Random_Profile_Adapter madapter;

    String profile;
    String name,dp,email;

    Boolean login;
    TextView status_v,name_v;
    CircleImageView drawer_img;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    boolean wifi_state;
    Session session;
    LinearLayout linear,upper,home_linear,popular_linear,latest_linear;
    DrawerLayout drawer;
    int cache_size=10*1024*1024;// 10 Mi8
    ArrayList<Data> data,ad_data,data_profile;
  ImageView Ad_Img,edit_image;
  String ad_ul;
  TextView home_tv,popular_tv,popula_linear,latest,home_tv_visible,popular_tv_visible,latest_visible;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         initView();
         getJsonArrayData();
         getProfileData();
         getJsonAdsData();
         linear=(LinearLayout)findViewById(R.id.upper_menu_visible);
         popular_linear=(LinearLayout)findViewById(R.id.popular_linea);
         latest_linear=(LinearLayout)findViewById(R.id.latest_linear);
         ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(MainActivity.this));
         upper=(LinearLayout)findViewById(R.id.upper_menu);
         session = new Session(MainActivity.this);
         home_linear=(LinearLayout)findViewById(R.id.homeee);
         home_tv=findViewById(R.id.home);
         popular_tv=findViewById(R.id.popular);
         latest=findViewById(R.id.latest);
         home_tv_visible=findViewById(R.id.home_visible);
         popular_tv_visible=findViewById(R.id.popular_visisble);
         latest_visible=findViewById(R.id.latest_visisble);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         home_linear.setVisibility(View.VISIBLE);
         popular_tv.setVisibility(View.VISIBLE);
         latest.setVisibility(View.VISIBLE);
        /*if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {
            // do your stuff..
        }*/
     //   setSupportActionBar(toolbar);
         wifi_state=checkWifiState();




// set drawer


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        toggle.setHomeAsUpIndicator(R.drawable.drawer);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        drawer_img=headerView.findViewById(R.id.imageView);
        edit_image=headerView.findViewById(R.id.imageView4);
        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Edit_SignUp_Activity.class);

                startActivity(i);
            }
        });
        name_v=headerView.findViewById(R.id.textView2);

         Ad_Img=navigationView.findViewById(R.id.ad_img);
       // status_v=headerView.findViewById(R.id.textView);
         toolbar.setNavigationIcon(R.drawable.drawer_icon);
        login=session.isLoggedIn();
        if(login==true) {
name=session.getName();
dp=session.getDp();
email=session.getEmail();
name_v.setText(name);
//status_v.setText(email);
Picasso.with(this).load(dp).into(drawer_img);
drawer_img.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
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




    }
public void uploadSelfie(View view)
{

    login = session.isLoggedIn();
    if(login==true) {
        startActivity(new Intent(MainActivity.this, CustomGallery_Activity.class));
        //Intent intent = new Intent(MainActivity.this, Upload_Iimage.class);
       // startActivity(intent);
    }
    else
    {
        Intent intent = new Intent(MainActivity.this, Registration_Activity.class);
        startActivity(intent);
    }
}
    private void initView() {


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mrecyclerview=(RecyclerView)findViewById(R.id.recycler_vie);
       // madapter=new Random_Profile_Adapter(MainActivity.this,R.layout.item_post,);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mrecyclerview.setLayoutManager(linearLayoutManager);
        mrecyclerview.setAdapter(madapter);



    }


    @Override
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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            drawer.setSelected(true);

            Intent leader= new Intent(MainActivity.this,MainActivity.class);


            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
            startActivity(leader,bndlanimation);

            }
            if(id==R.id.leader_board)
            {
                login = session.isLoggedIn();
                if(login==true) {
                Intent leader= new Intent(MainActivity.this,LeaderActivity.class);


                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
                startActivity(leader,bndlanimation);
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this,Registration_Activity.class);
                    startActivity(intent);
                }

            }

        if (id == R.id.nav_camera) {
            login = session.isLoggedIn();
            if(login==true) {
                if(data!=null) {
                    startActivity(new Intent(MainActivity.this, CustomGallery_Activity.class));
                    //Intent intent = new Intent(MainActivity.this, Upload_Iimage.class);
                    // startActivity(intent);
                }
            }
            else
            {
                Intent intent = new Intent(MainActivity.this,Registration_Activity.class);
                startActivity(intent);
            }
            // Handle the camera action
        }
        else if(id==R.id.nav_games)
        {
            login = session.isLoggedIn();
            if(login==true) {
                if(data!=null) {
                    Intent leader = new Intent(MainActivity.this, Encounter.class);

                    leader.putExtra("data", data);
                    leader.putExtra("data1", ad_data);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1, R.anim.animation2).toBundle();
                    startActivity(leader, bndlanimation);
                }
            }
            else
            {
                Intent leader= new Intent(MainActivity.this,Registration_Activity.class);


                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
                startActivity(leader,bndlanimation);
            }

        }
        else if (id == R.id.nav_account) {
            login = session.isLoggedIn();
            if(login==true) {
                if(data!=null) {
                    Intent leader = new Intent(MainActivity.this, ProfileActivity.class);


                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1, R.anim.animation2).toBundle();
                    startActivity(leader, bndlanimation);
                }
            }
            else
            {
                Intent leader= new Intent(MainActivity.this,Registration_Activity.class);


                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
                startActivity(leader,bndlanimation);
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    // random profile data



    public void getJsonArrayData() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(45, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        ProfileData service = retrofit.create( ProfileData.class);

        Call<RootObject> call = service.getProfileData();

        call.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                try {

               RootObject     profile_data =  response.body();
              data= (ArrayList<Data>) profile_data.getDataarray();
                //   Toast.makeText(MainActivity.this,profile_data.getStatus().toString(),Toast.LENGTH_SHORT).show();
                   // profile=profile_data.getStatus();
                    //Toast.makeText(MainActivity.this,data.toString(),Toast.LENGTH_SHORT).show();
                      madapter=new Random_Profile_Adapter(MainActivity.this,R.layout.item_post,data,1);
                     mrecyclerview.setAdapter(madapter);
                     mrecyclerview.smoothScrollToPosition(0);

                }catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                if(t instanceof SocketTimeoutException){
                    Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
                }
            }


        });

    }
    public void getProfileData() {

        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        ProfileData service = retrofit.create( ProfileData.class);

        Call<RootObject> call = service.getUserData();

        call.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                try {

                    RootObject     profile_data =  response.body();
                    data_profile= (ArrayList<Data>) profile_data.getDataarray();
                    //   Toast.makeText(MainActivity.this,profile_data.getStatus().toString(),Toast.LENGTH_SHORT).show();
                    // profile=profile_data.getStatus();
                    //Toast.makeText(MainActivity.this,data.toString(),Toast.LENGTH_SHORT).show();

                    adapter=new Main_Profile_Top_Adapter(MainActivity.this,R.layout.item_gallery,data_profile,1);
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.smoothScrollToPosition(0);
                }catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                if(t instanceof SocketTimeoutException){
                    Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
                }
            }


        });

    }
    public void getJsonAdsData() {

        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        ProfileData service = retrofit.create( ProfileData.class);

        Call<RootObject> call = service.getAdsData();

        call.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                try {

                    RootObject     profile_data =  response.body();
                  ad_data= (ArrayList<Data>) profile_data.getDataarray();
                    Picasso.with(MainActivity.this).load(ad_data.get(0).getPic()).into(Ad_Img);
                    ad_ul=ad_data.get(0).getCode();
                    // profile=profile_data.getStatus();
                    //Toast.makeText(MainActivity.this,data.toString(),Toast.LENGTH_SHORT).show();

                }catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                if(t instanceof SocketTimeoutException){
                    Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
                }
            }


        });

    }


    private void showDirectionsDialog() {
//        MyLog.i(TAG, "***************number " + brandLocation);
        final Dialog pDialog = new Dialog(this, R.style.Dialog);
        View layout = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.wifi_popupscreen, null);
        final TextView show_state = (TextView) layout.findViewById(R.id.show_state);
        show_state.setVisibility(View.VISIBLE);
        final Button retry_btn = (Button) layout
                .findViewById(R.id.retry_btn);
        pDialog.addContentView(layout,
                new ViewGroup.LayoutParams((int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 300, getResources()
                                .getDisplayMetrics()),
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        pDialog.getWindow().setGravity(Gravity.CENTER);
        pDialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDialog.setCancelable(false);
        pDialog.show();
        final boolean state2;

        retry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifi_state = checkWifiState();

                getJsonArrayData();

                pDialog.dismiss();


            }
        });
    }
    public boolean checkWifiState() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetwork=connManager.getActiveNetworkInfo();
        // NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean state;
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        // if (activeNetwork.isConnected()) {
        if (isConnected) {
            state = true;
            // Do whatever
        } else {
            state = false;
            showDirectionsDialog();
        }
        return state;
    }
    public void clickPopupar(View view)
    {
        login = session.isLoggedIn();
        if(login==true) {

                linear.setVisibility(View.VISIBLE);
                home_linear.setVisibility(View.INVISIBLE);
                popular_linear.setVisibility(View.VISIBLE);
                popular_tv.setVisibility(View.INVISIBLE);
                latest_linear.setVisibility(View.INVISIBLE);

               Intent intent=new Intent(MainActivity.this, Popular_Activity.class);
           intent.putExtra("data", data);
            intent.putExtra("data1",ad_data);
            startActivity(intent);
                //Intent intent = new Intent(MainActivity.this, Upload_Iimage.class);
                // startActivity(intent);

        }
        else
        {
            Intent intent = new Intent(MainActivity.this, Registration_Activity.class);
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
      //  startActivity(new Intent(MainActivity.this, MainActivity.class));

    }
    public void clickLatest(View view)
    {
        login=session.isLoggedIn();
        if(login==true) {

                latest.setVisibility(View.INVISIBLE);
                linear.setVisibility(View.VISIBLE);
                home_linear.setVisibility(View.INVISIBLE);
                popular_linear.setVisibility(View.INVISIBLE);
                latest_linear.setVisibility(View.VISIBLE);


            Intent intent=new Intent(MainActivity.this,Latest_Activity.class);
            intent.putExtra("data", data);
            intent.putExtra("data1",ad_data);
            startActivity(intent);

        }
        else
        {
            Intent intent = new Intent(MainActivity.this, Registration_Activity.class);
            startActivity(intent);
        }
    }
    public void clickOnSearch(View view)
    {
        login=session.isLoggedIn();
        if(login==true) {
            if(data_profile!=null) {
                Intent intent = new Intent(MainActivity.this, Search.class);
                intent.putExtra("profile", data_profile);

                startActivity(intent);
            }
        }
        else {
            Intent intent = new Intent(MainActivity.this, Registration_Activity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();
        moveTaskToBack(true);
        finish();
    }

}
