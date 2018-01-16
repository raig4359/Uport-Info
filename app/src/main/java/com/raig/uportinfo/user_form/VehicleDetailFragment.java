package com.raig.uportinfo.user_form;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.raig.uportinfo.R;
import com.raig.uportinfo.data.VehicleType;
import com.raig.uportinfo.rest_resource_model.AutoVariant;
import com.raig.uportinfo.ui_components.VehicleInfoView;

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
        addNewVehicleField();
    }

    private void init() {
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
        rootVehicle.addView(new VehicleInfoView(getActivity(),variantList,varStr));
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
