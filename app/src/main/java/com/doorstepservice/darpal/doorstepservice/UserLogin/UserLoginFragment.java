package com.doorstepservice.darpal.doorstepservice.UserLogin;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doorstepservice.darpal.doorstepservice.R;
import com.doorstepservice.darpal.doorstepservice.ServiceHandler;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserLoginFragment extends Fragment {


    public UserLoginFragment() {
        // Required empty public constructor
    }

    Button login, register;
    EditText email, password;
    String EMAIL = "EmailId";
    String PASSWORD = "Password";
    public String email1,password1,url,uid,s1,s2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        SharedPreferences pref = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        boolean b = pref.getBoolean("login", false);


        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        login = (Button) view.findViewById(R.id.btnLogin);
        register = (Button) view.findViewById(R.id.btnLinkToRegisterScreen);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.doorstepservice.darpal.doorstepservice.UserLogin.UserRegistrationFragment userRegistrationFragment = new com.doorstepservice.darpal.doorstepservice.UserLogin.UserRegistrationFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame, userRegistrationFragment).commit();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("") && password.getText().toString().equals("")) {
                    email.setError("This field cannot be blank");
                    password.setError("This field cannot be blank");
                    Toast.makeText(getActivity(), "Please Complete above required fields", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        s1 = email.getText().toString();
                        s2 = password.getText().toString();
                        url = "http://foodlabrinth.com/DSS_userLogin.php?EmailId=" + s1 + "&Password=" + s2;
                        //URL sourceUrl = new URL(url);
                        new Checkdata().execute();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Email / Password is incorrect.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });


        return view;
    }


    class Checkdata extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            //pd.setTitle("Lading Data");
            pd.setMessage("Please wait");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String result = serviceHandler.GetHTTPData(url);
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    email1 = jsonObject.getString(EMAIL);
                    password1 = jsonObject.getString(PASSWORD);
                    uid = jsonObject.getString("uid");
                    Log.e("Login ID is:", uid);

                }
            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            try {
                if (email1.equals(s1) && password1.equals(s2)) {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();

                    //Session code
                    SharedPreferences pref = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = pref.edit();
                    ed.putString("email", s1);
                    ed.putString("password", s2);
                    ed.putString("uid", uid);
                    ed.putBoolean("login", true);
                    ed.apply();
                    ed.commit();

                    String restoredText = pref.getString("uid", null);
                        Log.d("UID", restoredText);//"No name defined" is the default value.


                    com.doorstepservice.darpal.doorstepservice.UserLogin.UserProfileFragment profile = new com.doorstepservice.darpal.doorstepservice.UserLogin.UserProfileFragment();
                    getFragmentManager().beginTransaction().replace(R.id.frame, profile).commit();

                }
            } catch (Exception e) {
                //isOnline();
                //Toast.makeText(getActivity(), "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
