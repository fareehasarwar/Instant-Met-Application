package com.example.hifza.instantmet.json;

import com.example.hifza.instantmet.model.RootObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Fareeha on 1/9/2018.
 */

public interface PostJsonArray {
    @POST("api/v1/register")
    @FormUrlEncoded
    Observable<RootObject> savePost(

            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("country") String country,
            @Field("age") String age,
            @Field("dp") String dp,
            @Field("gender") String gender,
            @Field("fromuser") String fromuser);
}
