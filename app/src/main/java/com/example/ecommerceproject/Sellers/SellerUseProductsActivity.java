package com.example.ecommerceproject.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceproject.Model.CompayOrders;
import com.example.ecommerceproject.R;
import com.example.ecommerceproject.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SellerUseProductsActivity extends AppCompatActivity {

    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;
    private String userID = "",name = "",phone = "",address = "",total_price1="",cname="" , stock="0",company_key="", product_category="";
    private TextView updateBtn, sellerName ;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;

    private ProgressDialog pd;
    private String email_id = null;
    private String email = null;
    private String key = null;
    private String product_key = null;
    private String uID = null;
    private String category = null;
    private String quantity = null;
    private String pStock = "";
    private int cost = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_use_products);

        Toolbar toolbar = findViewById(R.id.orders_toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        productsList = findViewById(R.id.products_list);
        productsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        email_id = currentUser.getEmail();

        //sauvgarder uid dans userID
        userID = getIntent().getStringExtra("uid");
        cname = getIntent().getStringExtra("Companyname");
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        address = getIntent().getStringExtra("address");
        total_price1 = getIntent().getStringExtra("total_price");

//        product_category = getIntent().getStringExtra("product_category");
//        company_key = getIntent().getStringExtra("company_key");

        cartListRef = FirebaseDatabase.getInstance().getReference()
                .child("orders")
                .child(userID)
                .child("products");



    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<CompayOrders> options =
                new FirebaseRecyclerOptions.Builder<CompayOrders>()
                        .setQuery(cartListRef.orderByChild("state").equalTo("not shipped"), CompayOrders.class)
                        .build();

        FirebaseRecyclerAdapter<CompayOrders, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<CompayOrders, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final CartViewHolder holder, final int position, @NonNull final CompayOrders model) {

                        holder.txtProductQuantity.setText("Quantity: " + model.getQuantity());
                        holder.txtProductPrice.setText("Price " + model.getProduct_price() + " $");
                        holder.txtProductName.setText(model.getProduct_name());
                        holder.txtProductSeller.setText(model.getSeller_name());
                        holder.shipped.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(SellerUseProductsActivity.this);
                                builder.setTitle("Deliver This Order Products ? ");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        if (i==0)
                                        {
                                            pd.show();
                                            uID = getRef(position).getKey();
                                            quantity = model.getQuantity();
                                            category = model.getProduct_category();
                                            product_key = model.getProduct_key();
                                            String pName = model.getProduct_name();
                                            String pImage = model.getProduct_image();
                                            String pPrice = model.getProduct_price();
                                            String pQuantity = model.getQuantity();
                                            //send notification to the client
                                            //sendNotification(pName, pImage, pPrice, pQuantity);
                                            //change the state of the product to shipped
                                            HashMap<String, Object> Map = new HashMap<>();
                                            Map.put("state", "this product is delivered");
                                            cartListRef.child(uID).updateChildren(Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    pd.dismiss();
                                                    Toast.makeText(SellerUseProductsActivity.this, "Done.", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            //fetchCompanyDetails();

                                        }
                                        else
                                        {
                                            return;
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                        Picasso.get().load(model.getProduct_image()).into(holder.imageView1, new Callback() {
                            @Override
                            public void onSuccess() {
                                holder.progressBar31.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });


                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list_items3, parent, false);
                        updateBtn = view.findViewById(R.id.cartDelbtn);
                        updateBtn.setVisibility(View.INVISIBLE);

                        sellerName= view.findViewById(R.id.seller_name);
                        sellerName.setVisibility(View.INVISIBLE);

                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
        };

        productsList.setAdapter(adapter);
        adapter.startListening();
    }
//    private void fetchCompanyDetails() {
//
//        mDatabase.child("business").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    key = ds.getKey();
//
//                }
//                stock_update();
//                saveStockResualt();
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }
//    private void stock_update()
//    {
//        mDatabase.child("business").child(key).child("products").child(product_key).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                if (dataSnapshot.exists()) {
//                    String pStock = dataSnapshot.child("quantity_stock").getValue().toString();
//                    cost = (Integer.valueOf(pStock)) - (Integer.valueOf(quantity));
//                    stock = String.valueOf(cost);
//                    Toast.makeText(SellerUseProductsActivity.this, "oihsdnkjnkjsd   "+ stock, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
////        mDatabase.child("business").child(key).child("products").child(product_key).addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
////            {
////
////                if (dataSnapshot.exists())
////                {
////                    String pStock = dataSnapshot.child("quantity_stock").getValue().toString();
////                    //Toast.makeText(SellerUseProductsActivity.this, "oihsdnkjnkjsd   "+ cost, Toast.LENGTH_SHORT).show();
////                    cost = (Integer.valueOf(pStock))-(Integer.valueOf(quantity));
////                    stock =  String.valueOf(cost) ;
////                    Toast.makeText(SellerUseProductsActivity.this, "oihsdnkjnkjsd   "+ stock, Toast.LENGTH_SHORT).show();
////                    saveStockResualt(key,product_key,category, stock);
////                }
////
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError)
////            {
////
////            }
////        });
//    }

//    private void getProductShipToOrders(final String uID) {
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//
//
//
//        mDatabase.child("orders").child(userID).child("products").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
//                    Object producut_name = map.get("product_name");
//                    Object product_image = map.get("product_image");
//                    Object seller_name = map.get("seller_name");
//                    Object cart_key = map.get("cart_key");
//                    Object product_description = map.get("product_description");
//                    Object product_price = map.get("product_price");
//                    mDatabase.child("shipped_orders").child(uID).child("products").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>()
//                    {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task)
//                        {
//                            finish();
//                        }
//                    });
//                }
//                //nextStep2(uID);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        mDatabase.child("cart").child(userID).setValue(null);
//        finish();
//
//    }

//    private void saveStockResualt()
//    {
//
//        HashMap<String, Object> productMap = new HashMap<>();
//        productMap.put("quantity_stock", stock);
//
//        //change the state of the product to shipped
//        HashMap<String, Object> Map = new HashMap<>();
//        Map.put("state", "this product is delivered");
//        cartListRef.child(uID).updateChildren(Map);
//
//        //update the values of stock
//        mDatabase.child("business").child(key).child("products").child(product_key).updateChildren(productMap);
//        mDatabase.child("products").child(category).child(product_key).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task)
//            {
//                if (task.isSuccessful())
//                {
//                    pd.dismiss();
//                    Toast.makeText(SellerUseProductsActivity.this, "Done.", Toast.LENGTH_SHORT).show();
//                    Intent refreshIntent = getIntent();
//                    refreshIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    refreshIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(refreshIntent);
//                }
//                else {
//                    pd.dismiss();
//                    Toast.makeText(SellerUseProductsActivity.this, "error.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//    }


//    public void sendNotification(String pName,String pImage,String pPrice, String pQuantity)
//    {
//        HashMap<String, Object> Map = new HashMap<>();
//
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            this.finish();
        }
        return true;
    }


}
