package com.example.hifza.instantmet.json;

import com.example.hifza.instantmet.model.RootObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Fareeha on 2/26/2018.
 */

public interface EditImageApi {
    @Multipart
    @POST("api/v1/updatepost")
    Call<RootObject> editImageDetail(@Part("api_token") RequestBody api_token,
                                       @Part("uid") RequestBody uid,
                                       @Part("id") RequestBody id,
                                       @Part("description") RequestBody description,

                                       @Part("imagefrom") RequestBody imagefrom,

                                       @Part MultipartBody.Part postimage);

}

