package com.doorstepservice.darpal.doorstepservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.doorstepservice.darpal.doorstepservice.Adapter.OrderHistoryRecyclerAdapter;
import com.doorstepservice.darpal.doorstepservice.UserLogin.ServiceDispGetterSetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    String uid, URLHIST;
    String SERVICE = "Service";
    String ORDERID = "Orderid";
    String SID = "Sid";
    String SNAME = "Name";
    String SADD = "Address";
    String SCONTACT = "Contact_no";

    String sid;
    RecyclerView recyclerView;
    TextView storeName, storeAddress,orderid,service;
    ArrayList<ServiceDispGetterSetter> arrayList = new ArrayList<>();
    OrderHistoryRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        storeName = (TextView) findViewById(R.id.storeDispName);
        storeAddress = (TextView) findViewById(R.id.title_to_address);
        orderid = (TextView) findViewById(R.id.orderid);
        service = (TextView) findViewById(R.id.service_booked);
        recyclerView = (RecyclerView) findViewById(R.id.historydisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderHistoryActivity.this));

        SharedPreferences pref = getSharedPreferences("userpref", Context.MODE_PRIVATE);
        uid = pref.getString("uid", null);

        URLHIST = "http://foodlabrinth.com//DSS_history.php?Uid=" + uid;
        URLHIST = URLHIST.replaceAll(" ", "%20");
        URLHIST = URLHIST.replaceAll("\n", "%20");
        Log.e("History", URLHIST);
        try {
            URL source = new URL(URLHIST);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        new Getdata().execute();
    }


    class Getdata extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(OrderHistoryActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                ServiceHandler sh = new ServiceHandler();
                String result = sh.GetHTTPData(URLHIST);
                if (result != null) {

                    Log.e("browse result", result);
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        sid = jsonObject.getString(SID);
                        String sname = jsonObject.getString(SNAME);
                        String saddress = jsonObject.getString(SADD);
                        String contact = jsonObject.getString(SCONTACT);
                        String service = jsonObject.getString(SERVICE);
                        String order = jsonObject.getString(ORDERID);

                        ServiceDispGetterSetter serviceDispGetterSetter = new ServiceDispGetterSetter();
                        serviceDispGetterSetter.setStoreName(sname);
                        serviceDispGetterSetter.setStoreAddress(saddress);
                        serviceDispGetterSetter.setContact(contact);
                        serviceDispGetterSetter.setService(service);
                        serviceDispGetterSetter.setOrderid(order);
                        serviceDispGetterSetter.setSid(sid);

                        arrayList.add(serviceDispGetterSetter);
                    }
                }

                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();

            adapter = new OrderHistoryRecyclerAdapter(OrderHistoryActivity.this,arrayList);
            recyclerView.setAdapter(adapter);
        }
    }
}
