package com.example.ecommerceproject.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProductActivity extends AppCompatActivity
{
    private Toolbar toolbar5;

    private TextView tvPName, tvPPrice, tvPDesc, tvSName,tvStock;
    private ImageView productIV;
    private Button button2,button;

    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private String user_id;
    private String email_id;
    private ElegantNumberButton numberButton ;
    private LottieAnimationView wichlist;

    private String product_key;
    private String product_name;
    private String product_price;
    private String product_description;
    private String seller_name;
    private String stock;
    private String category;
    private String product_image;
    private String product_category;
    private LinearLayout similar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        toolbar5 = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar5);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar5.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar5.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        wichlist =  findViewById(R.id.add_to_wishlist);
        similar = findViewById(R.id.similar_id);

        tvPName = findViewById(R.id.tvPName);
        tvPPrice = findViewById(R.id.tvPPrice);
        tvPDesc = findViewById(R.id.tvPDesc);
        tvSName = findViewById(R.id.tvSName);
        productIV = findViewById(R.id.productIV);
        tvStock = findViewById(R.id.tvStock);
        button2 = findViewById(R.id.button2);
        button = findViewById(R.id.button);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Bundle b = getIntent().getExtras();
        product_key = b.get("product_key").toString();
        product_category = b.get("product_category").toString();
        //product_key = b.get("product_key").toString();

        if (b != null) {
            getUserDetails();
            mDatabase.child("products").child(product_category).child(product_key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    product_description = dataSnapshot.child("product_description").getValue().toString();
                    product_name = dataSnapshot.child("product_name").getValue().toString();
                    product_price = dataSnapshot.child("product_price").getValue().toString();
                    product_image = dataSnapshot.child("product_image").getValue().toString();
                    seller_name = dataSnapshot.child("company_name").getValue().toString();
                    stock = dataSnapshot.child("quantity_stock").getValue().toString();
                    category = dataSnapshot.child("product_category").getValue().toString();

                    showProduct();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productIntent = new Intent(ProductActivity.this, AddDeliveryDetails.class);
                    //productIntent.putExtra("product_key", );
                    productIntent.putExtra("product_name2", product_name);
                    productIntent.putExtra("product_price2", product_price);
                    productIntent.putExtra("product_image2", product_image);
                    productIntent.putExtra("product_description2", product_description);
                    productIntent.putExtra("seller_name2", seller_name);
                    productIntent.putExtra("product_category2", category);
                    productIntent.putExtra("product_key2", product_key);
                    productIntent.putExtra("quantity2",  numberButton.getNumber());
                    productIntent.putExtra("from_cart", "no");
                    startActivity(productIntent);
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveProductToCart();
                }
            });

            wichlist.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    saveToWishlist();
                    wichlist.playAnimation();
                }
            });

            similar.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent productIntent = new Intent(ProductActivity.this, ProductMainSubListActivity.class);
                    productIntent.putExtra("product_category", product_category);
                    startActivity(productIntent);
                }
            });
        }
    }

    private void saveToWishlist()
    {
        String cart_key = mDatabase.child("wishList").child(user_id).push().getKey();
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("product_name", product_name);
        dataMap.put("product_price", product_price);
        dataMap.put("product_image", product_image);
        dataMap.put("seller_name", seller_name);
        dataMap.put("product_key", product_key);
        dataMap.put("product_category", product_category);
        dataMap.put("cart_key", cart_key);
        dataMap.put("product_description", product_description);
        mDatabase.child("wishList").child(user_id).child(cart_key).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Product added successfully to wish list", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void saveProductToCart() {

        String cart_key = mDatabase.child("cart").child(user_id).push().getKey();
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("product_name", product_name);
        dataMap.put("product_price", product_price);
        dataMap.put("product_image", product_image);
        dataMap.put("seller_name", seller_name);
        dataMap.put("product_key", product_key);
        dataMap.put("cart_key", cart_key);
        dataMap.put("product_category", product_category);
        dataMap.put("product_description", product_description);
        dataMap.put("quantity", numberButton.getNumber());
        mDatabase.child("cart").child(user_id).child(cart_key).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Product added successfully to cart", Toast.LENGTH_LONG).show();
                Intent cartIntent = new Intent(ProductActivity.this, CartActivity.class);
                startActivity(cartIntent);
                finish();
            }
        });
    }

    private void getUserDetails() {
        user_id = Prevalent.currentOnlineUser.getPhone();
        mDatabase.child("Users").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                email_id = dataSnapshot.child("phone").getValue().toString();
                //showProduct();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showProduct() {
        Picasso.get().load(product_image).into(productIV);
        toolbar5.setTitle(product_name);
        tvPName.setText(product_name);
        tvSName.setText("by " + seller_name);
        tvPPrice.setText( product_price);
        tvPDesc.setText(product_description);
        tvStock.setText(stock);
    }
}
