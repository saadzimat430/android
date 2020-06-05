package com.example.ecommerceproject.Sellers.Company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceproject.R;
import com.example.ecommerceproject.Sellers.EditUserInfoActivity;
import com.example.ecommerceproject.Sellers.Main2Activity;
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

public class EditCompanyInfoActivity extends AppCompatActivity
{

    private Toolbar toolbar11;

    private EditText etEditOwnerName, etEditCompanyName,etEditAdress,etEditDescription, etEditPhoneNumber, etEditEmailId ;
    private Button editcompanysavebtn;

    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private String user_id;

    private String company_name = null;
    private String owner_name = null;
    private String phone_number = null;
    private String compnany_address = null;
    private String compnany_description = null;
    private String email_id = null;
    private String email = null;
    private String key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company_info);



        toolbar11 = findViewById(R.id.toolbar11);
        toolbar11.setTitle("Edit Company Info");
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



        etEditOwnerName = findViewById(R.id.etEditOwnerName);
        etEditCompanyName = findViewById(R.id.etEditCompanyName);
        etEditPhoneNumber = findViewById(R.id.etEditPhoneNumber);
        etEditEmailId = findViewById(R.id.etEditEmailId);
        etEditAdress = findViewById(R.id.etEditAdress);
        etEditDescription = findViewById(R.id.company_description_edit);
        editcompanysavebtn = findViewById(R.id.editcompanysavebtn);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        etEditEmailId.setFocusable(false);
        etEditEmailId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You cannot change your email id", Toast.LENGTH_LONG).show();
            }
        });
        getUserDetails();
        editcompanysavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCompanyDetails();
            }
        });
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

    private void saveCompanyDetails() {
        String a = etEditOwnerName.getText().toString();
        String b = etEditCompanyName.getText().toString();
        String c = etEditEmailId.getText().toString();
        String d = etEditPhoneNumber.getText().toString();
        String e = etEditAdress.getText().toString();
        String f = etEditDescription.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update changes");
        progressDialog.setMessage("Please wait, while we are updating your Company information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        if (a.isEmpty()) {
            etEditOwnerName.setError("Enter value");
            progressDialog.dismiss();
        } else if (b.isEmpty()) {
            etEditCompanyName.setError("Enter value");
            progressDialog.dismiss();
        } else if (c.isEmpty()) {
            etEditEmailId.setError("Enter value");
            progressDialog.dismiss();
        } else if (d.isEmpty()) {
            etEditPhoneNumber.setError("Enter value");
            progressDialog.dismiss();
        } else if (e.isEmpty()) {
            etEditAdress.setError("Enter value");
            progressDialog.dismiss();
        }else if (f.isEmpty()) {
            etEditDescription.setError("Enter value");
            progressDialog.dismiss();
        }
        else {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("company_name", b);
            dataMap.put("company_phone_number", d);
            dataMap.put("email", c);
            dataMap.put("company_address", e);
            dataMap.put("company_description", f);
            dataMap.put("owner_name", a);
            mDatabase.child("business").child(key).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        sendToMain();
                        progressDialog.dismiss();
                    } else {
                        String errMsg = task.getException().getMessage();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error: " + errMsg, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

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
        etEditAdress.setText(compnany_address);
        etEditDescription.setText(compnany_description);

    }

    private void sendToMain() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View viewLyout;
        viewLyout = layoutInflater.inflate(R.layout.ztoast_layout, (ViewGroup)findViewById(R.id.custom_layout));
        Toast toast = Toast.makeText(this, "Toast:Gravity.TOP", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(viewLyout);
        toast.show();
       // Toast.makeText(EditCompanyInfoActivity.this, "Company Info updated successfully.", Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(EditCompanyInfoActivity.this, CompanyHomeActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
