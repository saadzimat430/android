package com.example.ecommerceproject.Sellers.Company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceproject.R;
import com.example.ecommerceproject.Sellers.SellerLoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BusinessRegisterActivity extends AppCompatActivity
{

    private Toolbar BRtoolbar;

    private EditText etBREmail, etBRPassword;
    private Button button3;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private String user_id = null;

    private String name;
    private String email;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_register);


        BRtoolbar = findViewById(R.id.BRtoolbar);
        BRtoolbar.setTitle("Business Register");

        button3 = findViewById(R.id.button3);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            sendToLogin();
        } else {
            user_id = mAuth.getCurrentUser().getUid();
            getUserDetails();
        }
    }


    private void getUserDetails() {
        mDatabase.child("Sellers").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue().toString();
                email = dataSnapshot.child("email").getValue().toString();
                phone = dataSnapshot.child("phone").getValue().toString();

                showDetails();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showDetails() {
        mDatabase.child("Sellers").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String email = dataSnapshot.child("email").getValue().toString();
                button3.setText("Register via " + "'" + email + "'");

                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registerCompany();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void registerCompany() {
        final String key = mDatabase.child("business").push().getKey();
        final HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("email", email);
        dataMap.put("owner_name", name);
        dataMap.put("company_phone_number", phone);
        dataMap.put("company_key", key);

        mDatabase.child("business").child(key).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mDatabase.child("Sellers").child(user_id).child("business").updateChildren(dataMap);
                    Intent businessNextIntent = new Intent(BusinessRegisterActivity.this, BusinessDetailsAddActivity.class);
                    businessNextIntent.putExtra("email", email);
                    businessNextIntent.putExtra("key", key);
                    startActivity(businessNextIntent);
                    finish();
                } else {
                    String errMsg = task.getException().getMessage();
                    Toast.makeText(getApplicationContext(), "Error: " + errMsg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void sendToLogin() {
        Intent loginIntent = new Intent(BusinessRegisterActivity.this, SellerLoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
