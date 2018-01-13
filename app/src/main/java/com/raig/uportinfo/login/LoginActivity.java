package com.raig.uportinfo.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.raig.uportinfo.R;
import com.raig.uportinfo.ui_components.CustomProgressDialog;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CustomProgressDialog dialog = new CustomProgressDialog(this);
        dialog.show();
    }
}
