package com.example.ecommerceproject.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDeliveryDetails extends AppCompatActivity
{
    private Toolbar toolbar14;
    private Button button10;
    private EditText etADAddress, etADName, etADAddressPhone;

    private DatabaseReference mDatabase;
    private String currentUser;
    private FirebaseAuth mAuth;

    private String total_price = " ";
    private String total_product_count = " ";
    private String user_id = "";

    private String product_key = "";
    private String product_name2 = "";
    private String product_price2 = "";
    private String product_description2 = "";
    private String seller_name2 = "";
    private String product_image2 = "";
    private String quantity2 = "";
    private String product_category2 = "";
    private String cart_key2="";
    private String from_cart;
    private String product_key2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery_details);

        Bundle bundle = getIntent().getExtras();
        from_cart = bundle.get("from_cart").toString();
        if (from_cart.equals("no")) {
            product_name2 = bundle.get("product_name2").toString();
            product_price2 = bundle.get("product_price2").toString();
            product_description2 = bundle.get("product_description2").toString();
            seller_name2 = bundle.get("seller_name2").toString();
            product_image2 = bundle.get("product_image2").toString();
            quantity2 = bundle.get("quantity2").toString();
            product_key2 = bundle.get("product_key2").toString();
            product_category2 = bundle.get("product_category2").toString();


        } else if (from_cart.equals("yes")){
            total_price = bundle.get("total_price").toString();
            total_product_count = bundle.get("total_product_count").toString();
            cart_key2 = bundle.get("cart_key").toString();
        }


        toolbar14 = findViewById(R.id.toolbar14);
        toolbar14.setTitle("Add Details");
        setSupportActionBar(toolbar14);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar14.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar14.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        etADAddress = findViewById(R.id.etADAddress);
        etADName = findViewById(R.id.etADName);
        etADAddressPhone = findViewById(R.id.etADAddressPhone);
        button10 = findViewById(R.id.button10);

        mDatabase = FirebaseDatabase.getInstance().getReference();
       // mAuth = FirebaseAuth.getInstance();
        currentUser = Prevalent.currentOnlineUser.getPhone();

        if (from_cart.equals("yes"))
        {
            button10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = etADName.getText().toString();
                    String address = etADAddress.getText().toString();
                    String phone = etADAddressPhone.getText().toString();

                    if (name.isEmpty()) {
                        etADName.setError("Please enter name");
                    } else if (address.isEmpty()) {
                        etADAddress.setError("Please enter address");
                    }
                    else if (phone.isEmpty()) {
                        etADAddressPhone.setError("Please enter phone number");
                    }
                    else {
                        Intent cartBuyIntent = new Intent(AddDeliveryDetails.this, CartBuyActivity.class);
                        cartBuyIntent.putExtra("total_product_count", String.valueOf(total_product_count));
                        cartBuyIntent.putExtra("total_price", String.valueOf(total_price));
                        cartBuyIntent.putExtra("cart_key", String.valueOf(cart_key2));
                        cartBuyIntent.putExtra("name", name);
                        cartBuyIntent.putExtra("address", address);
                        cartBuyIntent.putExtra("phone", phone);
                        startActivity(cartBuyIntent);
                    }



                }
            });
        } else if (from_cart.equals("no")){
            button10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = etADName.getText().toString();
                    String address = etADAddress.getText().toString();
                    String phone = etADAddressPhone.getText().toString();

                    if (name.isEmpty()) {
                        etADName.setError("Please enter name");
                    } else if (address.isEmpty()) {
                        etADAddress.setError("Please enter address");
                    }
                    else if (phone.isEmpty()) {
                        etADAddressPhone.setError("Please enter phone number");
                    }
                    else {
                        Intent productIntent = new Intent(AddDeliveryDetails.this, BuyActivity.class);
                        //productIntent.putExtra("product_key", );
                        productIntent.putExtra("product_name1", product_name2);
                        productIntent.putExtra("product_price1", product_price2);
                        productIntent.putExtra("product_image1", product_image2);
                        productIntent.putExtra("product_description1", product_description2);
                        productIntent.putExtra("seller_name1", seller_name2);
                        productIntent.putExtra("quantity1", quantity2);
                        productIntent.putExtra("product_key1", product_key2);
                        productIntent.putExtra("product_category1", product_category2);
                        productIntent.putExtra("name1", name);
                        productIntent.putExtra("address1", address);
                        productIntent.putExtra("phone1",phone);
                        startActivity(productIntent);
                    }

                }
            });
        }
    }
}
