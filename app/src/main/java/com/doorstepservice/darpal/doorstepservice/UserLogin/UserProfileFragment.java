package com.doorstepservice.darpal.doorstepservice.UserLogin;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.doorstepservice.darpal.doorstepservice.AboutUsActivity;
import com.doorstepservice.darpal.doorstepservice.CancelOrderActivity;
import com.doorstepservice.darpal.doorstepservice.OrderHistoryActivity;
import com.doorstepservice.darpal.doorstepservice.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    Button history, cancel, share, feedback, about, rate, logout;
    TextView username;

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        final SharedPreferences pref = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        history = (Button) view.findViewById(R.id.history);
        cancel = (Button) view.findViewById(R.id.cancel);
        share = (Button) view.findViewById(R.id.share);
        feedback = (Button) view.findViewById(R.id.feedback);
        about = (Button) view.findViewById(R.id.about);
        rate = (Button) view.findViewById(R.id.rate);
        logout = (Button) view.findViewById(R.id.btnLogout);
        username = (TextView) view.findViewById(R.id.tvuname);

        String s1 = pref.getString("email", null);
        username.setText("Welcome, " + s1);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.edit().clear().commit();
                UserLoginFragment userLoginFragment = new UserLoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame, userLoginFragment).commit();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CancelOrderActivity.class);
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/email");
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ getString(R.string.mail_feedback_email) });
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback_subject));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.mail_feedback_message));
                startActivity(Intent.createChooser(intent, getString(R.string.title_send_feedback)));
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Door Step Service coming soon!! ;)");
                startActivity(Intent.createChooser(shareIntent, "Share link using..."));
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
            }
        });






        return view;
    }

}
