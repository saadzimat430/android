package com.example.ecommerceproject.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CartBuyActivity extends AppCompatActivity
{

    private Toolbar toolbar13;
    private TextView tvCBName, tvCBAddress, tvCBTItems, tvCBTPrice;
    private Button button11;

    private DatabaseReference mDatabase;
    private String currentUser;
    private FirebaseAuth mAuth;

    private String name1;
    private String phone1;
    private String address1;
    private String total_price;
    private String cart_key1;
    private String total_product_count;
    private String stock = "0";
    private String user_id;
    private String key;
    private String product_key;
    private String pStock;
    private String pcategory;
    private String quantity="0";
    private int cost = 0;
    private String productRandomKey;
    private int total_product_count1 = 0;
    private ProgressDialog pd;


    @SuppressWarnings("unchecked")
    public void myMethod()
    {
        //...
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_buy);


        Bundle bundle = getIntent().getExtras();
        name1 = bundle.get("name").toString();
        address1 = bundle.get("address").toString();
        phone1 = bundle.get("phone").toString();
        total_price = bundle.get("total_price").toString();
        total_product_count = bundle.get("total_product_count").toString();
        cart_key1 = bundle.get("cart_key").toString();

        toolbar13 = findViewById(R.id.toolbar13);
        toolbar13.setTitle("Place order");
        setSupportActionBar(toolbar13);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar13.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar13.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvCBName = findViewById(R.id.tvCBName);
        tvCBAddress = findViewById(R.id.tvCBAddress);
        tvCBTItems = findViewById(R.id.tvCBTItems);
        tvCBTPrice = findViewById(R.id.tvCBTPrice);
        button11 = findViewById(R.id.button11);

        mDatabase = FirebaseDatabase.getInstance().getReference();
       // mAuth = FirebaseAuth.getInstance();
        currentUser = Prevalent.currentOnlineUser.getPhone();
        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");

        if (currentUser != null) {
            user_id = Prevalent.currentOnlineUser.getPhone();
            button11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pd.show();
                    getProductShipToOrders();
                    //fetchQuantity();

                }
            });

        } else {
            sendToLogin();
        }

        tvCBName.setText(name1);
        tvCBAddress.setText(address1);
        tvCBTItems.setText(total_product_count);
        tvCBTPrice.setText("MAD  " + total_price);


    }


    private void getProductShipToOrders()
    {

        final String saveCurrentTime, saveCurrentDate;
        Calendar calForeDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForeDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForeDate.getTime());
        productRandomKey = saveCurrentDate + saveCurrentTime;

        mDatabase.child("cart").child(user_id).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object producut_name = map.get("product_name");
                    Object product_image = map.get("product_image");
                    Object seller_name = map.get("seller_name");
                    Object cart_key = map.get("cart_key");
                    Object product_description = map.get("product_description");
                    Object product_price = map.get("product_price");
                    map.put("state", "not shipped");

                    mDatabase.child("orders").child(user_id).child("products").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        nextStep();
        mDatabase.child("cart").child(user_id).setValue(null);
        finish();

    }

    private void nextStep()
    {
        final String saveCurrentTime, saveCurrentDate;
        Calendar calForeDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForeDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForeDate.getTime());

        //getTotalProductCount();

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", name1);
        dataMap.put("address", address1);
        dataMap.put("phone", phone1);
        dataMap.put("user_id", user_id);
        dataMap.put("state", "not shipped");
        dataMap.put("date", saveCurrentDate);
        dataMap.put("time", saveCurrentTime);
       // dataMap.put("total_product_count", total_product_count1);
        dataMap.put("total_price", total_price);
        mDatabase.child("orders").child(user_id).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //pd.dismiss();
                Toast.makeText(getApplicationContext(), "Product purchased successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CartBuyActivity.this, OrderPlacedActivity.class);
                intent.putExtra("name",name1);
                //pd.dismiss();
                startActivity(intent);
                finish();

            }
        });

        //fetchQuantity();
    }

    private void fetchQuantity()
    {
        mDatabase.child("orders").child(user_id).child("products").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                   if (dataSnapshot.exists())
                   {
                       product_key =  ds.child("product_key").getValue().toString();
                       pcategory = ds.child("product_category").getValue().toString();
                       quantity = ds.child("quantity").getValue().toString();
                       fetchCompanyDetails(product_key, quantity, pcategory);


                       //Toast.makeText(CartBuyActivity.this, "product key " +product_key + " quantity "+ quantity + " category "+ pcategory, Toast.LENGTH_SHORT).show();
                   }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchCompanyDetails(final String product_key, final String quantity , final String pcategory)
    {

        mDatabase.child("business").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    key = ds.getKey();

                }
                stock_update(product_key,quantity, pcategory);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        saveStockResualt(pcategory, product_key);
    }

    private void stock_update(final String product_key, final String quantity , final String pcategory)
    {
        mDatabase.child("business").child(key).child("products").child(product_key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                String ppStock = dataSnapshot.child("quantity_stock").getValue().toString();
                pStock = ppStock;
                cost = (Integer.valueOf(pStock)) - (Integer.valueOf(quantity));
                stock = String.valueOf(cost);

                //Toast.makeText(CartBuyActivity.this, "oihsdnkjnkjsd   "+ stock, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

//        mDatabase.child("business").child(key).child("products").child(product_key).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//
//                if (dataSnapshot.exists())
//                {
//                    String pStock = dataSnapshot.child("quantity_stock").getValue().toString();
//                    //Toast.makeText(SellerUseProductsActivity.this, "oihsdnkjnkjsd   "+ cost, Toast.LENGTH_SHORT).show();
//                    cost = (Integer.valueOf(pStock))-(Integer.valueOf(quantity));
//                    stock =  String.valueOf(cost) ;
//                    Toast.makeText(SellerUseProductsActivity.this, "oihsdnkjnkjsd   "+ stock, Toast.LENGTH_SHORT).show();
//                    saveStockResualt(key,product_key,category, stock);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError)
//            {
//
//            }
//        });
    }

    private void saveStockResualt(String pcategory , String product_key)
    {


        //Toast.makeText(this, "coooooooooooooooct     " + stock, Toast.LENGTH_SHORT).show();
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("quantity_stock", stock);

        //update the values of stock
        mDatabase.child("business").child(key).child("products").child(product_key).updateChildren(productMap);
        mDatabase.child("products").child(pcategory).child(product_key).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {

                    Toast.makeText(getApplicationContext(), "Done successfully", Toast.LENGTH_LONG).show();

                }
                else {
                    pd.dismiss();
                    Toast.makeText(CartBuyActivity.this, "error.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(CartBuyActivity.this, MainActivity.class);
        startActivity(loginIntent);
        finish();
    }

}
