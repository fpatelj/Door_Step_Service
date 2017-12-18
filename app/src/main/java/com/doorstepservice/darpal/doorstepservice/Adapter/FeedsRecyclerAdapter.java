package com.doorstepservice.darpal.doorstepservice.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doorstepservice.darpal.doorstepservice.FeedsActivity;
import com.doorstepservice.darpal.doorstepservice.R;
import com.doorstepservice.darpal.doorstepservice.UserLogin.ServiceDispGetterSetter;

import java.util.ArrayList;

/**
 * Created by Darpal on 12/13/2017.
 */

public class FeedsRecyclerAdapter extends RecyclerView.Adapter<FeedsRecyclerAdapter.ViewHolder> {

    FeedsActivity context;
    ArrayList<ServiceDispGetterSetter> arrayList = new ArrayList<>();
    String service, uid;

    public FeedsRecyclerAdapter(FeedsActivity feedsActivity, ArrayList<ServiceDispGetterSetter> arrayList) {
        this.context=feedsActivity;
        this.arrayList=arrayList;
    }

    @Override
    public FeedsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feed_cell, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedsRecyclerAdapter.ViewHolder holder, int position) {
        holder.storeName.setText(arrayList.get(position).getStoreName());
        holder.storeAdd.setText(arrayList.get(position).getStoreAddress());
        holder.reviewname.setText(arrayList.get(position).getReviewer_name());
        holder.reviews.setText(arrayList.get(position).getReviews());
        holder.category.setText(arrayList.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView storeName, storeAdd, reviewname, reviews, category;
        Context c;

        public ViewHolder(View itemView) {
            super(itemView);

            storeName = (TextView) itemView.findViewById(R.id.resFeedName);
            storeAdd = (TextView) itemView.findViewById(R.id.resAddFeed);
            reviewname = (TextView) itemView.findViewById(R.id.personFeedName);
            reviews = (TextView) itemView.findViewById(R.id.reviewFeed);
            category = (TextView) itemView.findViewById(R.id.categoryFeed);

        }
    }
}
