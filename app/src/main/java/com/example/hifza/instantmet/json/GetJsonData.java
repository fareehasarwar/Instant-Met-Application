package com.example.hifza.instantmet.json;

import com.example.hifza.instantmet.model.Data;
import com.example.hifza.instantmet.model.RootObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Fareeha on 1/9/2018.
 */

public interface GetJsonData {
    @GET("/api/v1/users")
    Call<RootObject> getAllDetails();
}
