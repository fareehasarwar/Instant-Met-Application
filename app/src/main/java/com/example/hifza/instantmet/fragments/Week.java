package com.example.hifza.instantmet.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hifza.instantmet.FanAdapter;
import com.example.hifza.instantmet.R;
import com.example.hifza.instantmet.adapter.Random_Profile_Adapter;
import com.example.hifza.instantmet.json.ProfileJsonParsing;
import com.example.hifza.instantmet.login.Session;
import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.RootObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fareeha on 1/8/2018.
 */

public class Week extends Fragment {

    Random_Profile_Adapter adapter;
    Session session_obj;
    String api_token,detail_url;
    RecyclerView recyclerView;
    //int[] mResIds = new int[]{R.mipmap.women_1, R.mipmap.women_2, R.mipmap.women_3, R.mipmap.women_4, R.mipmap.women_7, R.mipmap.women_8,R.mipmap.women_12, R.mipmap.women_2, R.mipmap.women_3, R.mipmap.women_4, R.mipmap.women_7, R.mipmap.women_8};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.today_fragment_layout, container, false);


        session_obj=new Session(getContext());

        api_token=session_obj.getApiToken();
        detail_url="api/v1/leaderboards/thisweek?api_token="+api_token;

        recyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);


        recyclerView.  setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
        getDetail();


        return view;
    }
    public  void getDetail()
    {

        String url = "http://selfie.todaylike.club/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileJsonParsing service = retrofit.create(ProfileJsonParsing.class);

        Call<RootObject> call = service.getLeaderboards(detail_url);

        call.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                try {

                    RootObject object=response.body();
                    ArrayList<Data> data= (ArrayList<Data>) object.getDataarray();


                   // Toast.makeText(getContext(),data.toString(),Toast.LENGTH_SHORT).show();
                    adapter=new Random_Profile_Adapter(getContext(),R.layout.leader_layout_item,data,4);
                    recyclerView.setAdapter(adapter);



                }catch (Exception e) {
                    Log.d("onResponse", e.toString());
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                if(t instanceof SocketTimeoutException){
                    Toast.makeText(getContext(),t.toString(),Toast.LENGTH_SHORT).show();
                }}
        });

    }
}
