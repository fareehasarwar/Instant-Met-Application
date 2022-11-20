package com.example.hifza.instantmet.json;

import com.example.hifza.instantmet.model.RootObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Fareeha on 2/28/2018.
 */

public interface PostEditedProfileData
    {
        @Multipart
        @POST("api/v1/updateuser")
        Call<RootObject> uploadEditedDetail( @Part("uid") RequestBody uid,
                                             @Part("api_token") RequestBody api_token,
                                             @Part("name") RequestBody name,
                                             @Part("email") RequestBody email,
                                              @Part("password") RequestBody password,
                                              @Part("country") RequestBody country,
                                              @Part("age") RequestBody dob,
                                              @Part("gender") RequestBody gender,

                                              @Part("ustatus") RequestBody ustatus);

    }

