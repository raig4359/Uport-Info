package com.raig.uportinfo.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gaurav on 16-01-2018.
 */

public class VehicleType implements Parcelable {

    private String id;
    private String vehicleTypeName;
    private String vehicleModel;
    private int spinnerPos = -1;

    public VehicleType(String id, String vehicleTypeName, String vehicleModel) {
        this.id = id;
        this.vehicleTypeName = vehicleTypeName;
        this.vehicleModel = vehicleModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public int getSpinnerPos() {
        return spinnerPos;
    }

    public void setSpinnerPos(int spinnerPos) {
        this.spinnerPos = spinnerPos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.vehicleTypeName);
        dest.writeString(this.vehicleModel);
        dest.writeInt(this.spinnerPos);
    }

    protected VehicleType(Parcel in) {
        this.id = in.readString();
        this.vehicleTypeName = in.readString();
        this.vehicleModel = in.readString();
        this.spinnerPos = in.readInt();
    }

    public static final Parcelable.Creator<VehicleType> CREATOR = new Parcelable.Creator<VehicleType>() {
        @Override
        public VehicleType createFromParcel(Parcel source) {
            return new VehicleType(source);
        }

        @Override
        public VehicleType[] newArray(int size) {
            return new VehicleType[size];
        }
    };
}
