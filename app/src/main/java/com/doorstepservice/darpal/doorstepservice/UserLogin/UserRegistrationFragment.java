package com.doorstepservice.darpal.doorstepservice.UserLogin;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doorstepservice.darpal.doorstepservice.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserRegistrationFragment extends Fragment {


    public UserRegistrationFragment() {
        // Required empty public constructor
    }


    Button btnToLogin, register;
    EditText firstname, lastname, email, password, contact;
    String s1, s2, s3, s4, s5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_registration, container, false);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        firstname = (EditText) view.findViewById(R.id.firstname);
        lastname = (EditText) view.findViewById(R.id.lastname);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        contact = (EditText) view.findViewById(R.id.mobileno);
        btnToLogin = (Button) view.findViewById(R.id.btnLinkToLoginScreen);
        register = (Button) view.findViewById(R.id.btnRegister);


        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLoginFragment userLoginFragment = new UserLoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame, userLoginFragment).commit();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    s1 = firstname.getText().toString();
                    s2 = lastname.getText().toString();
                    s3 = email.getText().toString();
                    s4 = password.getText().toString();
                    s5 = contact.getText().toString();

                    if ((!isValidPassword(s4)) && (!isValidMobile(s5)) && (s1.equals("")) && (s2.equals("")) && (s3.equals(""))) {
                        password.setError("Minimum 4 AlphaNumeric Characters");
                        contact.setError("Invalid Number");
                        firstname.setError("Minimum 3 Characters");
                        lastname.setError("Minimum 3 Characters");
                        email.setError("Minimum 6 Characters");
                    }
                    else {

                        String url = "http://foodlabrinth.com/DSS_userRegistration.php?FirstName=" + s1 +
                                "&LastName=" + s2 + "&EmailId=" + s3 + "&Password=" + s4 + "&MobileNo=" + s5 + "";

                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(url).build();
                        Response response = client.newCall(request).execute();
                        //Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT).show();

                        Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                        Log.e("URL=", url);

                    }
                } catch (Exception e) {
                    isOnline();
                    Log.i("Error", e.toString());
                }
            }


        });

        return view;
    }

    private boolean isValidPassword(String s4) {
        if (s4 != null && s4.length() > 6) {
            return true;
        }
        return false;
    }

    private boolean isValidMobile(String s5) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", s5)) {
            if (s5.length() < 6 || s5.length() > 10) {
                // if(phone.length() != 10) {
                check = false;
                //Toast.makeText(getActivity(), "Please Enter valid mobile number", Toast.LENGTH_LONG).show();
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }


    public boolean isOnline() {
        ConnectivityManager connec =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            //Toast.makeText(getActivity(), " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            Snackbar.make(getActivity().findViewById(R.id.frame), "Not Connected To Internet", Snackbar.LENGTH_LONG)
                    .show();
            return false;
        }

        return connec.getActiveNetworkInfo() != null &&
                connec.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
