package com.doorstepservice.darpal.doorstepservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.doorstepservice.darpal.doorstepservice.Adapter.CustomBaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class TypeOfServiceListActivity extends AppCompatActivity {

    ListView listView;
    List<RowItem> rowItems;
    String[] titles = {"Plumbing", "Cleaning", "Painting", "Local moving", "Electricity", "Gardening", "Gas", "Party Planning"};
    Integer[] images = {R.drawable.plumbing, R.drawable.cleaning, R.drawable.painting, R.drawable.localmoving,
            R.drawable.electricity, R.drawable.gardening, R.drawable.gas, R.drawable.party};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_service_list);

        Intent i1 = getIntent();
        final String search = i1.getStringExtra("zip");
        final String citysearch = i1.getStringExtra("city");
        //Toast.makeText(TypeOfServiceListActivity.this, "" + search, Toast.LENGTH_SHORT).show();


        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(images[i], titles[i]);
            rowItems.add(item);
        }

        listView = (ListView) findViewById(R.id.list);
        CustomBaseAdapter adapter = new CustomBaseAdapter(this, rowItems);
        listView.setAdapter(adapter);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String pos = listView.getItemAtPosition(position).toString();
                //Toast.makeText(TypeOfServiceListActivity.this, "" + String.valueOf(position), Toast.LENGTH_SHORT).show();


                if (position == 0) {
                    Intent intent1 = new Intent(TypeOfServiceListActivity.this, ServiceDisplayActivity.class);
                    String URL1 = "http://foodlabrinth.com/DSS_vendorsDisplay.php?Category=Plumbing"+"&Zip_Code="+search+"&Area="+citysearch;
                    Log.e("Plumbing", URL1);
                    intent1.putExtra("URL", URL1);

                    startActivity(intent1);
                }
//                Log.e("zipcode", zip);

                if (position == 1) {
                    Intent intent = new Intent(TypeOfServiceListActivity.this, ServiceDisplayActivity.class);
                    String URL1 = "http://foodlabrinth.com/DSS_vendorsDisplay.php?Category=Cleaning"+"&Zip_Code="+search+"&Area="+citysearch;
                    Log.e("Cleaning", URL1);
                    intent.putExtra("URL", URL1);
                    startActivity(intent);
                }

                if (position == 2) {
                    Intent intent = new Intent(TypeOfServiceListActivity.this, ServiceDisplayActivity.class);
                    String URL1 = "http://foodlabrinth.com/DSS_vendorsDisplay.php?Category=Painting"+"&Zip_Code="+search+"&Area="+citysearch;
                    Log.e("Painting", URL1);
                    intent.putExtra("URL", URL1);
                    startActivity(intent);
                }

                if (position == 3) {
                    Intent intent = new Intent(TypeOfServiceListActivity.this, ServiceDisplayActivity.class);
                    String URL1 = "http://foodlabrinth.com/DSS_vendorsDisplay.php?Category=Local%20Moving"+"&Zip_Code="+search+"&Area="+citysearch;
                    Log.e("Local Moving", URL1);
                    intent.putExtra("URL", URL1);
                    startActivity(intent);
                }

                if (position == 4) {
                    Intent intent = new Intent(TypeOfServiceListActivity.this, ServiceDisplayActivity.class);
                    String URL1 = "http://foodlabrinth.com/DSS_vendorsDisplay.php?Category=Electricity"+"&Zip_Code="+search+"&Area="+citysearch;
                    Log.e("Electricity", URL1);
                    intent.putExtra("URL", URL1);
                    startActivity(intent);
                }

                if (position == 5) {
                    Intent intent = new Intent(TypeOfServiceListActivity.this, ServiceDisplayActivity.class);
                    String URL1 = "http://foodlabrinth.com/DSS_vendorsDisplay.php?Category=Gardening"+"&Zip_Code="+search+"&Area="+citysearch;
                    Log.e("Gardening", URL1);
                    intent.putExtra("URL", URL1);
                    startActivity(intent);
                }

                if (position == 6) {
                    Intent intent = new Intent(TypeOfServiceListActivity.this, ServiceDisplayActivity.class);
                    String URL1 = "http://foodlabrinth.com/DSS_vendorsDisplay.php?Category=Gas%20Services"+"&Zip_Code="+search+"&Area="+citysearch;
                    Log.e("Gas Service", URL1);
                    intent.putExtra("URL", URL1);
                    startActivity(intent);
                }

                if (position == 7) {
                    Intent intent = new Intent(TypeOfServiceListActivity.this, ServiceDisplayActivity.class);
                    String URL1 = "http://foodlabrinth.com/DSS_vendorsDisplay.php?Category=Party%20Planning"+"&Zip_Code="+search+"&Area="+citysearch;
                    Log.e("Party Planning", URL1);
                    intent.putExtra("URL", URL1);
                    startActivity(intent);
                }


            }


        });


    }
}
