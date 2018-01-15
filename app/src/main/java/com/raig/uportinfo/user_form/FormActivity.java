package com.raig.uportinfo.user_form;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.raig.uportinfo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FormActivity extends AppCompatActivity implements OnFormInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String USER_FORM = "UserDetailFrag";
    String VEHICLE_FORM = "VehicleDetailFrag";
    String DRIVER_FORM = "DriverDetailFrag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);
        init();
        showUserInfoForm();
    }

    private void init() {
        setSupportActionBar(toolbar);
    }

    public void showUserInfoForm() {
        setTitle("User Information");
        FragmentManager manager = getSupportFragmentManager();
        UserDetailFragment userDetailFragment = UserDetailFragment.newInstance();
        manager.beginTransaction()
                .replace(R.id.frame, userDetailFragment, USER_FORM)
                .addToBackStack(USER_FORM)
                .commit();
    }

    @Override
    public void showVehicleInfoForm() {

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
}
