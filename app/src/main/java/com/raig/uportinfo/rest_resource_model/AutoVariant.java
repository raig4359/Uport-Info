package com.raig.uportinfo.rest_resource_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gaurav on 16-01-2018.
 */

public class AutoVariant implements Parcelable {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("truck_type_name")
    @Expose
    public String truckTypeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTruckTypeName() {
        return truckTypeName;
    }

    public void setTruckTypeName(String truckTypeName) {
        this.truckTypeName = truckTypeName;
    }

    @Override
    public String toString() {
        return "AutoVariant{" +
                "id='" + id + '\'' +
                ", truckTypeName='" + truckTypeName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.truckTypeName);
    }

    public AutoVariant() {
    }

    protected AutoVariant(Parcel in) {
        this.id = in.readString();
        this.truckTypeName = in.readString();
    }

    public static final Parcelable.Creator<AutoVariant> CREATOR = new Parcelable.Creator<AutoVariant>() {
        @Override
        public AutoVariant createFromParcel(Parcel source) {
            return new AutoVariant(source);
        }

        @Override
        public AutoVariant[] newArray(int size) {
            return new AutoVariant[size];
        }
    };
}
