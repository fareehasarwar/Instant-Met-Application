package com.example.hifza.instantmet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fareeha on 1/9/2018.
 */

public class RootObject {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_code")
    @Expose
    private String status_code;

    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("dataarray")
    @Expose
    private List<Data> dataarray;




    public List<Data> getDatarray() {
        return datarray;
    }

    public void setDatarray(List<Data> datarray) {
        this.datarray = datarray;
    }

    @SerializedName("datarray")
    @Expose

    private List<Data> datarray;
    public List<Data> getDataarray() {
        return dataarray;
    }

    public void setDataarray(List<Data> dataarray) {
        this.dataarray = dataarray;
    }


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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
