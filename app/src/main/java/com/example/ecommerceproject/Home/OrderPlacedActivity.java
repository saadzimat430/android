package com.example.ecommerceproject.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerceproject.R;
import com.example.ecommerceproject.Sellers.Main2Activity;

public class OrderPlacedActivity extends AppCompatActivity
{
    private ImageView finish ;
    private String name = "";
    private TextView congra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        finish = findViewById(R.id.finish_is);
        congra = findViewById(R.id.congra_id);


        Bundle b = getIntent().getExtras();
        name = b.get("name").toString();
        congra.setText("Congratulations " +name+ ", your final order has bees placed successfully. Soon it will be verified.");
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderPlacedActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


    }
}
