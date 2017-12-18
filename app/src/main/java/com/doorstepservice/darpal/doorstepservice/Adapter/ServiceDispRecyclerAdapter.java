package com.doorstepservice.darpal.doorstepservice.Adapter;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doorstepservice.darpal.doorstepservice.ConfirmationPageActivity;
import com.doorstepservice.darpal.doorstepservice.R;
import com.doorstepservice.darpal.doorstepservice.ServiceDisplayActivity;
import com.doorstepservice.darpal.doorstepservice.UserLogin.ServiceDispGetterSetter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Darpal on 11/13/2017.
 */

public class ServiceDispRecyclerAdapter extends RecyclerView.Adapter<ServiceDispRecyclerAdapter.MyViewHolder> {

    ServiceDisplayActivity context;
    ArrayList<ServiceDispGetterSetter> arrayList = new ArrayList<>();
    String service, uid;


    public ServiceDispRecyclerAdapter(ServiceDisplayActivity serviceDisplayActivity, ArrayList<ServiceDispGetterSetter> arrayList) {
        this.context = serviceDisplayActivity;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_cell, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.storeName.setText(arrayList.get(position).getStoreName());
        holder.storeAdd.setText(arrayList.get(position).getStoreAddress());
        holder.contact.setText(arrayList.get(position).getContact());
        holder.sink.setText(arrayList.get(position).getSink().replace(", ", "\n"));
        holder.sinkprice.setText(arrayList.get(position).getSinkprice().replace(",", "\n"));

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView storeName, storeAdd, sink, sinkprice;
        Button contact;
        Context c;


        public MyViewHolder(View itemView) {
            super(itemView);

            storeName = (TextView) itemView.findViewById(R.id.storeDispName);
            storeAdd = (TextView) itemView.findViewById(R.id.title_to_address);
            sink = (TextView) itemView.findViewById(R.id.sink);
            sinkprice = (TextView) itemView.findViewById(R.id.sinkprice);
            contact = (Button) itemView.findViewById(R.id.btnCall);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(final View view) {
            //Toast.makeText(context, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            int pos = getAdapterPosition();
            final String sid = arrayList.get(pos).getSid();
            final String Contact = arrayList.get(pos).getContact();
            //Toast.makeText(context, "Store ID: " + sid, Toast.LENGTH_SHORT).show();

            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            c = itemView.getContext();
            /*final Dialog dialog = new Dialog(c);
            dialog.setContentView(R.layout.service_comment);
            dialog.setTitle("Comment Ur Service Below");
            dialog.show();*/
            //Toast.makeText(c, "contact number of vendor is" + Contact, Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder alert = new AlertDialog.Builder(c);
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view1 = inflater.inflate(R.layout.service_comment, (ViewGroup) view.findViewById(R.id.root));
            final EditText editText = (EditText) view1.findViewById(R.id.service_comment);
            alert.setCancelable(false);
            alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    service = editText.getText().toString();

                    //Toast.makeText(c, "service entered:" + service, Toast.LENGTH_SHORT).show();

                    SharedPreferences pref1 = context.getSharedPreferences("userpref", Context.MODE_PRIVATE);
                    uid = pref1.getString("uid", null);
//                    Log.d("ADAPTER UID", uid);//"No name defined" is the default value.
                    boolean b1 = pref1.getBoolean("login", false);


                    if (b1) {
                        try {
                            Random orderid = new Random();
                            int num = orderid.nextInt(900000) + 100000;

                            String url = "http://foodlabrinth.com/DSS_services.php?Service=" + service + "&Sid=" + sid + "&Uid=" + uid + "&Orderid=" + num;

                            url = url.replaceAll(" ", "%20");
                            url = url.replaceAll("\n", "%20");
                            URL source = new URL(url);
                            Log.e("urll==", url);

                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url(url).build();
                            client.newCall(request).execute();

                            //getting notification to user who booked a service
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                            builder.setSmallIcon(R.mipmap.logo);
                            builder.setContentTitle("Services you Booked are...");
                            builder.setContentText(service);
                            builder.setVibrate(new long[]{0, 1000});
                            builder.setLights(Color.RED, 3000, 3000);
                            NotificationManager mNotificationManager =
                                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            mNotificationManager.notify(001, builder.build());
                            context.requestPermissions();

                            //sms sending
                            sendMessage(Contact, service);



                            //LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);

                            //opening confirmation page
                            Intent intent = new Intent(context, ConfirmationPageActivity.class);
                            intent.putExtra("orderid",num);
                            context.startActivity(intent);


                            //Toast.makeText(c, "Confirmation ID is " + num, Toast.LENGTH_SHORT).show();
                            Log.e("Confirmation ID :", String.valueOf(num));

                            Toast.makeText(c, "Service(s) are Booked!", Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(view, "Please Login to Book a Service", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }


                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            alert.setView(view1);
            alert.show();

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
}
