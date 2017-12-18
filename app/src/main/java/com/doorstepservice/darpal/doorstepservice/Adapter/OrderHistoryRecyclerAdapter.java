package com.doorstepservice.darpal.doorstepservice.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doorstepservice.darpal.doorstepservice.OrderHistoryActivity;
import com.doorstepservice.darpal.doorstepservice.R;
import com.doorstepservice.darpal.doorstepservice.UserLogin.ServiceDispGetterSetter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Darpal on 12/9/2017.
 */

public class OrderHistoryRecyclerAdapter extends RecyclerView.Adapter<OrderHistoryRecyclerAdapter.MyVH> {

    OrderHistoryActivity context;
    ArrayList<ServiceDispGetterSetter> arrayList = new ArrayList<>();
    String stars, reviews, uid;


    public OrderHistoryRecyclerAdapter(OrderHistoryActivity orderHistoryActivity, ArrayList<ServiceDispGetterSetter> arrayList) {
        this.context = orderHistoryActivity;
        this.arrayList = arrayList;
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_cell, parent, false);
        return new MyVH(view);
    }

    @Override
    public void onBindViewHolder(MyVH holder, int position) {
        holder.service.setText(arrayList.get(position).getService());
        holder.orderid.setText(arrayList.get(position).getOrderid());
        holder.storename.setText(arrayList.get(position).getStoreName());
        holder.storeadd.setText(arrayList.get(position).getStoreAddress());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyVH extends RecyclerView.ViewHolder {

        TextView service, orderid, storename, storeadd;
        Button rate;

        public MyVH(View itemView) {
            super(itemView);

            service = (TextView) itemView.findViewById(R.id.service_booked);
            orderid = (TextView) itemView.findViewById(R.id.orderid);
            storename = (TextView) itemView.findViewById(R.id.storeDispName);
            storeadd = (TextView) itemView.findViewById(R.id.title_to_address);
            rate = (Button) itemView.findViewById(R.id.rate);

            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    final String sid = arrayList.get(pos).getSid();
                    final String Contact = arrayList.get(pos).getContact();

                    SharedPreferences pref1 = context.getSharedPreferences("userpref", Context.MODE_PRIVATE);
                    uid = pref1.getString("uid", null);

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View view1 = inflater.inflate(R.layout.rating_cell, (ViewGroup) view.findViewById(R.id.root));
                    final EditText reviewet = (EditText) view1.findViewById(R.id.review);
                    RatingBar ratingBar = (RatingBar) view1.findViewById(R.id.ratingBar);

                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            stars = String.valueOf(rating);
                        }
                    });
                    alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            reviews = reviewet.getText().toString();

                            try {

                                String url = "http://foodlabrinth.com/DSS_rating.php?review=" + reviews + "&Sid=" + sid + "&rating=" + stars + "&uid=" + uid;

                                url = url.replaceAll(" ", "%20");
                                url = url.replaceAll("\n", "%20");
                                URL source = new URL(url);
                                Log.e("urll==", url);

                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder().url(url).build();
                                client.newCall(request).execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(context, "Review submitted ", Toast.LENGTH_SHORT).show();

                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setView(view1);
                    alert.show();

                    //Toast.makeText(context, "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
                }


            });
        }

    }
}
