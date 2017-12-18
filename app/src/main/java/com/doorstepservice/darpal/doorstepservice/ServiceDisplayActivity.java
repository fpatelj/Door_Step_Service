package com.doorstepservice.darpal.doorstepservice;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.doorstepservice.darpal.doorstepservice.Adapter.ServiceDispRecyclerAdapter;
import com.doorstepservice.darpal.doorstepservice.UserLogin.ServiceDispGetterSetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ServiceDisplayActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView storeName, storeAddress, sink, sinkprice;
    Button call;
    String URLDATA;


    String SNAME = "Name";
    String SADD = "Address";
    String SCONTACT = "Contact_no";
    String SID = "Sid";
    String SINK = "Services";
    String PRICE = "Price";
    public String sid;

    ArrayList<ServiceDispGetterSetter> arrayList = new ArrayList<>();
    ServiceDispRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_display);

        //URLDATA = intent.getStringExtra("URL");
        //Log.e("URL---", URLDATA);
        Intent intent = getIntent();
        URLDATA = intent.getStringExtra("URL");
        new Getdata().execute();
        //listView = (ListView) findViewById(R.id.servicelist);
        recyclerView = (RecyclerView) findViewById(R.id.serviceDisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(ServiceDisplayActivity.this));


        storeName = (TextView) findViewById(R.id.storeDispName);
        storeAddress = (TextView) findViewById(R.id.title_to_address);
        sink = (TextView) findViewById(R.id.sink);
        sinkprice = (TextView) findViewById(R.id.sinkprice);

        call = (Button) findViewById(R.id.btnCall);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mapview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.map) {
            Intent intent = new Intent(ServiceDisplayActivity.this, com.doorstepservice.darpal.doorstepservice.VendorsMapActivity.class);
            startActivity(intent);
            // do something here
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestPermissions() {
        final int PERMISSION_REQUEST_CODE = 1;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }
    }


    class Getdata extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ServiceDisplayActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                com.doorstepservice.darpal.doorstepservice.ServiceHandler sh = new com.doorstepservice.darpal.doorstepservice.ServiceHandler();
                String result = sh.GetHTTPData(URLDATA);
                if (result != null) {

                    Log.e("browse result", result);
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        sid = jsonObject.getString(SID);
                        String sname = jsonObject.getString(SNAME);
                        String saddress = jsonObject.getString(SADD);
                        String contact = jsonObject.getString(SCONTACT);
                        String sink = jsonObject.getString(SINK);
                        String price = jsonObject.getString(PRICE);

                        ServiceDispGetterSetter serviceDispGetterSetter = new ServiceDispGetterSetter();
                        serviceDispGetterSetter.setStoreName(sname);
                        serviceDispGetterSetter.setStoreAddress(saddress);
                        serviceDispGetterSetter.setContact(contact);
                        serviceDispGetterSetter.setSink(sink);
                        serviceDispGetterSetter.setSinkprice(price);
                        serviceDispGetterSetter.setSid(sid);

                        arrayList.add(serviceDispGetterSetter);
                        Log.e("Sid",sid);
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
            adapter = new ServiceDispRecyclerAdapter(ServiceDisplayActivity.this, arrayList);

            recyclerView.setAdapter(adapter);

        }
    }
}
