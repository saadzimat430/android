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
import com.example.ecommerceproject.Sellers.Main2Activity;
import com.example.ecommerceproject.Sellers.SellerLoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class BusinessDetailsAddActivity extends AppCompatActivity
{

    private Toolbar toolbar;

    private EditText editText, companyAddress, companyDescription;
    private Button button4;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details_add);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add company details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        editText = findViewById(R.id.editText);
        companyAddress = findViewById(R.id.companyAdress);
        companyDescription = findViewById(R.id.company_description1);
        button4 = findViewById(R.id.button4);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();


        Intent getIntent = new Intent();
        Bundle b = getIntent().getExtras();
        final String key = b.get("key").toString();


        if (currentUser != null) {
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String company_name = editText.getText().toString();
                    final String company_address = companyAddress.getText().toString();
                    final String company_description = companyDescription.getText().toString();
                    button4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (company_name.isEmpty()) {
                                editText.setError("Enter company name");
                            } else if (company_address.isEmpty()) {
                                editText.setError("Enter company address");
                            }else if (company_description.isEmpty()) {
                                editText.setError("Enter company description");
                            }
                            else {
                                Map<String, Object> dataMap = new HashMap<>();
                                dataMap.put("company_name", company_name);
                                dataMap.put("company_address", company_address);
                                dataMap.put("company_description", company_description);
                                mDatabase.child("business").child(key).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(), "Company Successfully Registered", Toast.LENGTH_LONG).show();
                                        Intent homeIntent = new Intent(BusinessDetailsAddActivity.this, Main2Activity.class);
                                        startActivity(homeIntent);
                                        finish();
                                    }
                                });
                            }

                        }
                    });
                }
            });
        } else {
            sendToLogin();
        }





    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(BusinessDetailsAddActivity.this, SellerLoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

}
