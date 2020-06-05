package com.example.ecommerceproject.Sellers;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SellerNewOrdersActivity extends AppCompatActivity {

    private RecyclerView orderList;
    private DatabaseReference ordersRef, productsordersRef;
    private String userShippingAddress = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_check_orders);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("orders");
        productsordersRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        Toolbar toolbar = findViewById(R.id.orders_toolbar1);
        setSupportActionBar(toolbar);

        orderList = findViewById(R.id.order_list1);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }





    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView username, userPhoneNumber, userTotalPrice, userDateTime,state, userShippingAddress,showProduct,userProcductPrice,userProcductQuantity;
        public LinearLayout showOrdersBtn;
        public ImageView imageView;
        public LinearLayout imgcall, location;

        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.order_user_name1);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number1);
            userTotalPrice = itemView.findViewById(R.id.order_total_price1);
            userDateTime = itemView.findViewById(R.id.order_date_time1);
            userShippingAddress = itemView.findViewById(R.id.order_address_city1);
            showOrdersBtn = itemView.findViewById(R.id.show_all_products_btn1);
            imgcall = itemView.findViewById(R.id.img_call);
            imageView = itemView.findViewById(R.id.imageView_id);
            location = itemView.findViewById(R.id.location_id);
            showProduct = itemView.findViewById(R.id.show_product_id);


        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
         if (id == R.id.notification_id)
        {
            Toast.makeText(getApplicationContext(), "notifications", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}


