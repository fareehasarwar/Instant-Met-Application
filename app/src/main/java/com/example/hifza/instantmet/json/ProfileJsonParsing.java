package com.example.hifza.instantmet.json;

import android.widget.Toast;

import com.example.hifza.instantmet.FansActivity;
import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.Pojo;
import com.example.hifza.instantmet.model.RootObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Fareeha on 1/20/2018.
 */

public interface ProfileJsonParsing {
    @GET
    Call<Pojo> getUserDetail(@Url String fullURL);
    @GET
    Call<List<Data>> getFansDetail(@Url String fullURL);
    @GET
    Call<RootObject> getLeaderboards(@Url String fullURL);
    @GET
    Call<RootObject> getEncounterData(@Url String fullURL);
    @GET
    Call<RootObject> getPopularData(@Url String fullURL);


}
