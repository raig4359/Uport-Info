package com.raig.uportinfo.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.raig.uportinfo.R;
import com.raig.uportinfo.SharedPrefUtils;
import com.raig.uportinfo.network.RestClient;
import com.raig.uportinfo.rest_resource_model.ApiResponse;
import com.raig.uportinfo.ui_components.CustomProgressDialog;
import com.raig.uportinfo.UIFunctions;
import com.raig.uportinfo.user_form.FormActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private String TAG = "LoginActivity";

    @BindView(R.id.cl_root)
    ConstraintLayout clRoot;
    @BindView(R.id.et_user_id)
    EditText etUserId;
    @BindView(R.id.et_pwd)
    EditText etPassword;

    UIFunctions uiFunctions;
    CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setTitle("User Information");
        uiFunctions = new UIFunctions();
        progressDialog = new CustomProgressDialog(this,false);
    }

    @OnClick(R.id.bt_login)
    public void validate() {
        switch (0) {
            case 0:
                if (etUserId.getText().toString().trim().isEmpty() ||
                        etPassword.getText().toString().trim().isEmpty()) {
                    uiFunctions.showMessage(clRoot,getString(R.string.fields_mandatory), Snackbar.LENGTH_LONG);
                    break;
                }
            case 1:
                login();
                break;
        }
    }

    public void login() {

        progressDialog.show();

        HashMap<String,String> params = new HashMap<>();
        params.put("username",etUserId.getText().toString().trim());
        params.put("password",etPassword.getText().toString().trim());


        RestClient.getRestClient().getUportService()
                .performLogin(params).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.e(TAG, "onResponse: " + response.body().toString());
                SharedPrefUtils.saveLoginState(getApplicationContext());
                progressDialog.dismiss();
                uiFunctions.showMessage(clRoot,response.body().message, Snackbar.LENGTH_LONG);
                startActivity(new Intent(LoginActivity.this, FormActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                uiFunctions.showMessage(clRoot,getString(R.string.login_failed), Snackbar.LENGTH_LONG);
            }
        });
    }

}
