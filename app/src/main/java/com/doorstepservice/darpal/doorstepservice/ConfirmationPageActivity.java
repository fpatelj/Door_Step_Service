package com.doorstepservice.darpal.doorstepservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmationPageActivity extends AppCompatActivity {

    int orderid;
    Button history, rate;
    TextView another;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);

        history = (Button) findViewById(R.id.mybookingsbtn);
        another = (TextView) findViewById(R.id.bookanotherbtn);
        rate = (Button) findViewById(R.id.reviewbtn);

        Bundle bundle = getIntent().getExtras();
        orderid =  bundle.getInt("orderid", 0);
        TextView order = (TextView) findViewById(R.id.orderid);
        order.setText(String.valueOf(orderid));

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmationPageActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmationPageActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            }
        });

        another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmationPageActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        //Toast.makeText(this, "orderid :" + orderid, Toast.LENGTH_SHORT).show();
    }
}
