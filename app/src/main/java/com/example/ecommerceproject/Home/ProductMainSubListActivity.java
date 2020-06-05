package com.example.ecommerceproject.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceproject.Home.Adapters.ProductsAdapter;
import com.example.ecommerceproject.Home.Models.Products;
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
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

public class ProductMainSubListActivity extends AppCompatActivity
{

    private Toolbar toolbar4;

    private TextView textView42;

    private RecyclerView recyclerview;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    private ProductsAdapter productsAdapter;
    private List<Products> productsLists = new ArrayList<>();
    private Context mContext;
    private String categories = "";

    private String user_id;
    NotificationBadge badge;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main_sub_list);

        toolbar4 = findViewById(R.id.toolbar4);



        textView42 = findViewById(R.id.textView42);

        mContext = ProductMainSubListActivity.this;

        recyclerview = findViewById(R.id.recyclerview);
        productsAdapter = new ProductsAdapter(productsLists, mContext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(productsAdapter);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();
        String electronics = bundle.getString("electronics");
        String books = bundle.getString("books");
        String watch = bundle.getString("watches");
        String men = bundle.getString("men dresses");
        String female = bundle.getString("female dresses");
        String shoes = bundle.getString("shoes");
        String laptops = bundle.getString("laptops");
        String mobiles = bundle.getString("mobile phones");
        String product_category = bundle.getString("product_category");


        user_id = Prevalent.currentOnlineUser.getPhone();

        if (electronics != null){
            toolbar4.setTitle("Electronics");
            categories = "Electronics";
            setSupportActionBar(toolbar4);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar4.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
            toolbar4.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mDatabase.child("products").child("Electronics").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists()) {
                        textView42.setVisibility(View.INVISIBLE);
                        Products products = dataSnapshot.getValue(Products.class);
                        productsLists.add(products);
                        productsAdapter.notifyDataSetChanged();
                    }


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if (books != null){
            toolbar4.setTitle("Books");
            categories = "Books";
            setSupportActionBar(toolbar4);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar4.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
            toolbar4.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mDatabase.child("products").child("Books").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists()) {
                        textView42.setVisibility(View.INVISIBLE);
                        Products products = dataSnapshot.getValue(Products.class);
                        productsLists.add(products);
                        productsAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if (watch != null){
            toolbar4.setTitle("watches");
            categories = "Watches";
            setSupportActionBar(toolbar4);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar4.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
            toolbar4.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mDatabase.child("products").child("Watches").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists()) {
                        textView42.setVisibility(View.INVISIBLE);
                        Products products = dataSnapshot.getValue(Products.class);
                        productsLists.add(products);
                        productsAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if (men != null) {
            toolbar4.setTitle("Men Dresses");
            categories = "Men Dresses";
            setSupportActionBar(toolbar4);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar4.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
            toolbar4.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mDatabase.child("products").child("Men Dresses").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists()) {
                        textView42.setVisibility(View.INVISIBLE);
                        Products products = dataSnapshot.getValue(Products.class);
                        productsLists.add(products);
                        productsAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if (female != null) {
            toolbar4.setTitle("Female Dresses");
            categories = "Female Dresses";
            setSupportActionBar(toolbar4);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar4.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
            toolbar4.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mDatabase.child("products").child("Female Dresses").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists()) {
                        textView42.setVisibility(View.INVISIBLE);
                        Products products = dataSnapshot.getValue(Products.class);
                        productsLists.add(products);
                        productsAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if (laptops != null) {
            toolbar4.setTitle("Laptops");
            categories = "Laptops";
            setSupportActionBar(toolbar4);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar4.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
            toolbar4.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mDatabase.child("products").child("Laptops").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists()) {
                        textView42.setVisibility(View.INVISIBLE);
                        Products products = dataSnapshot.getValue(Products.class);
                        productsLists.add(products);
                        productsAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if (mobiles != null) {
            toolbar4.setTitle("Mobile Phones");
            categories = "Mobile Phones";
            setSupportActionBar(toolbar4);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar4.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
            toolbar4.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mDatabase.child("products").child("Mobile Phones").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists()) {
                        textView42.setVisibility(View.INVISIBLE);
                        Products products = dataSnapshot.getValue(Products.class);
                        productsLists.add(products);
                        productsAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if (shoes != null) {
            toolbar4.setTitle("Shoes");
            categories = "Shoes";
            setSupportActionBar(toolbar4);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar4.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
            toolbar4.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mDatabase.child("products").child("Shoes").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists()) {
                        textView42.setVisibility(View.INVISIBLE);
                        Products products = dataSnapshot.getValue(Products.class);
                        productsLists.add(products);
                        productsAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if (product_category != null) {
            toolbar4.setTitle(product_category);
            categories = "product_category";
            setSupportActionBar(toolbar4);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar4.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
            toolbar4.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mDatabase.child("products").child(product_category).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists()) {
                        textView42.setVisibility(View.INVISIBLE);
                        Products products = dataSnapshot.getValue(Products.class);
                        productsLists.add(products);
                        productsAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent mainIntent = new Intent(ProductMainSubListActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu2,menu);
        View view = menu.findItem(R.id.notification_id1).getActionView();
        badge = (NotificationBadge) view.findViewById(R.id.badge1);
        updateOrders();

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent orderIntent = new Intent(ProductMainSubListActivity.this, CartActivity.class);
                startActivity(orderIntent);

            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.search_id1)
        {
            Intent orderIntent = new Intent(ProductMainSubListActivity.this, SearchProductsActivity.class);
            orderIntent.putExtra("category", categories);
            startActivity(orderIntent);
            Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();

        }
        else  if (id == R.id.notification_id1)
        {
            Intent orderIntent = new Intent(ProductMainSubListActivity.this, CartActivity.class);
            startActivity(orderIntent);
        }

        return true;
    }

    private void updateOrders()
    {
        if (badge==null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference().child("cart").child(user_id);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        count = (int) dataSnapshot.getChildrenCount();
                        if (count==0)
                        {
                            badge.setVisibility(View.INVISIBLE);
                        }else
                        {

                            badge.setVisibility(View.VISIBLE);
                            badge.setText(String.valueOf(count));
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateOrders();
    }
}
