package com.doorstepservice.darpal.doorstepservice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.doorstepservice.darpal.doorstepservice.UserLogin.UserLoginActivity;

public class MainActivity extends AppCompatActivity {


    android.support.v7.widget.SearchView searchView;
    Button login,feeds;
    LinearLayout linearLayout;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = (android.support.v7.widget.SearchView) findViewById(R.id.floating_search_view);
        searchView.setQueryHint("Enter Zip code or City");
        //searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, TypeOfServiceListActivity.class);
                intent.putExtra("zip", String.valueOf(query));
                intent.putExtra("city",String.valueOf(query));
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        login = (Button) findViewById(R.id.userlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UserLoginActivity.class);
                startActivity(intent);
            }
        });

        feeds = (Button) findViewById(R.id.feed);
        feeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FeedsActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences pref1 = getSharedPreferences("userpref", Context.MODE_PRIVATE);
        boolean b1 = pref1.getBoolean("login", false);

        if(b1){
            login.setText("View My Profile");
        }
        else {
            login.setText("Login");
        }

    }


}
