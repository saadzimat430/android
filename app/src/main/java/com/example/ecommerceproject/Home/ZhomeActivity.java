package com.example.ecommerceproject.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ZhomeActivity extends AppCompatActivity
{

    private Toolbar cattoolbar;

    private ImageView settingsbtn, orderIV, imageView7, imageView16;
    private TextView textView, textView2, textView3, tvName,textView35,textView32,textView33,textView34,textView31;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    private String user_id;
    private String ds_company_name;

    private String name;
    private String email;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhome);

        cattoolbar = findViewById(R.id.hometoolbar);
        cattoolbar.setTitle("Categories");
        setSupportActionBar(cattoolbar);
        cattoolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        cattoolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        cattoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        settingsbtn = findViewById(R.id.settingsbtn);
        tvName = findViewById(R.id.tvName);
        imageView16 = findViewById(R.id.imageView16);
        orderIV = findViewById(R.id.orderIV);
        imageView7 = findViewById(R.id.imageView7);
        textView35 = findViewById(R.id.textView35);
        textView32 = findViewById(R.id.textView32);
        textView33 = findViewById(R.id.textView33);
        textView34 = findViewById(R.id.textView34);
        textView31 = findViewById(R.id.textView31);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        textView31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productMainListIntent = new Intent(ZhomeActivity.this, ProductMainSubListActivity.class);
                productMainListIntent.putExtra("watches", "watches");
                startActivity(productMainListIntent);
            }
        });

        textView34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productMainListIntent = new Intent(ZhomeActivity.this, ProductMainSubListActivity.class);
                productMainListIntent.putExtra("shoes", "shoes");
                startActivity(productMainListIntent);
            }
        });

        textView33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productMainListIntent = new Intent(ZhomeActivity.this, ProductMainSubListActivity.class);
                productMainListIntent.putExtra("men dresses", "men dresses");
                startActivity(productMainListIntent);
            }
        });

        textView32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productMainListIntent = new Intent(ZhomeActivity.this, ProductMainSubListActivity.class);
                productMainListIntent.putExtra("female dresses", "female dresses");
                startActivity(productMainListIntent);
            }
        });

        textView35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productMainListIntent = new Intent(ZhomeActivity.this, ProductMainSubListActivity.class);
                productMainListIntent.putExtra("laptops", "laptops");
                startActivity(productMainListIntent);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productMainListIntent = new Intent(ZhomeActivity.this, ProductMainSubListActivity.class);
                productMainListIntent.putExtra("electronics", "electronics");
                startActivity(productMainListIntent);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productMainListIntent = new Intent(ZhomeActivity.this, ProductMainSubListActivity.class);
                productMainListIntent.putExtra("books", "books");
                startActivity(productMainListIntent);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productMainListIntent = new Intent(ZhomeActivity.this, ProductMainSubListActivity.class);
                productMainListIntent.putExtra("mobile phones", "mobile phones");
                startActivity(productMainListIntent);
            }
        });

//        settingsbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent settingsIntent = new Intent(ZhomeActivity.this, EditInfoActivity.class);
//                startActivity(settingsIntent);
//            }
//        });
        orderIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderIntent = new Intent(ZhomeActivity.this, OrderActivity.class);
                startActivity(orderIntent);
            }
        });
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderIntent = new Intent(ZhomeActivity.this, CartActivity.class);
                startActivity(orderIntent);
            }
        });
    }


//    private void businessActivity() {
//        mDatabase.child("business").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                        final String ds_email = ds.child("email").getValue().toString();
//                        if (ds_email.equals(email)) {
//                            if (ds.child("company_name").exists()) {
//                                ds_company_name = ds.child("company_name").getValue().toString();
//                            }
//
//                            final String key = ds.getKey();
//                            settingsbtn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent settingsIntent = new Intent(ZhomeActivity.this, EditInfoActivity.class);
//                                    settingsIntent.putExtra("ds_email", ds_email);
//                                    settingsIntent.putExtra("ds_company_name", ds_company_name);
//                                    settingsIntent.putExtra("key", key);
//                                    startActivity(settingsIntent);
//                                }
//                            });
//
//                        }
//
//
//                    }
//                } else {
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

//    private void sendToAddDetails() {
//        Intent detailsActivity = new Intent(ZhomeActivity.this, AddUserDetailsActivity.class);
//        startActivity(detailsActivity);
//        finish();
//    }
//
//    private void sendToLogin() {
//        Intent loginIntent = new Intent(ZhomeActivity.this, LoginActivity.class);
//        startActivity(loginIntent);
//        finish();
//    }
//
//    private void sendToEdit() {
//        Intent editIntent = new Intent(ZhomeActivity.this, EditUserInfoActivity.class);
//        startActivity(editIntent);
//        finish();
//    }
//
//    private void sendToRegister() {
//        Intent registerIntent = new Intent(ZhomeActivity.this, RegisterActivity.class);
//        startActivity(registerIntent);
//        finish();
//    }
//
//    private void sendToStart() {
//        Intent registerIntent = new Intent(ZhomeActivity.this, StartActivity.class);
//        startActivity(registerIntent);
//        finish();
//    }
}
