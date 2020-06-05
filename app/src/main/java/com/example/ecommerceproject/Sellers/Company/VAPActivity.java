package com.example.ecommerceproject.Sellers.Company;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ecommerceproject.Model.Products;
import com.example.ecommerceproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class VAPActivity extends AppCompatActivity
{
    private RecyclerView vapRV;
    private VAPAdapter vapAdapter;
    private List<CProducts> productsLists = new ArrayList<>();
    private Context mContext;
    private Toolbar toolbar15;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;

    private String user_id;
    private String ds_email;
    private String ds_bs_email;
    private String company_key;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vap);


        mContext = VAPActivity.this;

        toolbar15 = findViewById(R.id.toolbar15);
        toolbar15.setTitle("Your products");
        setSupportActionBar(toolbar15);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar15.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar15.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        company_key = bundle.get("company_key").toString();

        vapRV = findViewById(R.id.vapRV);
        vapAdapter = new VAPAdapter(productsLists, mContext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        vapRV.setLayoutManager(layoutManager);
        vapRV.setAdapter(vapAdapter);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        user_id = currentUser.getUid();

        Toast.makeText(getApplicationContext(), company_key, Toast.LENGTH_LONG).show();

        mDatabase.child("business").child(company_key).child("products").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                CProducts products = dataSnapshot.getValue(CProducts.class);
                productsLists.add(products);
                vapAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (CProducts p : productsLists) {
                    if (key.equals(p.getProduct_key())) {
                        productsLists.remove(p);
                        vapAdapter.notifyDataSetChanged();
                        break;
                    }
                }

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
