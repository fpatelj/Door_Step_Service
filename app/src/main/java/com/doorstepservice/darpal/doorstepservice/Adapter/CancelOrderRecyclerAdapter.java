package com.doorstepservice.darpal.doorstepservice.Adapter;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.doorstepservice.darpal.doorstepservice.CancelOrderActivity;
import com.doorstepservice.darpal.doorstepservice.R;
import com.doorstepservice.darpal.doorstepservice.ServiceHandler;
import com.doorstepservice.darpal.doorstepservice.UserLogin.ServiceDispGetterSetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Darpal on 12/10/2017.
 */

public class CancelOrderRecyclerAdapter extends RecyclerView.Adapter<CancelOrderRecyclerAdapter.VH> {

    CancelOrderActivity context;
    ArrayList<ServiceDispGetterSetter> arrayList = new ArrayList<>();
    String URLCANCEL;
    String SERVICE = "Service";
    String ORDERID = "Orderid";
    String SID = "Sid";
    String SNAME = "Name";
    String SADD = "Address";
    String SCONTACT = "Contact_no";

    public CancelOrderRecyclerAdapter(CancelOrderActivity cancelOrderActivity, ArrayList<ServiceDispGetterSetter> arrayList) {
        this.context = cancelOrderActivity;
        this.arrayList = arrayList;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cancel_cell, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {

        holder.service.setText(arrayList.get(position).getService());
        holder.orderid.setText(arrayList.get(position).getOrderid());
        holder.storename.setText(arrayList.get(position).getStoreName());
        holder.storeadd.setText(arrayList.get(position).getStoreAddress());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        TextView service, orderid, storename, storeadd;
        Button cancel;

        public VH(View itemView) {
            super(itemView);

            service = (TextView) itemView.findViewById(R.id.service_booked);
            this.orderid = (TextView) itemView.findViewById(R.id.orderid);
            storename = (TextView) itemView.findViewById(R.id.storeDispName);
            storeadd = (TextView) itemView.findViewById(R.id.title_to_address);
            cancel = (Button) itemView.findViewById(R.id.cancelbtn);

            cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    String serv = service.getText().toString();
                    String cancelid = orderid.getText().toString();
                    final String Contact = arrayList.get(pos).getContact();
//                    Toast.makeText(context, "" + cancelid, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context, "" + getPosition(), Toast.LENGTH_SHORT).show();
                    arrayList.remove(getPosition());


                    URLCANCEL = "http://foodlabrinth.com/DSS_cancel.php?Orderid=" + cancelid;
                    URLCANCEL = URLCANCEL.replaceAll(" ", "%20");
                    URLCANCEL = URLCANCEL.replaceAll("\n", "%20");
                    Log.e("CancelURL:", URLCANCEL);

                    //getting notification to user who booked a service
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    builder.setSmallIcon(R.mipmap.logo);
                    builder.setContentTitle("Your service has been canceled!");
                    builder.setContentText(serv);
                    builder.setVibrate(new long[]{0, 1000});
                    builder.setLights(Color.RED, 3000, 3000);
                    NotificationManager mNotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(001, builder.build());
                    context.requestPermissions();

                    //sms sending
                    sendMessage(Contact, serv);

                    try {
                        URL source = new URL(URLCANCEL);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    new Getdata().execute();
                }
            });
        }

        class Getdata extends AsyncTask<Void, Void, Void> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    ServiceHandler sh = new ServiceHandler();
                    String result = sh.GetHTTPData(URLCANCEL);
                    if (result != null) {

                        Log.e("cancel result", result);
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //sid = jsonObject.getString(SID);
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
                            //serviceDispGetterSetter.setSid(sid);

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

                context.adapter = new CancelOrderRecyclerAdapter(context,arrayList);
                context.recyclerView.setAdapter(context.adapter);
            }
        }
    }


    public void removeAt(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }

    private void sendMessage(String phoneNo, String message) {
        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            //Toast.makeText(context, "SMS Sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            //Toast.makeText(context, "Failed. Please try again!", Toast.LENGTH_LONG).show();
            Log.e("SMS Fail", String.valueOf(e));
        }

    }
}
