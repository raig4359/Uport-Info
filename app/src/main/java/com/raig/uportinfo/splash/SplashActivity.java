package com.raig.uportinfo.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.raig.uportinfo.SharedPrefUtils;
import com.raig.uportinfo.login.LoginActivity;
import com.raig.uportinfo.user_form.FormActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPrefUtils.isLoggedIn(getApplicationContext())) {
                    startActivity(new Intent(SplashActivity.this, FormActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 1200);
    }
}
