package com.example.ecommerceproject.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceproject.Buyers.CartActivity;
import com.example.ecommerceproject.Buyers.ProductDetailsActivity;
import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.example.ecommerceproject.Sellers.Company.VAPActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SellerMaintainProductsActivity extends AppCompatActivity {

    private Button applyChangesBtn;
    private EditText name, price, description, stock;
    private ImageView imageView;
    private String productID = "", product_category = "", company_key="";
    private TextView deleteBtn;
    private DatabaseReference mDatabase ;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_maintain_products);

        Toolbar toolbar = findViewById(R.id.toolbar_maintain);
        toolbar.setTitle("Maintain Product");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SellerMaintainProductsActivity.super.onBackPressed();
            }
        });

        productID = getIntent().getStringExtra("product_key");
        product_category = getIntent().getStringExtra("product_category");
        company_key = getIntent().getStringExtra("company_key");
        mDatabase = FirebaseDatabase.getInstance().getReference();



        applyChangesBtn = findViewById(R.id.apply_changes_btn);
        name = findViewById(R.id.product_name1);
        price = findViewById(R.id.product_price1);
        description = findViewById(R.id.product_description1);
        imageView = findViewById(R.id.product_image1);
        deleteBtn = findViewById(R.id.delete_btn);
        stock = findViewById(R.id.product_quantity);

        displaySpecificProductInfo();

        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                applyChanges();
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CharSequence options[] = new CharSequence[]
                        {
                                "Yes",
                                "No"
                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(SellerMaintainProductsActivity.this);
                builder.setTitle("Are You Sure ? ");


                builder.setItems(options, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if (i==0)
                        {
                            deleteThisProduct();
                        }
                        if (i==1)
                        {
                            return;
                        }
                    }
                });

                builder.show();
            }
        });
    }



//delete product method
    private void deleteThisProduct()
    {
        mDatabase.child("business").child(company_key).child("products").child(productID).removeValue();
        mDatabase.child("products").child(product_category).child(productID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {

                Intent intent = new Intent(SellerMaintainProductsActivity.this, Main2Activity.class);
                startActivity(intent);
                finish();
                Toast.makeText(SellerMaintainProductsActivity.this, "The Product Is Deleted Successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }


    //sauvgarde du changements
    private void applyChanges()
    {
        pd = new ProgressDialog(this);
        pd.setMessage("Updating...");
        pd.show();
        String pName = name.getText().toString();
        String pPrice = price.getText().toString();
        String pDescription = description.getText().toString();
        String pStock = stock.getText().toString();

        if (pName.equals(""))
        {
            name.setError("Write Down Product Name.");
            pd.dismiss();
            //Toast.makeText(SellerMaintainProductsActivity.this, "Write Down Product Name.", Toast.LENGTH_SHORT).show();
        }
        else if (pPrice.equals(""))
        {
            price.setError("Write Down Product Price.");
            pd.dismiss();
            //Toast.makeText(SellerMaintainProductsActivity.this, "Write Down Product Price.", Toast.LENGTH_SHORT).show();
        }
        else if (pDescription.equals(""))
        {
            description.setError("Write Down Product Description.");
            pd.dismiss();
            //Toast.makeText(SellerMaintainProductsActivity.this, "Write Down Product Description.", Toast.LENGTH_SHORT).show();
        }
        else if (pStock.equals(""))
        {
            stock.setError("Write Down Product Description.");
            pd.dismiss();
            //Toast.makeText(SellerMaintainProductsActivity.this, "Write Down Product Description.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("product_key", productID);
            productMap.put("product_description", pDescription);
            productMap.put("product_price", pPrice);
            productMap.put("product_name", pName);
            productMap.put("quantity_stock", pStock);

            //update the values
            mDatabase.child("business").child(company_key).child("products").child(productID).updateChildren(productMap);
            mDatabase.child("products").child(product_category).child(productID).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        pd.dismiss();
                        Toast.makeText(SellerMaintainProductsActivity.this, "Changes Applied Successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SellerMaintainProductsActivity.this, Main2Activity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }

    }



    private void displaySpecificProductInfo() {

        mDatabase.child("business").child(company_key).child("products").child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String pName = dataSnapshot.child("product_name").getValue().toString();
                    String pPrice = dataSnapshot.child("product_price").getValue().toString();
                    String pDescription = dataSnapshot.child("product_description").getValue().toString();
                    String pStock = dataSnapshot.child("quantity_stock").getValue().toString();
                    String pImage = dataSnapshot.child("product_image").getValue().toString();

                    name.setText(pName);
                    price.setText(pPrice);
                    description.setText(pDescription);
                    stock.setText(pStock);
                    Picasso.get().load(pImage).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }





}
