package com.raig.uportinfo.user_form;

/**
 * Created by Gaurav on 15-01-2018.
 */

public interface OnFormInteractionListener {
    void showVehicleInfoForm();
    void showDriverDetailsForm();
    void requestPermission();
    void onEditClick();
    void addNew();
}
