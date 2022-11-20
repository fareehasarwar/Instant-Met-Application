package com.example.hifza.instantmet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fareeha on 1/20/2018.
 */

public class Pojo {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("response")
    @Expose
    private String response;


    @SerializedName("dataa")
    @Expose
    private List<Data> dataa;
    @SerializedName("datarray")
    @Expose
    private List<Data> datarray;



    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("dataarray")
    @Expose
    private Dataarray dataarray;



    @SerializedName("array")
    @Expose

    private String array;

    public String getData() {
        return data;
    }


    public void setData(String data) {
        this.data = data;
    }
    public String getArray() {
        return array;
    }

    public void setArray(String array) {
        this.array = array;
    }
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Dataarray getDataarray() {
        return dataarray;
    }

    public void setDataarray(Dataarray dataarray) {
        this.dataarray = dataarray;
    }
    public List<Data> getDataa() {
        return dataa;
    }

    public void setDataa(List<Data> dataa) {
        this.dataa = dataa;
    }
    public List<Data> getDatarray() {
        return datarray;
    }

    public void setDatarray(List<Data> datarray) {
        this.datarray = datarray;
    }
}
