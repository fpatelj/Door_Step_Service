package com.doorstepservice.darpal.doorstepservice.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doorstepservice.darpal.doorstepservice.R;
import com.doorstepservice.darpal.doorstepservice.RowItem;

import java.util.List;

/**
 * Created by Darpal on 11/2/2017.
 */

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItems;

    public CustomBaseAdapter(Context context, List<RowItem> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_cell, null);
            holder = new ViewHolder();

            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        RowItem rowItem = (RowItem) getItem(position);

        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());

        return convertView;
    }
}
