package com.example.hifza.instantmet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fareeha on 1/20/2018.
 */

public class Dataarray {
    @SerializedName("user_detail")
    @Expose
    private List<Data> user_detail = null;
    @SerializedName("user_post")
    @Expose
    private List<Data> user_post = null;
    @SerializedName("following")
    @Expose
    private List<Data> following = null;
    @SerializedName("followers")
    @Expose
    private List<Data> followers = null;
    @SerializedName("post_detail")
    @Expose
    private List<Data> post_detail = null;

    public List<Data> getPost_detail() {
        return post_detail;
    }

    public void setPost_detail(List<Data> post_detail) {
        this.post_detail = post_detail;
    }



    public void setTlikes(List<Data> tlikes) {
        this.tlikes = tlikes;
    }

    @SerializedName("tlikes")
    @Expose
    private List<Data> tlikes = null;

    public List<Data> getUser_post() {
        return user_post;
    }

    public void setUser_post(List<Data> user_post) {
        this.user_post = user_post;
    }


    public List<Data> getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(List<Data> user_detail) {
        this.user_detail = user_detail;
    }

    public List<Data> getFollowing() {
        return following;
    }

    public void setFollowing(List<Data> following) {
        this.following = following;
    }

    public List<Data> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Data> followers) {
        this.followers = followers;
    }
}
