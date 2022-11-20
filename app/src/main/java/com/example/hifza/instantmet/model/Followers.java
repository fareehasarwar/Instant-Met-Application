package com.example.hifza.instantmet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fareeha on 1/20/2018.
 */

public  class Followers {
    @SerializedName("followers")
    @Expose
    private String followers;

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }
}
