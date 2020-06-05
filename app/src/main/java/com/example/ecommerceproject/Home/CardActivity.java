package com.example.ecommerceproject.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CardActivity extends AppCompatActivity
{
    private Toolbar toolbar9;

    private EditText etCardNumber, etCardName, etCardMonth, etCardYear, etCardCVV, etAtmPin;
    private Button button6;

    private DatabaseReference mDatabase;
    private String currentUser;
    private FirebaseAuth mAuth;

    private String user_id;
    private String phone;
    private String name;
    private String address;

    private String product_key;
    private String product_name;
    private String product_price;
    private String product_category;
    private String product_description;
    private String seller_name;
    private String quantity;
    private String product_image;
    private String productRandomKey;
    private int  total_product_price=0;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        toolbar9 = findViewById(R.id.toolbar9);
        toolbar9.setTitle("Add Card");
        pd = new ProgressDialog(this);
        pd.setMessage("Updating...");

        etCardNumber = findViewById(R.id.etCardNumber);
        etCardName = findViewById(R.id.etCardName);
        etCardMonth = findViewById(R.id.etCardMonth);
        etCardYear = findViewById(R.id.etCardYear);
        etCardCVV = findViewById(R.id.etCardCVV);
        etAtmPin = findViewById(R.id.etAtmPin);
        button6 = findViewById(R.id.button6);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //mAuth = FirebaseAuth.getInstance();
        currentUser = Prevalent.currentOnlineUser.getPhone();

        Bundle b = getIntent().getExtras();
        //product_key = b.get("product_key").toString();
        product_name = b.get("product_name1").toString();
        product_price = b.get("product_price1").toString();
        product_category = b.get("product_category1").toString();
        product_key = b.get("product_key1").toString();
        quantity = b.get("quantity1").toString();
        product_description = b.get("product_description1").toString();
        seller_name = b.get("seller_name1").toString();
        product_image = b.get("product_image1").toString();
        name = b.get("name1").toString();
        address = b.get("address1").toString();
        phone = b.get("phone1").toString();
        user_id = b.get("user_id").toString();

        total_product_price = (Integer.valueOf(product_price)*(Integer.valueOf(quantity)));
        //sauvgarder la date
        final String saveCurrentTime, saveCurrentDate;
        Calendar calForeDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForeDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForeDate.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                String n1 = etCardNumber.getText().toString();
                String n2 = etCardName.getText().toString();
                String n3 = etCardMonth.getText().toString();
                String n4 = etCardYear.getText().toString();
                String n5 = etCardCVV.getText().toString();
                String n6 = etAtmPin.getText().toString();

                if (n1.isEmpty()) {
                    pd.dismiss();
                    etCardNumber.setError("Please enter details");
                } else if (n2.isEmpty()) {
                    pd.dismiss();
                    etCardName.setError("Please enter details");
                } else if (n3.isEmpty()) {
                    pd.dismiss();
                    etCardMonth.setError("Please enter details");
                } else if (n4.isEmpty()) {
                    pd.dismiss();
                    etCardYear.setError("Please enter details");
                } else if (n5.isEmpty()) {
                    pd.dismiss();
                    etCardCVV.setError("Please enter details");
                } else if (n6.isEmpty()) {
                    pd.dismiss();
                    etAtmPin.setError("Please enter details");
                } else if (Integer.parseInt(n3) > 12) {
                    pd.dismiss();
                    etCardMonth.setError("Please enter correct card details");
                } else if (n1.length() < 16) {
                    pd.dismiss();
                    etCardNumber.setError("Please enter correct card details");
                } else {
                    HashMap<String, Object> dataMap = new HashMap<>();
                    dataMap.put("product_image", product_image);
                    dataMap.put("product_name", product_name);
                    dataMap.put("product_price", product_price);
                    dataMap.put("product_category", product_category);
                    dataMap.put("state", "not shipped");
                    dataMap.put("product_description", product_description);
                    dataMap.put("quantity", quantity);
                    dataMap.put("product_key", product_key);
                    dataMap.put("seller_name", seller_name);
                    nextStep();
                    mDatabase.child("orders").child(user_id).child("products").push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }


            }
        });
    }



    private void nextStep() {
        final String saveCurrentTime, saveCurrentDate;
        Calendar calForeDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForeDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForeDate.getTime());

        //getTotalProductCount();

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", name);
        dataMap.put("address", address);
        dataMap.put("phone",phone );
        dataMap.put("user_id", user_id);
        dataMap.put("state", "not shipped");
        dataMap.put("date", saveCurrentDate);
        dataMap.put("time", saveCurrentTime);
        dataMap.put("total_price", String.valueOf(total_product_price));
        mDatabase.child("orders").child(user_id).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Product purchased successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CardActivity.this, OrderPlacedActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
                finish();
            }
        });

    }

//    private void getTotalProductCount() {
//        mDatabase.child("orders").child(currentUser).child("products").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                total_product_count1 = (int) dataSnapshot.getChildrenCount();
//                //Toast.makeText(getApplicationContext(), String.valueOf(total_product_count), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
