package com.doorstepservice.darpal.doorstepservice;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.doorstepservice.darpal.doorstepservice.Adapter.FeedsRecyclerAdapter;
import com.doorstepservice.darpal.doorstepservice.UserLogin.ServiceDispGetterSetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FeedsActivity extends AppCompatActivity {

    RecyclerView feedrv;
    TextView storename, storeadd, reviewname, reviews;
    ArrayList<ServiceDispGetterSetter> arrayList = new ArrayList<>();

    String SNAME = "Name";
    String SADD = "Address";
    String Rname = "FirstName";
    String REVIEW = "review";
    String CATEGORY = "Category";
    /*String SINK = "Services";
    String PRICE = "Price";*/
    public String sid;

    String URLDATA;
    FeedsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        feedrv = (RecyclerView) findViewById(R.id.feedrv);
        feedrv.setLayoutManager(new LinearLayoutManager(FeedsActivity.this));

        storename = (TextView) findViewById(R.id.resFeedName);
        storeadd = (TextView) findViewById(R.id.resAddFeed);
        reviewname = (TextView) findViewById(R.id.personFeedName);
        reviews = (TextView) findViewById(R.id.reviewFeed);

        URLDATA = "http://foodlabrinth.com//DSS_reviewFetch.php";
        URLDATA = URLDATA.replaceAll(" ", "%20");
        URLDATA = URLDATA.replaceAll("\n", "%20");
        Log.e("feeds", URLDATA);
        try {
            URL source = new URL(URLDATA);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        new Getdata().execute();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    class Getdata extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(FeedsActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                ServiceHandler sh = new ServiceHandler();
                String result = sh.GetHTTPData(URLDATA);
                if (result != null) {

                    Log.e("browse result", result);
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //sid = jsonObject.getString(SID);
                        String sname = jsonObject.getString(SNAME);
                        String saddress = jsonObject.getString(SADD);
                        String rname = jsonObject.getString(Rname);
                        String review = jsonObject.getString(REVIEW);
                        String category = jsonObject.getString(CATEGORY);
                        //String price = jsonObject.getString(PRICE);

                        ServiceDispGetterSetter serviceDispGetterSetter = new ServiceDispGetterSetter();
                        serviceDispGetterSetter.setStoreName(sname);
                        serviceDispGetterSetter.setStoreAddress(saddress);
                        serviceDispGetterSetter.setReviewer_name(rname);
                        serviceDispGetterSetter.setReviews(review);
                        serviceDispGetterSetter.setCategory(category);
                        //serviceDispGetterSetter.setSinkprice(price);
                        //serviceDispGetterSetter.setSid(sid);

                        arrayList.add(serviceDispGetterSetter);
                        //Log.e("Sid",sid);
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
            adapter = new FeedsRecyclerAdapter(FeedsActivity.this, arrayList);

            feedrv.setAdapter(adapter);

        }
    }
}
