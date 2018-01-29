package com.raig.uportinfo.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gaurav on 29-01-2018.
 */

public class CreateResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("response")
    @Expose
    private List<UserDataInfo> response = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UserDataInfo> getUserDataInfo() {
        return response;
    }

    public void setUserDataInfo(List<UserDataInfo> response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CreateResponse{" +
                "status='" + status + '\'' +
                ", response=" + response +
                ", message='" + message + '\'' +
                '}';
    }
}
