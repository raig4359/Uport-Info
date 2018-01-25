package com.raig.uportinfo.user_form;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.raig.uportinfo.R;
import com.raig.uportinfo.data.VehicleType;
import com.raig.uportinfo.genericRecyclerview.RecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gaurav on 19-01-2018.
 */

public class VehicleHolder extends RecyclerViewHolder {

    @BindView(R.id.et_truck_type)
    EditText etTruckType;
    @BindView(R.id.et_vehicle_type)
    EditText etVehicleModel;

    public VehicleHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void setData(Context context, Object itemObject) {
        super.setData(context, itemObject);
        VehicleType vehicleType = (VehicleType) itemObject;
        etTruckType.setText(vehicleType.getVehicleTypeName());
        etVehicleModel.setText(vehicleType.getVehicleModel());
    }
}
