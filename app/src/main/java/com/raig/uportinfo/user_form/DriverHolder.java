package com.raig.uportinfo.user_form;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.raig.uportinfo.R;
import com.raig.uportinfo.data.DriverModel;
import com.raig.uportinfo.genericRecyclerview.RecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gaurav on 19-01-2018.
 */

public class DriverHolder extends RecyclerViewHolder {

    @BindView(R.id.et_name)
     EditText etName;
    @BindView(R.id.et_email)
     EditText etEmail;
    @BindView(R.id.et_mobile)
     EditText etMobile;
    @BindView(R.id.et_experience)
     EditText etExp;
    @BindView(R.id.et_trucks_driven)
     EditText etTruckDriven;

    public DriverHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void setData(Context context, Object itemObject) {
        super.setData(context, itemObject);
        DriverModel driverModel = (DriverModel) itemObject;
        etName.setText(driverModel.getName());
        etEmail.setText(driverModel.getEmail());
        etMobile.setText(driverModel.getMobileNo());
        etExp.setText(driverModel.getExperience());
        etTruckDriven.setText(driverModel.getVehiclesDriven());
    }
}
