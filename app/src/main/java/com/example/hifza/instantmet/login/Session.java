package com.example.hifza.instantmet.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hifza.instantmet.model.Data;

/**
 * Created by Fareeha on 1/9/2018.
 */

public class Session
{
    private static Session mInstance;
    private static Context mCtx;
    String email,apitoken,uid_get, name, dp,gender,country,password,age;
    private static final String SHARED_PREF_NAME = "MyPref";
    private static final String API_TOKEN= "api_token";
    private static final String KEY_USER_ID = "keyuserid";

    private static final String KEY_USER_NAME = "keyusername";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_GENDER = "keyusergender";
    private static final String KEY_USER_DOB = "keyuserdob";
    private static final String KEY_USER_DP = "keyuserdp";

    private static final String KEY_USER_PASSWORD = "keyuserpassword";
    private static final String KEY_USER_COUNTRY = "keyusercountry";
    public Session(Context context) {
        mCtx = context;
    }

    public static synchronized Session getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Session(context);
        }
        return mInstance;
    }
    public boolean userLogin(Data user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_GENDER, user.getGender());
        editor.putString(KEY_USER_DOB, user.getAge());
        editor.putString(KEY_USER_DP, user.getDp());
        editor.putString(KEY_USER_PASSWORD, user.getPassword());
        editor.putString(KEY_USER_COUNTRY, user.getCountry());
        editor.apply();
        return true;
    }
    public boolean userLoginDetail(String api_token,String id,String name,String email,String password,String dob,String dp,String gender,String country) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(API_TOKEN, api_token);
        editor.putString(KEY_USER_ID, id);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_EMAIL,email);
        editor.putString(KEY_USER_GENDER,gender);
        editor.putString(KEY_USER_DOB, dob);
        editor.putString(KEY_USER_DP, dp);
        editor.putString(KEY_USER_PASSWORD,password);
        editor.putString(KEY_USER_COUNTRY, country);
        editor.apply();
        return true;
    }


    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_EMAIL, null) != null)
            return true;
        return false;
    }
    public String getDp()  {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_DP, null) != null)
        {
            dp=sharedPreferences.getString(KEY_USER_DP, null);
        }

        return dp;
    }
    public String getName()  {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_NAME, null) != null)
        {
           name=sharedPreferences.getString(KEY_USER_NAME, null);
        }

        return name;
    }
    public String getEmail()  {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_EMAIL, null) != null)
        {
            email=sharedPreferences.getString(KEY_USER_EMAIL, null);
        }

     return email;
    }
    public String getApiToken()  {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(API_TOKEN, null) != null)
        {
            apitoken=sharedPreferences.getString(API_TOKEN, null);
        }

        return apitoken;
    }
    public String getUserId()  {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_ID, null) != null)
        {
            uid_get=sharedPreferences.getString(KEY_USER_ID, null);
        }

        return uid_get;
    }
    public String getCountry()  {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_COUNTRY, null) != null)
        {
            country=sharedPreferences.getString(KEY_USER_COUNTRY, null);
        }

        return country;
    }
    public String getGender()  {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_GENDER, null) != null)
        {
            gender=sharedPreferences.getString(KEY_USER_GENDER, null);
        }

        return gender;
    }
    public String getPassword()  {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_PASSWORD, null) != null)
        {
           password=sharedPreferences.getString(KEY_USER_PASSWORD, null);
        }

        return password;
    }
    public String getAge()  {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_DOB, null) != null)
        {
            age=sharedPreferences.getString(KEY_USER_DOB, null);
        }

        return age;
    }

    public Data getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Data(
                sharedPreferences.getString(KEY_USER_ID, null),

                sharedPreferences.getString(KEY_USER_NAME, null),
                sharedPreferences.getString(KEY_USER_EMAIL, null),

                sharedPreferences.getString(KEY_USER_DP, null),
                sharedPreferences.getString(KEY_USER_DOB, null),
                sharedPreferences.getString(KEY_USER_PASSWORD, null),
                sharedPreferences.getString(KEY_USER_GENDER, null),
                sharedPreferences.getString(KEY_USER_COUNTRY, null)
        );
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
