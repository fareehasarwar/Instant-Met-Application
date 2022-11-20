package com.example.hifza.instantmet.json;

import com.example.hifza.instantmet.model.RootObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Fareeha on 1/9/2018.
 */

public interface PostLoginData
{
    @Multipart
    @POST("api/v1/register")
    Call<RootObject> uploadLoginDetail(@Part("name") RequestBody name,
                                       @Part("email") RequestBody email,
                                       @Part("password") RequestBody password,
                                       @Part("country") RequestBody country,
                                       @Part("age") RequestBody dob,
                                       @Part("gender") RequestBody gender,
                                       @Part("fromuser") RequestBody fromuser,
                                       @Part MultipartBody.Part imageupload);

}