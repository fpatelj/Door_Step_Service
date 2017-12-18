package com.doorstepservice.darpal.doorstepservice.UserLogin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.doorstepservice.darpal.doorstepservice.R;

public class UserLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        SharedPreferences pref1 = getSharedPreferences("userpref", Context.MODE_PRIVATE);
        boolean b1 = pref1.getBoolean("login", false);

        if(b1){
            com.doorstepservice.darpal.doorstepservice.UserLogin.UserProfileFragment userProfileFragment = new com.doorstepservice.darpal.doorstepservice.UserLogin.UserProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, userProfileFragment).commit();
        }
        else {
            UserLoginFragment userLoginFragment = new UserLoginFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,userLoginFragment).commit();
        }
        /*UserLoginFragment userLoginFragment = new UserLoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,userLoginFragment).commit();*/

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
