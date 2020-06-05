package com.example.ecommerceproject.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceproject.Home.Adapters.ProductsAdapter;
import com.example.ecommerceproject.Home.Models.Products;
import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.example.ecommerceproject.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchProductsActivity extends AppCompatActivity {

    private ImageView searchBtn;
    private EditText searchTxt;
    private RecyclerView searchList;
    private String searchInput;
    private EditText searchView;
    private DatabaseReference mDatabase;
    private Toolbar toolbar4;
    private TextView textView4;
    private ProductsAdapter productsAdapter;
    private List<Products> productsLists = new ArrayList<>();
    private Context mContext;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);

        Bundle bundle = getIntent().getExtras();
        final String category = bundle.getString("category");

        toolbar4 = findViewById(R.id.hometoolbar);


        searchView = findViewById(R.id.search_id);
        searchBtn = findViewById(R.id.search_btn_id);
        searchList = findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(SearchProductsActivity.this));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput = searchView.getText().toString();
                onStart();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        final String category = bundle.getString("category");
        user_id = Prevalent.currentOnlineUser.getPhone();
        mDatabase = FirebaseDatabase.getInstance().getReference();



        if (category != null) {

            setSupportActionBar(toolbar4);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar4.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
            toolbar4.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products").child(category);

            FirebaseRecyclerOptions<Products> options =
                    new FirebaseRecyclerOptions.Builder<Products>()
                            .setQuery(reference.orderByChild("product_name").startAt(searchInput).endAt(searchInput + "\uf8ff"), Products.class)
                            .build();

            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ProductViewHolder viewHolder, int position, @NonNull final Products model) {
                            Picasso.get().load(model.getProduct_image()).into(viewHolder.imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                    //viewHolder.progressBar3.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });
                            viewHolder.txtProductName.setText(model.getProduct_name());
                            viewHolder.txtProductPrice.setText("MAD " + model.getProduct_price());
                            viewHolder.txtProductDesc.setText(model.getProduct_description());
                            viewHolder.tvSellerName.setText("by " + model.getCompany_name());


                            viewHolder.productcv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent productIntent = new Intent(SearchProductsActivity.this, ProductActivity.class);
                                    productIntent.putExtra("product_key", model.getProduct_key());
                                    productIntent.putExtra("product_category", model.getProduct_category());
                                    productIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(productIntent);
                                }
                            });

                            viewHolder.button14.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent productIntent = new Intent(SearchProductsActivity.this, AddDeliveryDetails.class);
                                    //productIntent.putExtra("product_key", );
                                    productIntent.putExtra("product_name2", model.getProduct_name());
                                    productIntent.putExtra("product_price2", model.getProduct_price());
                                    productIntent.putExtra("product_image2", model.getProduct_image());
                                    productIntent.putExtra("product_description2", model.getProduct_description());
                                    productIntent.putExtra("seller_name2", model.getCompany_name());
                                    productIntent.putExtra("from_cart", "no");
                                    startActivity(productIntent);
                                }
                            });

                            viewHolder.button15.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String cart_key = mDatabase.child("cart").child(user_id).push().getKey();
                                    HashMap<String, Object> dataMap = new HashMap<>();
                                    dataMap.put("product_name", model.getProduct_name());
                                    dataMap.put("product_price", model.getProduct_price());
                                    dataMap.put("product_image", model.getProduct_image());
                                    dataMap.put("seller_name", model.getCompany_name());
                                    dataMap.put("cart_key", cart_key);
                                    dataMap.put("product_description", model.getProduct_description());
                                    mDatabase.child("cart").child(user_id).child(cart_key).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Vibrator vibrator = (Vibrator) SearchProductsActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                                            vibrator.vibrate(50);
                                            Toast.makeText(mContext, "Product added successfully to cart", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                            ProductViewHolder holder = new ProductViewHolder(view);
                            return holder;
                        }
                    };
            searchList.setAdapter(adapter);
            adapter.startListening();
        }
    }

}