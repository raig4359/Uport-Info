package com.raig.uportinfo.user_form;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.raig.uportinfo.R;
import com.raig.uportinfo.UIFunctions;
import com.raig.uportinfo.data.DriverModel;
import com.raig.uportinfo.data.UserProfileModel;
import com.raig.uportinfo.data.VehicleType;
import com.raig.uportinfo.genericRecyclerview.Interface.RecyclerItemClickListener;
import com.raig.uportinfo.genericRecyclerview.Interface.RecyclerViewHolderCallBack;
import com.raig.uportinfo.genericRecyclerview.RecyclerViewAdapter;
import com.raig.uportinfo.genericRecyclerview.RecyclerViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewFragment extends Fragment {

    View view;

    @BindView(R.id.iv_user)
    ImageView ivUser;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_company_name)
    EditText etCompanyName;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_pin_code)
    EditText etPincode;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.et_state)
    EditText etState;
    @BindView(R.id.et_country)
    EditText etCountry;
    @BindView(R.id.rv_vehicle_info)
    RecyclerView rvVehicleInfo;
    @BindView(R.id.rv_driver_info)
    RecyclerView rvDriverInfo;

    @BindView(R.id.bt_edit)
    Button btEdit;
    @BindView(R.id.bt_add_new_info)
    Button btAddNew;

    UIFunctions uiFunctions;
    UserProfileModel profileModel;
    ArrayList<VehicleType> vehicleTypeList;
    ArrayList<DriverModel> driverList;

    private OnFormInteractionListener mListener;

    private final static String USER_PROFILE_PARAM = "userProfileModel";
    private static final String VEHICLE_INFO_PARAM = "vehicleInfo";
    private static final String DRIVER_INFO_PARAM = "DriverInfo";
    private String TAG = "PreviewFragment";


    public PreviewFragment() {
        // Required empty public constructor
    }

    public static PreviewFragment newInstance(UserProfileModel model, ArrayList<VehicleType> vehicleTypeList, ArrayList<DriverModel> driverList) {
        PreviewFragment fragment = new PreviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER_PROFILE_PARAM, model);
        args.putParcelableArrayList(VEHICLE_INFO_PARAM, vehicleTypeList);
        args.putParcelableArrayList(DRIVER_INFO_PARAM, driverList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileModel = getArguments().getParcelable(USER_PROFILE_PARAM);
        vehicleTypeList = getArguments().getParcelableArrayList(VEHICLE_INFO_PARAM);
        driverList = getArguments().getParcelableArrayList(DRIVER_INFO_PARAM);
        uiFunctions = new UIFunctions();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_preview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setData();
    }

    private void setData() {

        etName.setText(profileModel.getName());
        etEmail.setText(profileModel.getEmail());
        etMobile.setText(profileModel.getMobileNo());
        etCompanyName.setText(profileModel.getCompanyName());
        etAddress.setText(profileModel.getAddress());
        etPincode.setText(profileModel.getPincode());
        etCity.setText(profileModel.getCity());
        etState.setText(profileModel.getState());
        etCountry.setText(profileModel.getCountry());

        RecyclerViewAdapter vehicleAdapter = new RecyclerViewAdapter(getActivity(), vehicleTypeList, new RecyclerViewHolderCallBack() {

            @Override
            public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new VehicleHolder(LayoutInflater.from(getActivity()).inflate(R.layout.row_vehicle_info, parent, false));
            }
        }, new RecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClicked(RecyclerViewHolder holder, Object itemObject, int itemPosition) {

            }
        });
        rvVehicleInfo.setAdapter(vehicleAdapter);

        RecyclerViewAdapter driverAdapter = new RecyclerViewAdapter(getActivity(), driverList, new RecyclerViewHolderCallBack() {

            @Override
            public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new DriverHolder(LayoutInflater.from(getActivity()).inflate(R.layout.row_driver_info, parent, false));
            }
        }, new RecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClicked(RecyclerViewHolder holder, Object itemObject, int itemPosition) {

            }
        });
        rvDriverInfo.setAdapter(driverAdapter);

    }

    @OnClick(R.id.bt_edit)
    public void onEdit() {
        mListener.onEditClick();
    }

    @OnClick(R.id.bt_add_new_info)
    public void onAddNew() {
        mListener.addNew();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFormInteractionListener) {
            mListener = (OnFormInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
