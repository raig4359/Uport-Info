package com.raig.uportinfo.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gaurav on 29-01-2018.
 */

public class VechicleInfo {
    @SerializedName("truck_type")
    @Expose
    private String truckType;
    @SerializedName("model")
    @Expose
    private String model;

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
