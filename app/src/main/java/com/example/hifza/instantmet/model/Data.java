package com.example.hifza.instantmet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Fareeha on 1/9/2018.
 */

public class Data implements Serializable {

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
    @SerializedName("pic")
    @Expose
    private String pic;


    @SerializedName("code")

    @Expose
    private String code;

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("description")
    @Expose
    private String description;



    @SerializedName("imagefrom")
    @Expose
    private String imagefrom;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("country")
    @Expose

    private String country;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("fromuser")
    @Expose
    private String fromuser;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dp")
    @Expose
    private String dp;
    @SerializedName("totallike")
    @Expose
    private String totallike;
    @SerializedName("totallikes")
    @Expose
    private String totallikes;
    @SerializedName("tlike")
    @Expose
    private String tlike;
    @SerializedName("counter")
    @Expose
    private  String counter;
    @SerializedName("tpoints")
    @Expose
    private  String tpoints;
    @SerializedName("remember_token")
    @Expose
    private String remember_token;
    @SerializedName("api_token")
    @Expose
    private  String api_token;
    @SerializedName("postimage")
    @Expose
    private  String postimage;




    @SerializedName("ustatus")
    @Expose
    private  String ustatus;
    @SerializedName("total_view")
    @Expose
    private  String total_view;



    @SerializedName("total_like")
    @Expose
    private  String total_like;


    @SerializedName("following")
    @Expose
    private  String following;
    @SerializedName("followers")
    @Expose
    private  String followers;
    @SerializedName("u_status")
    @Expose
    private  String u_status;




    public String getTlike() {
        return tlike;
    }

    public void setTlike(String tlike) {
        this.tlike = tlike;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }
    public String getTotallikes() {
        return totallikes;
    }

    public void setTotallikes(String totallikes) {
        this.totallikes = totallikes;
    }
    public String getTpoints() {
        return tpoints;
    }

    public void setTpoints(String tpoints) {
        this.tpoints = tpoints;
    }

    public String getRemember_token() {
        return remember_token;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }


    public String getUstatus() {
        return ustatus;
    }

    public void setUstatus(String ustatus) {
        this.ustatus = ustatus;
    }



    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getU_status() {
        return u_status;
    }

    public void setU_status(String u_status) {
        this.u_status = u_status;
    }

    public Data(String id, String name, String email, String password, String gender, String country, String age, String dp) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.country = country;
        this.age = age;
        this.dp= dp;
    }
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTotal_view() {
        return total_view;
    }

    public void setTotal_view(String total_view) {
        this.total_view = total_view;
    }

    public String getTotal_like() {
        return total_like;
    }

    public void setTotal_like(String total_like) {
        this.total_like = total_like;
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

    public String getTotallike() {
        return totallike;
    }

    public void setTotallike(String totallike) {
        this.totallike = totallike;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getFromuser() {
        return fromuser;
    }

    public void setFromuser(String fromuser) {
        this.fromuser = fromuser;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getUsername() { return this.username; }

    public void setUsername(String username) { this.username = username; }



    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }



    public String getCountry() { return this.country; }

    public void setCountry(String country) { this.country = country; }




    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }



    public String getPassword() { return this.password; }

    public void setPassword(String password) { this.password = password; }








    @Override
    public String toString()
    {
        return "Model [id = "+id+", username = "+name+", email = "+email+", age= "+age+", name = "+name+", gender = "+gender+", dp = "+dp+", password = "+password+", country = "+country+", uid="+uid+", created_at"+created_at+"]";
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

}
