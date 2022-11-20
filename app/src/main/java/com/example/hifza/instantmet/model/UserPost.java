package com.example.hifza.instantmet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fareeha on 1/20/2018.
 */

public class UserPost {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("uid")
    @Expose
    private String uid;
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
    private Object tags;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public Object getTags() {
        return tags;
    }

    public void setTags(Object tags) {
        this.tags = tags;
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
}
