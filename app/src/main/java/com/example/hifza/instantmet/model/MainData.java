package com.example.hifza.instantmet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fareeha on 1/29/2018.
 */

public class MainData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("dp")
    @Expose
    private String dp;
    @SerializedName("u_status")
    @Expose
    private String u_status;
    @SerializedName("api_token")
    @Expose
    private String api_token;
    @SerializedName("remember_token")
    @Expose
    private String remember_token;
    @SerializedName("totallike")
    @Expose
    private String totallike;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("postimage")
    @Expose
    private String postimage;
    @SerializedName("imagefrom")
    @Expose
    private String imagefrom;
    @SerializedName("tags")
    @Expose
    private String tags;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getU_status() {
        return u_status;
    }

    public void setU_status(String u_status) {
        this.u_status = u_status;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getRemember_token() {
        return remember_token;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }

    public String getTotallike() {
        return totallike;
    }

    public void setTotallike(String totallike) {
        this.totallike = totallike;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getImagefrom() {
        return imagefrom;
    }

    public void setImagefrom(String imagefrom) {
        this.imagefrom = imagefrom;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }







}
