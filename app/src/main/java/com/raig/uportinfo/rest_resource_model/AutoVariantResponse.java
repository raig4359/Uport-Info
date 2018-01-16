package com.raig.uportinfo.rest_resource_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gaurav on 16-01-2018.
 */

public class AutoVariantResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("response")
    @Expose
    public List<AutoVariant> response = null;

    @SerializedName("message")
    @Expose
    public String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AutoVariant> getAutoVariant() {
        return response;
    }

    public void setAutoVariant(List<AutoVariant> response) {
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
        return "AutoVariantResponse{" +
                "status='" + status + '\'' +
                ", response=" + (response != null ? response.toArray() : "") +
                ", message='" + message + '\'' +
                '}';
    }
}
