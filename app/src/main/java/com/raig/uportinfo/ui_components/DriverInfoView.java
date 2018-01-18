package com.raig.uportinfo.ui_components;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.raig.uportinfo.R;
import com.raig.uportinfo.data.DriverModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gaurav on 17-01-2018.
 */

public class DriverInfoView extends ConstraintLayout {

    @BindView(R.id.iv_delete_driver_item)
    ImageView ivDelete;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_experience)
    EditText etExperience;
    @BindView(R.id.et_trucks_driven)
    EditText etVehiclesDriven;

    DriverModel driverModel;

    public DriverInfoView(Context context, DriverModel model) {
        this(context);
        init(context);
        this.driverModel = model;
        setData();
    }

    public DriverInfoView(Context context) {
        super(context);
    }


    public DriverInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DriverInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.layout_driver_details, this);
        ButterKnife.bind(this);
    }

    private void setData() {
        etName.setText(driverModel.getName());
        etEmail.setText(driverModel.getEmail());
        etMobile.setText(driverModel.getMobileNo());
        etExperience.setText(driverModel.getExperience());
        etVehiclesDriven.setText(driverModel.getVehiclesDriven());
    }

    @OnClick(R.id.iv_delete_driver_item)
    public void onRemoveInfoField() {
        this.driverModel = null;
        ((ViewGroup) this.getParent()).removeView(this);
    }

    public DriverModel getDriverModel() {
        driverModel.setName(etName.getText().toString().trim());
        driverModel.setEmail(etEmail.getText().toString().trim());
        driverModel.setMobileNo(etMobile.getText().toString().trim());
        driverModel.setExperience(etExperience.getText().toString().trim());
        driverModel.setVehiclesDriven(etVehiclesDriven.getText().toString().trim());

        if (driverModel.getName().isEmpty() || driverModel.getMobileNo().isEmpty() ||
                driverModel.getExperience().isEmpty() || driverModel.getVehiclesDriven().isEmpty()) {
            return null;
        } else {
            return driverModel;
        }
    }

    public DriverModel getTemporaryModel() {
        driverModel.setName(etName.getText().toString().trim());
        driverModel.setEmail(etEmail.getText().toString().trim());
        driverModel.setMobileNo(etMobile.getText().toString().trim());
        driverModel.setExperience(etExperience.getText().toString().trim());
        driverModel.setVehiclesDriven(etVehiclesDriven.getText().toString().trim());
        return driverModel;
    }

    public void setDeleteButtonVisibility(int visibility) {
        ivDelete.setVisibility(visibility);
    }
}
