package com.raig.uportinfo.user_form;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.raig.uportinfo.R;
import com.raig.uportinfo.UIFunctions;
import com.raig.uportinfo.data.DriverInfoEvent;
import com.raig.uportinfo.data.DriverModel;
import com.raig.uportinfo.data.UserProfileEvent;
import com.raig.uportinfo.data.UserProfileModel;
import com.raig.uportinfo.data.VehicleInfoEvent;
import com.raig.uportinfo.data.VehicleType;
import com.raig.uportinfo.network.RestClient;
import com.raig.uportinfo.rest_resource_model.AutoVariant;
import com.raig.uportinfo.rest_resource_model.AutoVariantResponse;
import com.raig.uportinfo.ui_components.CustomProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends AppCompatActivity implements OnFormInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    CustomProgressDialog progressDialog;
    UIFunctions uiFunctions;

    ArrayList<AutoVariant> variantList;
    ArrayList<VehicleType> vehicleTypeList;
    ArrayList<DriverModel> driverList;
    UserProfileModel userProfileModel;

    String USER_FORM = "UserDetailFrag";
    String VEHICLE_FORM = "VehicleDetailFrag";
    String DRIVER_FORM = "DriverDetailFrag";
    private String TAG = "FormActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);
        init();
        getTruckTypes();
    }

    private void init() {
        setSupportActionBar(toolbar);
        progressDialog = new CustomProgressDialog(this, false);
        uiFunctions = new UIFunctions();
        /*creating a empty user model*/
        userProfileModel = new UserProfileModel();
        vehicleTypeList = new ArrayList<>();
        /*adding a single empty vehicle type model*/
        vehicleTypeList.add(new VehicleType("", "", ""));
        driverList = new ArrayList<>();
        /*adding a single empty driver model*/
        driverList.add(new DriverModel("", "", "", "", ""));
        variantList = new ArrayList<>();
    }

    private void getTruckTypes() {
         RestClient.getRestClient().getUportService()
                .getTruckTypes().enqueue(new Callback<AutoVariantResponse>() {
            @Override
            public void onResponse(Call<AutoVariantResponse> call, Response<AutoVariantResponse> response) {
                Log.e(TAG, "onResponse: " + response.body().toString());

                AutoVariant variant1 = new AutoVariant();
                variant1.setId("-1");
                variant1.setTruckTypeName("Select Vehicle Type");

                variantList.add(variant1);
                variantList.addAll(response.body().getAutoVariant());

                progressBar.setVisibility(View.GONE);
                 showUserInfoForm();
            }

            @Override
            public void onFailure(Call<AutoVariantResponse> call, Throwable t) {
                t.printStackTrace();

                uiFunctions.showMessage(rlRoot, "Something went wrong!", Snackbar.LENGTH_LONG);

                AutoVariant variant1 = new AutoVariant();
                variant1.setId("-1");
                variant1.setTruckTypeName("Select Vehicle Type");

                variantList.add(variant1);
                variantList.add(variant1);
                variantList.add(variant1);
                variantList.add(variant1);
                variantList.add(variant1);
                progressBar.setVisibility(View.GONE);
                showUserInfoForm();
            }
        });
    }

    public void showUserInfoForm() {
        setTitle("User Information");
        FragmentManager manager = getSupportFragmentManager();
        UserDetailFragment userDetailFragment = UserDetailFragment.newInstance(userProfileModel);
        manager.beginTransaction()
                .replace(R.id.frame, userDetailFragment, USER_FORM)
                .addToBackStack(USER_FORM)
                .commit();
    }

    @Override
    public void showVehicleInfoForm() {
        setTitle("Vehicle Information");
        FragmentManager manager = getSupportFragmentManager();
        VehicleDetailFragment vehicleDetailFragment = VehicleDetailFragment.newInstance(vehicleTypeList, variantList);
        manager.beginTransaction()
                .replace(R.id.frame, vehicleDetailFragment, VEHICLE_FORM)
                .addToBackStack(VEHICLE_FORM)
                .commit();
    }

    @Override
    public void showDriverDetailsForm() {

    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVehicleInfoEvent(VehicleInfoEvent vehicleInfoEvent) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDriverInfoEvent(DriverInfoEvent driverInfoEvent) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProfileInfoEvent(UserProfileEvent profileEvent) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
