package com.example.ecommerceproject.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class BuyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar8;

    private TextView tvName, tvPName, tvPPrice, tvAdd, quantityText;
    private ImageView imageView11;
    private Button buybutton;
    private Spinner paymentMethod;

    private DatabaseReference mDatabase;
    private String currentUser;
    private FirebaseAuth mAuth;

    private String user_id;
    private String email1;
    private String name1;
    private String address1;
    private String phone1;
    private String productRandomKey;

    private String product_key;
    private String product_name1="";
    private String product_category1="";
    private String product_price1="";
    private String product_description1="";
    private String seller_name1="";
    private String product_image1="";
    private String quantity1="";
    private String product_key1="";
    private String item;
    private int total_product_price1 = 0;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        toolbar8 = findViewById(R.id.toolbar8);
        toolbar8.setTitle("Deliver to");
        setSupportActionBar(toolbar8);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar8.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar8.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvName = findViewById(R.id.tvName);
        tvPName = findViewById(R.id.tvPName);
        tvPPrice = findViewById(R.id.tvPPrice);
        tvAdd = findViewById(R.id.tvAdd);
        buybutton = findViewById(R.id.buybutton);
        imageView11 = findViewById(R.id.imageView11);
        paymentMethod = findViewById(R.id.PaymentMethod);
        quantityText = findViewById(R.id.quantity_buy);

        pd = new ProgressDialog(this);
        pd.setMessage("Updating...");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        currentUser = Prevalent.currentOnlineUser.getPhone();

        List<String> categories = new ArrayList<String>();
        categories.add("Cash on delivery");
        categories.add("Card");
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.spinner1, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        paymentMethod.setAdapter(arrayAdapter);
        paymentMethod.setOnItemSelectedListener(this);

        Bundle b = getIntent().getExtras();
        product_name1 = b.get("product_name1").toString();
        product_price1 = b.get("product_price1").toString();
        product_description1 = b.get("product_description1").toString();
        seller_name1 = b.get("seller_name1").toString();
        product_image1 = b.get("product_image1").toString();
        product_category1 = b.get("product_category1").toString();
        quantity1 = b.get("quantity1").toString();
        name1 = b.get("name1").toString();
        address1 = b.get("address1").toString();
        phone1 = b.get("phone1").toString();
        product_key1 = b.get("product_key1").toString();

        total_product_price1 = (Integer.valueOf(product_price1)*(Integer.valueOf(quantity1)));

        if (currentUser != null) {
            showDetails();
            buybutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.equals("Cash on delivery")) {
                        pd.show();
                        nextStep();
                    }else if (item.equals("Card"))
                    {
                        pd.dismiss();

                        Intent mainIntent = new Intent(BuyActivity.this, CardActivity.class);
                        mainIntent.putExtra("product_name1", product_name1);
                        mainIntent.putExtra("product_price1", product_price1);
                        mainIntent.putExtra("product_image1", product_image1);
                        mainIntent.putExtra("product_description1", product_description1);
                        mainIntent.putExtra("seller_name1", seller_name1);
                        mainIntent.putExtra("product_category1", product_category1);
                        mainIntent.putExtra("product_key1", product_key1);
                        mainIntent.putExtra("name1", name1);
                        mainIntent.putExtra("phone1", phone1);
                        mainIntent.putExtra("quantity1", quantity1);
                        mainIntent.putExtra("address1", address1);
                        mainIntent.putExtra("user_id", currentUser);
                        startActivity(mainIntent);

                    }
                    else  if (item.equals("Select Payment Mode")) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Please select a payment method", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void showDetails() {
        Picasso.get().load(product_image1).into(imageView11);
        tvAdd.setText(address1);
        tvPName.setText(product_name1);
        tvName.setText(name1);
        quantityText.setText("Quantity: "+quantity1);
        tvPPrice.setText("MAD  " +quantity1 +"*"+product_price1);
        tvAdd.setText(address1);
    }

    private void nextStep() {
        final String saveCurrentTime, saveCurrentDate;
        Calendar calForeDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForeDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForeDate.getTime());
        productRandomKey = saveCurrentDate + saveCurrentTime;

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("product_image", product_image1);
        dataMap.put("product_name", product_name1);
        dataMap.put("phone", phone1);
        dataMap.put("state", "not shipped");
        dataMap.put("product_price", product_price1);
        dataMap.put("product_category", product_category1);
        dataMap.put("product_description", product_description1);
        dataMap.put("product_key", product_key1);
        dataMap.put("seller_name", seller_name1);
        dataMap.put("quantity", quantity1);

        mDatabase.child("orders").child(currentUser).child("products").push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
        nextStep1();
    }

    private void nextStep1() {
        final String saveCurrentTime, saveCurrentDate;
        Calendar calForeDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForeDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForeDate.getTime());

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", name1);
        dataMap.put("phone", phone1);
        dataMap.put("address", address1);
        dataMap.put("user_id", user_id);
        dataMap.put("state", "not shipped");
        dataMap.put("total_price", String.valueOf(total_product_price1));
        dataMap.put("date", saveCurrentDate);
        dataMap.put("time", saveCurrentTime);
        mDatabase.child("orders").child(currentUser).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Toast.makeText(getApplicationContext(), "Product purchased successfully", Toast.LENGTH_LONG).show();
                pd.dismiss();
                Intent intent = new Intent(BuyActivity.this, OrderPlacedActivity.class);
                intent.putExtra("name",name1);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}
