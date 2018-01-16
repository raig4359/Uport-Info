package com.raig.uportinfo.data;

import java.util.ArrayList;

/**
 * Created by Gaurav on 16-01-2018.
 */

public class VehicleInfoEvent {

    ArrayList<VehicleType> vehicleTypes;

    public VehicleInfoEvent(ArrayList<VehicleType> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

}
