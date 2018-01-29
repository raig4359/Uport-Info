package com.raig.uportinfo.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gaurav on 29-01-2018.
 */

public class UserDataInfo {
    @SerializedName("userInfo")
    @Expose
    private UserInfo userInfo;
    @SerializedName("VechicleInfo")
    @Expose
    private List<VechicleInfo> vechicleInfo = null;
    @SerializedName("DriverInfo")
    @Expose
    private List<DriverInfo> driverInfo = null;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<VechicleInfo> getVechicleInfo() {
        return vechicleInfo;
    }

    public void setVechicleInfo(List<VechicleInfo> vechicleInfo) {
        this.vechicleInfo = vechicleInfo;
    }

    public List<DriverInfo> getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(List<DriverInfo> driverInfo) {
        this.driverInfo = driverInfo;
    }
}
