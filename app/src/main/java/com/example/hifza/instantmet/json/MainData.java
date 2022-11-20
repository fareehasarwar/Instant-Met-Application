package com.example.hifza.instantmet.json;

import com.example.hifza.instantmet.model.Root;
import com.example.hifza.instantmet.model.RootObject;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Fareeha on 1/29/2018.
 */

public interface MainData {
    @GET("/api/v1/home")
        Call<Root> getMainData();


}
