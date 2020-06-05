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
import com.example.ecommerceproject.Home.Adapters.WishListAdapter;
import com.example.ecommerceproject.Home.Models.WishList;
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

public class WishListActivity extends AppCompatActivity {

    private Toolbar toolbar6;

    private TextView textView, tvPrice;

    private RecyclerView cartRV;
    private List<WishList> wishLists = new ArrayList<>();
    private Context mContext;
    private WishListAdapter wishListAdapter;

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
        setContentView(R.layout.activity_wish_list);

        mContext = WishListActivity.this;

        toolbar6 = findViewById(R.id.add_toolbar_wishlist);
        toolbar6.setTitle("Your Wish List");
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

        emptycart = findViewById(R.id.empty_notification_wish);
        cartRV = findViewById(R.id.wish_list);
        wishListAdapter = new WishListAdapter(wishLists, mContext);
        cartRV.setAdapter(wishListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        cartRV.setLayoutManager(layoutManager);

        textView = findViewById(R.id.textView_wish);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        user_id = Prevalent.currentOnlineUser.getPhone();
        getWishListDetails();


    }

    private void getWishListDetails() {
        mDatabase.child("wishList").child(user_id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    textView.setVisibility(View.INVISIBLE);
                    emptycart.setVisibility(View.INVISIBLE);
                    WishList wish = dataSnapshot.getValue(WishList.class);
                    wishLists.add(wish);
                    wishListAdapter.notifyDataSetChanged();
                } else if (!dataSnapshot.exists()) {

                    textView.setVisibility(View.GONE);

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (WishList c : wishLists) {
                    if (key.equals(c.getCart_key())) {
                        wishLists.remove(c);
                        wishListAdapter.notifyDataSetChanged();
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

}
