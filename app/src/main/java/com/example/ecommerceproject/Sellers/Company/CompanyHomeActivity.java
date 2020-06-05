package com.example.ecommerceproject.Sellers.Company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ecommerceproject.R;
import com.example.ecommerceproject.Sellers.EditUserInfoActivity;
import com.example.ecommerceproject.Sellers.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompanyHomeActivity extends AppCompatActivity
{
    private TextView cName, cAddress, cOwner;
    private LinearLayout cMap, cProducts, cEdit,cAdd, cAbout,cInfo ;

    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private String user_id;

    private Toolbar cToolbar;


    private String company_name = null;
    private String owner_name = null;
    private String compnany_address = null;
    private String email_id = null;
    private String email = null;
    private String company_info = null;
    private String key = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);

        cToolbar = findViewById(R.id.cToolbar);
        cToolbar.setTitle("Company Info");
        setSupportActionBar(cToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        cToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        cToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });


        cName = findViewById(R.id.cName);
        cAddress = findViewById(R.id.cAddress);
        cOwner = findViewById(R.id.cOwner);
        cMap = findViewById(R.id.cMap);
        cProducts = findViewById(R.id.shoppp);
        cAdd = findViewById(R.id.add_product_company);
        cEdit = findViewById(R.id.cEdit);
        cAbout = findViewById(R.id.about_company);
        cInfo = findViewById(R.id.com_info);

        cEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CompanyHomeActivity.this, EditCompanyInfoActivity.class);
                startActivity(intent);
            }
        });


        cAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CompanyHomeActivity.this, CompanyAddProductActivity.class);
                startActivity(intent);
            }
        });




        cProducts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CompanyHomeActivity.this, VAPActivity.class);
                intent.putExtra("company_key",key );
                startActivity(intent);
            }
        });

        cMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CompanyHomeActivity.this, MapsActivity.class);
                intent.putExtra("company_add",compnany_address );
                intent.putExtra("company_key",key );
                startActivity(intent);

            }
        });

        cInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CompanyHomeActivity.this, AboutCompanyActivity.class);
                intent.putExtra("company_add",compnany_address );
                intent.putExtra("company_key",key );
                startActivity(intent);

            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        getUserDetails();

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
                    //phone_number = dataSnapshot.child("company_phone_number").getValue().toString();
                    compnany_address = dataSnapshot.child("company_address").getValue().toString();
                    displayDetails();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayDetails() {
        cOwner.setText("Owner: " +owner_name);
        cName.setText(company_name);
        cAddress.setText(compnany_address);

    }
}
