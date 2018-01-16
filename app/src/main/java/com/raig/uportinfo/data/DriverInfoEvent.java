package com.raig.uportinfo.data;

import java.util.ArrayList;

/**
 * Created by Gaurav on 16-01-2018.
 */

public class DriverInfoEvent {

    ArrayList<DriverModel> driverModels;

    public DriverInfoEvent(ArrayList<DriverModel> driverModels) {
        this.driverModels = driverModels;
    }
}
