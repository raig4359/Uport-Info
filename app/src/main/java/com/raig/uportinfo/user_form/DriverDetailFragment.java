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

import com.raig.uportinfo.R;
import com.raig.uportinfo.UIFunctions;
import com.raig.uportinfo.data.DriverInfoEvent;
import com.raig.uportinfo.data.DriverModel;
import com.raig.uportinfo.ui_components.DriverInfoView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DriverDetailFragment extends Fragment {

    private static final String DRIVER_INFO_PARAM = "vehicleInfo";

    View view;
    @BindView(R.id.ll_driver_details)
    LinearLayout rootDriver;

    private OnFormInteractionListener mListener;
    ArrayList<DriverModel> driverList;
    private String TAG = "DriverDetail";
    private UIFunctions uiFunctions;

    public DriverDetailFragment() {
        // Required empty public constructor
    }

    public static DriverDetailFragment newInstance(ArrayList<DriverModel> driverList) {
        DriverDetailFragment fragment = new DriverDetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(DRIVER_INFO_PARAM, driverList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        driverList = getArguments().getParcelableArrayList(DRIVER_INFO_PARAM);
        uiFunctions = new UIFunctions();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Driver Information");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_driver_detail, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        preFillData();
    }

    private void init() {
        rootDriver.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                Log.e(TAG, "onChildViewAdded: ");
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {

            }
        });
    }


    private void preFillData() {
        for (DriverModel driverModel : driverList) {
            rootDriver.addView(new DriverInfoView(getActivity(), driverModel));
        }
        DriverInfoView driverInfoView = (DriverInfoView) rootDriver.getChildAt(0);
        driverInfoView.setDeleteButtonVisibility(View.GONE);
    }

    @OnClick(R.id.tv_add_new)
    public void addNewDriverField() {
        rootDriver.addView(new DriverInfoView(getActivity(),
                new DriverModel("", "", "", "", "")));
    }

    @OnClick(R.id.bt_submit)
    public void submitDriverInfo() {
        if (validateDriverInfo()) {
            EventBus.getDefault().post(new DriverInfoEvent(driverList));
        } else {
            uiFunctions.showMessage(view, "Fill all mandatory fields!", Snackbar.LENGTH_LONG);
        }
    }

    private boolean validateDriverInfo() {
        driverList.clear();
        int dc = rootDriver.getChildCount();
        for (int i = 0; i < dc; ++i) {
            DriverInfoView driverInfoView = (DriverInfoView) rootDriver.getChildAt(i);
            DriverModel model = driverInfoView.getDriverModel();
            if (model == null) {
                return false;
            }
            driverList.add(model);
        }
        return true;
    }

    private void saveDriverInfoTemporarily(){
        driverList.clear();
        int dc = rootDriver.getChildCount();
        for (int i = 0; i < dc; ++i) {
            DriverInfoView driverInfoView = (DriverInfoView) rootDriver.getChildAt(i);
            DriverModel model = driverInfoView.getTemporaryModel();
            driverList.add(model);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        saveDriverInfoTemporarily();
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
