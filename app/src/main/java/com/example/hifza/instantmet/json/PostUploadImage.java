package com.example.hifza.instantmet.json;

import com.example.hifza.instantmet.model.RootObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Fareeha on 1/15/2018.
 */

public interface PostUploadImage {
    @Multipart
    @POST("api/v1/addpost")
    Call<RootObject> uploadImageDetail( @Part("api_token") RequestBody api_token,
                                        @Part("uid") RequestBody uid,
                                       @Part("description") RequestBody description,

                                       @Part("imagefrom") RequestBody imagefrom,

                                       @Part MultipartBody.Part postimage);

}
