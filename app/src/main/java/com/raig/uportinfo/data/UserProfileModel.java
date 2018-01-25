package com.raig.uportinfo.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gaurav on 16-01-2018.
 */

public class UserProfileModel implements Parcelable {

    private String name;
    private String email;
    private String mobileNo;
    private String companyName;
    private String address;
    private String pincode;
    private String city;
    private String state;
    private String country;
    private String imagePath = "";

    public UserProfileModel() {
        name = "";
        email = "";
        mobileNo = "";
        companyName = "";
        address = "";
        pincode = "";
        city = "";
        state = "";
        country = "";
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
        dest.writeString(this.companyName);
        dest.writeString(this.address);
        dest.writeString(this.pincode);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.country);
        dest.writeString(this.imagePath);
    }

    protected UserProfileModel(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.mobileNo = in.readString();
        this.companyName = in.readString();
        this.address = in.readString();
        this.pincode = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.country = in.readString();
        this.imagePath = in.readString();
    }

    public static final Creator<UserProfileModel> CREATOR = new Creator<UserProfileModel>() {
        @Override
        public UserProfileModel createFromParcel(Parcel source) {
            return new UserProfileModel(source);
        }

        @Override
        public UserProfileModel[] newArray(int size) {
            return new UserProfileModel[size];
        }
    };
}
