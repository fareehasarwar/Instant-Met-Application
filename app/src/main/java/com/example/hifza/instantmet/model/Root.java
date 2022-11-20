package com.example.hifza.instantmet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fareeha on 1/29/2018.
 */

public class Root {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_code")
    @Expose
    private String status_code;



    @SerializedName("dataarray")
    @Expose
    private List<Data> dataarray;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public List<Data> getDataarray() {
        return dataarray;
    }

    public void setDataarray(List<Data> dataarray) {
        this.dataarray = dataarray;
    }
}
