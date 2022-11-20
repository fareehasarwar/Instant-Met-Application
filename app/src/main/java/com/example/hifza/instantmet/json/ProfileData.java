package com.example.hifza.instantmet.json;

import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.Root;
import com.example.hifza.instantmet.model.RootObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Fareeha on 1/14/2018.
 */

public interface ProfileData {
    @GET("api/v1/home")
    Call<RootObject> getProfileData();
    @GET("api/v1/ads")
    Call<RootObject> getAdsData();
    @GET("api/v1/users")
    Call<RootObject> getUserData();
}
