package com.raig.uportinfo.rest_resource_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gaurav on 1/14/2018.
 */

public class ApiResponse {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("response")
    @Expose
    public List<Object> response = null;
    @SerializedName("message")
    @Expose
    public String message;

    /**
     *
     * @param message
     * @param response
     * @param status
     */
    public ApiResponse(String status, List<Object> response, String message) {
        super();
        this.status = status;
        this.response = response;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "status='" + status + '\'' +
                ", response=" + response +
                ", message='" + message + '\'' +
                '}';
    }
}
