package com.raig.uportinfo.user_form;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.raig.uportinfo.R;
import com.raig.uportinfo.UIFunctions;
import com.raig.uportinfo.data.VehicleInfoEvent;
import com.raig.uportinfo.data.VehicleType;
import com.raig.uportinfo.rest_resource_model.AutoVariant;
import com.raig.uportinfo.ui_components.VehicleInfoView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VehicleDetailFragment extends Fragment {

    private static final String VEHICLE_INFO_PARAM = "vehicleInfo";
    private static final String VARIANT_INFO_PARAM = "variantInfo";
    private String TAG = "VehicleDetailFrag";

    View view;
    @BindView(R.id.ll_vehicle_details)
    LinearLayout rootVehicle;

    private OnFormInteractionListener mListener;
    ArrayList<VehicleType> vehicleTypeList;
    ArrayList<AutoVariant> variantList;
    ArrayList<String> varStr;

    UIFunctions uiFunctions;


    public VehicleDetailFragment() {
        // Required empty public constructor
    }

    public static VehicleDetailFragment newInstance(ArrayList<VehicleType> vehicleTypeList, ArrayList<AutoVariant> variantList) {
        VehicleDetailFragment fragment = new VehicleDetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(VEHICLE_INFO_PARAM, vehicleTypeList);
        args.putParcelableArrayList(VARIANT_INFO_PARAM, variantList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vehicleTypeList = getArguments().getParcelableArrayList(VEHICLE_INFO_PARAM);
        variantList = getArguments().getParcelableArrayList(VARIANT_INFO_PARAM);
        varStr = new ArrayList<>();
        for (AutoVariant variant : variantList) {
            varStr.add(variant.getTruckTypeName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Vehicle Information");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vehicle_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        preFillData();
    }

    private void preFillData() {
        for (VehicleType vehicleType : vehicleTypeList) {
            rootVehicle.addView(new VehicleInfoView(getActivity(), variantList, varStr, vehicleType));
        }
        VehicleInfoView vehicleInfoView = (VehicleInfoView) rootVehicle.getChildAt(0);
        vehicleInfoView.setDeleteButtonVisibility(View.GONE);
    }

    private void init() {
        uiFunctions = new UIFunctions();
        rootVehicle.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                Log.e(TAG, "onChildViewAdded: ");
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {

            }
        });
    }

    @OnClick(R.id.tv_add_new)
    public void addNewVehicleField() {
//        ConstraintLayout newVehicleField = (ConstraintLayout) LayoutInflater.from(getActivity())
//                .inflate(R.layout.layout_vehicle_detail, null);
//        newVehicleField.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
        rootVehicle.addView(new VehicleInfoView(getActivity(), variantList, varStr,
                new VehicleType("", "", "")));
    }

    @OnClick(R.id.bt_next)
    public void showDriverInfoForm() {
        if (validateVehicleForm()) {
            EventBus.getDefault().post(new VehicleInfoEvent(vehicleTypeList));
            mListener.showDriverDetailsForm();
            Toast.makeText(getActivity(), "Vehicle Information saved!", Toast.LENGTH_SHORT).show();
        }else {
            uiFunctions.showMessage(view, "All fields are mandatory!", Snackbar.LENGTH_LONG);
        }
    }

    private boolean validateVehicleForm() {
        vehicleTypeList.clear();
        int vc = rootVehicle.getChildCount();
        for (int i = 0; i < vc; ++i) {
            VehicleInfoView vehicleInfoView = (VehicleInfoView) rootVehicle.getChildAt(i);
            VehicleType type = vehicleInfoView.getVehicleType();
            if (type == null) {
                return false;
            }
            vehicleTypeList.add(type);
        }
        return true;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFormInteractionListener) {
            mListener = (OnFormInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFormInteractionListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: # " );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: # " );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: # " );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView: # " );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
