package com.example.ecommerceproject.Sellers.Company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ecommerceproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutCompanyActivity extends AppCompatActivity
{
    private Toolbar toolbar11;

    private TextView etEditOwnerName, etEditCompanyName,etEditAdress,etEditDescription, etEditPhoneNumber, etEditEmailId ;


    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private LinearLayout cMap, cProducts, cAbout ;


    private String company_name = null;
    private String owner_name = null;
    private String phone_number = null;
    private String compnany_address = null;
    private String compnany_description = null;
    private String email_id = null;
    private String email = null;
    private String key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_company);

        toolbar11 = findViewById(R.id.company_details_toolbar1);
        toolbar11.setTitle("Company Info");
        setSupportActionBar(toolbar11);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar11.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar11.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        etEditOwnerName = findViewById(R.id.company_owner_id);
        etEditCompanyName = findViewById(R.id.company_name_details);
        etEditPhoneNumber = findViewById(R.id.phone_id);
        etEditEmailId = findViewById(R.id.company_email_id);
        //etEditAdress = findViewById(R.id.etEditAdress);
        etEditDescription = findViewById(R.id.company_description_details1);

        //cMap = findViewById(R.id.cmap8);
        //cProducts = findViewById(R.id.company_product1);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        getUserDetails();


//        cProducts.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(AboutCompanyActivity.this, VAPActivity.class);
//                intent.putExtra("company_key",key );
//                startActivity(intent);
//            }
//        });

//        cMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(AboutCompanyActivity.this, MapsActivity.class);
//                intent.putExtra("company_add",compnany_address );
//                intent.putExtra("company_key",key );
//                startActivity(intent);
//
//            }
//        });
    }


    private void getUserDetails() {
        mDatabase.child("Sellers").child(currentUser.getUid()).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                email_id = dataSnapshot.getValue().toString();
                getCompanyDetails();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getCompanyDetails() {
        mDatabase.child("business").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    email = ds.child("email").getValue().toString();
                    key = ds.getKey();
                    if (email_id.equals(email)) {
                        fetchCompanyDetails();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchCompanyDetails() {
        mDatabase.child("business").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    company_name = dataSnapshot.child("company_name").getValue().toString();
                    owner_name = dataSnapshot.child("owner_name").getValue().toString();
                    phone_number = dataSnapshot.child("company_phone_number").getValue().toString();
                    compnany_address = dataSnapshot.child("company_address").getValue().toString();
                    compnany_description = dataSnapshot.child("company_description").getValue().toString();
                    displayDetails();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayDetails() {
        etEditEmailId.setText(email_id);
        etEditPhoneNumber.setText(phone_number);
        etEditOwnerName.setText(owner_name);
        etEditCompanyName.setText(company_name);
        //etEditAdress.setText(compnany_address);
        etEditDescription.setText(compnany_description);

    }
}
