package com.raig.uportinfo.ui_components;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.raig.uportinfo.R;
import com.raig.uportinfo.rest_resource_model.AutoVariant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gaurav on 16-01-2018.
 */

public class VehicleInfoView extends ConstraintLayout {

    @BindView(R.id.sp_vehicle_type)
    AppCompatSpinner spSelectType;
    @BindView(R.id.et_vehicle_type)
    EditText etModel;
    @BindView(R.id.iv_delete_vehicle_item)
    ImageView ivDel;

    ArrayAdapter<String> adapter;

    ArrayList<String> varStr;
    ArrayList<AutoVariant> variantList;

    String selectedTypeId = "-1";
    String selectedTypeName = "";
    int selectedSpinnerPosition = -1;
    String selectedVehicleModel = "";

    public VehicleInfoView(Context context, ArrayList<AutoVariant> variantList, ArrayList<String> varStr) {
        this(context);
        init(context);
        this.variantList = variantList;
        this.varStr = varStr;
        initTypeAdapter(context);
    }

    public VehicleInfoView(Context context) {
        super(context);
    }

    public VehicleInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VehicleInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.layout_vehicle_detail, this);
        ButterKnife.bind(this);
    }

    public void initTypeAdapter(Context context) {
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, varStr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSelectType.setAdapter(adapter);
        spSelectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedTypeId = variantList.get(position).getId();
                    selectedTypeName = variantList.get(position).getTruckTypeName();
                    selectedSpinnerPosition = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public String getSelectedTypeId() {
        return selectedTypeId;
    }

    public void setSelectedTypeId(String selectedTypeId) {
        this.selectedTypeId = selectedTypeId;
    }

    public String getSelectedTypeName() {
        return selectedTypeName;
    }

    public void setSelectedTypeName(String selectedTypeName) {
        this.selectedTypeName = selectedTypeName;
    }

    public String getSelectedVehicleModel() {
        return selectedVehicleModel;
    }

    public void setSelectedVehicleModel(String selectedVehicleModel) {
        this.selectedVehicleModel = selectedVehicleModel;
    }

    @OnClick(R.id.iv_delete_vehicle_item)
    public void onRemoveInfoField() {
        ((ViewGroup) this.getParent()).removeView(this);
    }

}
