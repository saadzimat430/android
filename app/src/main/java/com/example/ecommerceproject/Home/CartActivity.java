package com.example.ecommerceproject.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ecommerceproject.Home.Adapters.CartAdapter;
import com.example.ecommerceproject.Home.Models.Cart;
import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity
{
    private Toolbar toolbar6;

    private TextView textView, tvPrice;
    private Button button7;
    private CardView cardView3;

    private RecyclerView cartRV;
    private List<Cart> cartList = new ArrayList<>();
    private Context mContext;
    private CartAdapter cartAdapter;

    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private String user_id , cart_key;
    private int total_price = 0;
    private String p_price;
    private int total_product_count = 0;
    private LottieAnimationView emptycart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);

        mContext = CartActivity.this;

        toolbar6 = findViewById(R.id.toolbar6);
        toolbar6.setTitle("Your cart");
        setSupportActionBar(toolbar6);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar6.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar6.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        emptycart = findViewById(R.id.empty_notification1);
        cartRV = findViewById(R.id.cartRV);
        cartAdapter = new CartAdapter(cartList, mContext);
        cartRV.setAdapter(cartAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        cartRV.setLayoutManager(layoutManager);

        textView = findViewById(R.id.textView);
        tvPrice = findViewById(R.id.tvPrice);
        button7 = findViewById(R.id.button7);
        cardView3 = findViewById(R.id.cardView3);


        mDatabase = FirebaseDatabase.getInstance().getReference();

            user_id = Prevalent.currentOnlineUser.getPhone();
            getCartDetails();
            getTotalPrice();
            getTotalProductCount();
            button7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent buyIntent = new Intent(CartActivity.this, AddDeliveryDetails.class);
                    buyIntent.putExtra("total_price", String.valueOf(total_price));
                    buyIntent.putExtra("cart_key", String.valueOf(total_price));
                    buyIntent.putExtra("total_product_count", String.valueOf(total_product_count));
                    buyIntent.putExtra("cart_key", String.valueOf(cart_key));
                    buyIntent.putExtra("from_cart", "yes");
                    startActivity(buyIntent);
                }
            });

        }



    private void getTotalProductCount() {
        mDatabase.child("cart").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Cart cart = ds.getValue(Cart.class);
                    Integer cost = (Integer.valueOf(cart.getQuantity()));
                    cart_key = String.valueOf(cart.getCart_key());
                    total_product_count = total_product_count + cost;
                }
//                total_product_count = (int) dataSnapshot.getChildrenCount();
                //Toast.makeText(getApplicationContext(), String.valueOf(total_product_count), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getTotalPrice() {
        mDatabase.child("cart").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Cart cart = ds.getValue(Cart.class);
                        int cost = (Integer.valueOf(cart.getProduct_price()))*(Integer.valueOf(cart.getQuantity()));
                        cart_key = String.valueOf(cart.getCart_key());
                        total_price = total_price + cost;
                    }
                    tvPrice.setText(String.valueOf("MAD " + total_price));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getCartDetails() {
        mDatabase.child("cart").child(user_id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    textView.setVisibility(View.INVISIBLE);
                    cardView3.setVisibility(View.VISIBLE);
                    emptycart.setVisibility(View.INVISIBLE);
                    Cart cart = dataSnapshot.getValue(Cart.class);
                    cartList.add(cart);
                    cartAdapter.notifyDataSetChanged();
                } else if (!dataSnapshot.exists()) {

                    textView.setVisibility(View.GONE);
                    tvPrice.setVisibility(View.GONE);
                    button7.setVisibility(View.GONE);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (Cart c : cartList) {
                    if (key.equals(c.getCart_key())) {
                        cartList.remove(c);
                        cartAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                Intent refreshIntent = getIntent();
                refreshIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                refreshIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(refreshIntent);


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void sendToLogin() {
        Intent loginIntent = new Intent(CartActivity.this, MainActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
