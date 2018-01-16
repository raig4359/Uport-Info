package com.raig.uportinfo.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gaurav on 16-01-2018.
 */

public class DriverModel implements Parcelable {

    private String name;
    private String email;
    private String mobileNo;
    private String experience;
    private String vehiclesDriven;

    public DriverModel(String name, String email, String mobileNo, String experience, String vehiclesDriven) {
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.experience = experience;
        this.vehiclesDriven = vehiclesDriven;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getVehiclesDriven() {
        return vehiclesDriven;
    }

    public void setVehiclesDriven(String vehiclesDriven) {
        this.vehiclesDriven = vehiclesDriven;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.mobileNo);
        dest.writeString(this.experience);
        dest.writeString(this.vehiclesDriven);
    }

    protected DriverModel(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.mobileNo = in.readString();
        this.experience = in.readString();
        this.vehiclesDriven = in.readString();
    }

    public static final Parcelable.Creator<DriverModel> CREATOR = new Parcelable.Creator<DriverModel>() {
        @Override
        public DriverModel createFromParcel(Parcel source) {
            return new DriverModel(source);
        }

        @Override
        public DriverModel[] newArray(int size) {
            return new DriverModel[size];
        }
    };
}
