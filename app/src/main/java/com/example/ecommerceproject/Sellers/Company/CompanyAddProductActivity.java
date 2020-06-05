package com.example.ecommerceproject.Sellers.Company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ecommerceproject.R;
import com.example.ecommerceproject.Sellers.Main2Activity;
import com.example.ecommerceproject.Sellers.SellerLoginActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompanyAddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar toolbar2;

    private EditText etAddProductName, etAddProductPrice, etAddProductDescription,stock;
    private Button addbtn;
    private ImageView imageView5;
    private ProgressDialog pd;
    private Spinner sProductCategory;

    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseStorage mStorage;
    private StorageReference storageReference;

    private String user_id = null;
    private String email;
    private String ds_email;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;

    private String company_name;
    private String key;
    private String product_name;
    private String product_price;
    private String product_description;
    private String product_stock ;
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_add_product);


        toolbar2 = findViewById(R.id.toolbar2);
        toolbar2.setTitle("Add a new product");
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar2.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        etAddProductName = findViewById(R.id.etAddProductName);
        etAddProductPrice = findViewById(R.id.etAddProductPrice);
        etAddProductDescription = findViewById(R.id.etAddProductDescription);
        addbtn = findViewById(R.id.addbtn);
        imageView5 = findViewById(R.id.imageView5);
        sProductCategory = findViewById(R.id.sProductCategory);
        stock = findViewById(R.id.etAddProduct_Quantity_Stock);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance();

        storageReference = mStorage.getReferenceFromUrl("gs://ecommerceproject-dc6c8.appspot.com");

        if (currentUser == null) {
            sendToLogin();
        } else {
            user_id = mAuth.getCurrentUser().getUid();
            List<String> categories = new ArrayList<String>();
            categories.add("Electronics");
            categories.add("Watches");
            categories.add("Female Dresses");
            categories.add("Men Dresses");
            categories.add("Shoes");
            categories.add("Laptops");
            categories.add("Mobile Phones");
            categories.add("Books");

            ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_item);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            sProductCategory.setAdapter(arrayAdapter);
            sProductCategory.setOnItemSelectedListener(this);

            getRegisteredUserDetails();
        }
    }
//Recupere l'email du fournisseur
    private void getRegisteredUserDetails()
    {
        mDatabase.child("Sellers").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                email  = dataSnapshot.child("email").getValue().toString();
                getCompanyDetails();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //recuper les infos de l'entreprise
    private void getCompanyDetails()
    {
        mDatabase.child("business").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    ds_email = ds.child("email").getValue().toString();
                    if (ds_email.equals(email)) {
                        key = ds.getKey();
                        company_name = ds.child("company_name").getValue().toString();
                        imageView5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_PICK);
                                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
                            }
                        });
                        addbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getInputDetails();
                            }
                        });

                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //verifier les inputs
    private void getInputDetails()
    {
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading...");
        product_name = etAddProductName.getText().toString();
        product_price = etAddProductPrice.getText().toString();
        product_description = etAddProductDescription.getText().toString();
        product_stock = stock.getText().toString();
        if (product_name.isEmpty()) {
            etAddProductName.setError("Enter product name");
        } else if (product_price.isEmpty()) {
            etAddProductPrice.setError("Enter product price");
        }
        else if (product_stock.isEmpty()) {
            stock.setError("Enter product price");
        }
        else if (product_description.isEmpty()) {
            etAddProductDescription.setError("Enter product description");
        } else  if (item.equals("Select Category")) {
            Toast.makeText(getApplicationContext(), "Please select a category", Toast.LENGTH_LONG).show();
        }
        else {
            uploadDetails();
        }

    }


    //reccuperer le lien de l'image importée
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                Picasso.get().load(filePath).into(imageView5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//sauvgarder les données dans firebase
    private void uploadDetails()
    {
        if(filePath != null) {
            pd.show();
            final Long ts_long = System.currentTimeMillis()/1000;
            final String ts = ts_long.toString();
            final StorageReference childRef = storageReference.child("Company_product_images/" + ts + ".jpg");

            //uploading the image
            final UploadTask uploadTask = childRef.putFile(filePath);

            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        pd.dismiss();
                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }

                                // Continue with the task to get the download URL
                                return childRef.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                pd.dismiss();
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();
                                    String mUri = downloadUri.toString();
                                    Toast.makeText(getApplicationContext(), mUri, Toast.LENGTH_LONG).show();
                                    final String product_key = mDatabase.child("products").child(item).push().getKey();
                                    final HashMap<String, Object> dataMap = new HashMap<>();
                                    dataMap.put("product_image", mUri);
                                    dataMap.put("product_name", product_name);
                                    dataMap.put("product_price", product_price);
                                    dataMap.put("product_description", product_description);
                                    dataMap.put("quantity_stock", product_stock);
                                    dataMap.put("company_key", key);
                                    dataMap.put("company_name", company_name);
                                    dataMap.put("product_added_time", ts_long);
                                    dataMap.put("product_category", item);
                                    dataMap.put("product_key", product_key);
                                    mDatabase.child("products").child(item).child(product_key).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                mDatabase.child("business").child(key).child("products").child(product_key).setValue(dataMap);
                                                Toast.makeText(getApplicationContext(), "Product added successfully", Toast.LENGTH_LONG).show();
                                                Intent settingsIntent = new Intent(CompanyAddProductActivity.this, Main2Activity.class);
                                                startActivity(settingsIntent);
                                                finish();

                                            } else {
                                                String errMsg = task.getException().getMessage();
                                                Toast.makeText(getApplicationContext(), "Error: " + errMsg, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    // Handle failures
                                    // ...
                                    String errMsg = task.getException().getMessage();
                                    Toast.makeText(getApplicationContext(), "Download Uri Error: " + errMsg, Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } else {
                        String errMsg = task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), "Error: " + errMsg, Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Upload Failed: " + e, Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(CompanyAddProductActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
        }
    }


    private void sendToLogin()
    {
        Intent loginIntent = new Intent(CompanyAddProductActivity.this, SellerLoginActivity.class);
        startActivity(loginIntent);
        finish();
    }







    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
