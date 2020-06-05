package com.example.ecommerceproject.Home.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceproject.Home.AddDeliveryDetails;
import com.example.ecommerceproject.Home.ProductActivity;
import com.example.ecommerceproject.Home.Models.Products;
import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Products> productsList;
    private Context mContext;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    private String user_id;

    public ProductsAdapter(List<Products> productsList, Context mContext) {
        this.productsList = productsList;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_list_item2, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Products products = productsList.get(i);
        //viewHolder.progressBar3.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        user_id = Prevalent.currentOnlineUser.getPhone();

        Picasso.get().load(products.getProduct_image()).into(viewHolder.productIV, new Callback() {
            @Override
            public void onSuccess() {
                //viewHolder.progressBar3.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        viewHolder.tvProductName.setText(products.getProduct_name());
        viewHolder.tvProductPrice.setText("MAD " + products.getProduct_price());
        //viewHolder.tvProductDesc.setText(products.getProduct_description());
        viewHolder.tvSellerName.setText("by " + products.getCompany_name());
        //viewHolder.tvStock.setText("Only " + products.getQuantity_stock()+ " products left");


        viewHolder.productcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (products.getQuantity_stock().equals("0"))
                {
                    Toast.makeText(mContext, "Sorry, this product is no longer available.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent productIntent = new Intent(mContext, ProductActivity.class);
                    productIntent.putExtra("product_key", products.getProduct_key());
                    productIntent.putExtra("product_category", products.getProduct_category());
                    productIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(productIntent);
                }
            }
        });

        viewHolder.button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (products.getQuantity_stock().equals("0"))
                {
                    Toast.makeText(mContext, "Sorry, You can not buy this product.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent productIntent = new Intent(mContext, AddDeliveryDetails.class);
                    //productIntent.putExtra("product_key", );
                    productIntent.putExtra("product_name2", products.getProduct_name());
                    productIntent.putExtra("product_price2", products.getProduct_price());
                    productIntent.putExtra("product_image2", products.getProduct_image());
                    productIntent.putExtra("product_description2", products.getProduct_description());
                    productIntent.putExtra("seller_name2", products.getCompany_name());
                    productIntent.putExtra("product_key2", products.getProduct_key());
                    productIntent.putExtra("product_category2", products.getProduct_category());
                    productIntent.putExtra("quantity2", "1");
                    productIntent.putExtra("from_cart", "no");
                    mContext.startActivity(productIntent);
                }
            }
        });

        viewHolder.button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (products.getQuantity_stock().equals("0"))
                {
                    Toast.makeText(mContext, "Sorry, you can not add this product to the CartList", Toast.LENGTH_SHORT).show();
                }
                else {
                    String cart_key = mDatabase.child("cart").child(user_id).push().getKey();
                    HashMap<String, Object> dataMap = new HashMap<>();
                    dataMap.put("product_name", products.getProduct_name());
                    dataMap.put("product_price", products.getProduct_price());
                    dataMap.put("product_category", products.getProduct_category());
                    dataMap.put("product_image", products.getProduct_image());
                    dataMap.put("product_key", products.getProduct_key());
                    dataMap.put("seller_name", products.getCompany_name());
                    dataMap.put("quantity", "1");
                    dataMap.put("cart_key", cart_key);
                    dataMap.put("product_description", products.getProduct_description());
                    mDatabase.child("cart").child(user_id).child(cart_key).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(50);
                            Toast.makeText(mContext, "Product added successfully to cart", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productIV;
        private TextView tvProductName, tvProductPrice, tvProductDesc, tvSellerName,tvStock;
        private CardView productcv;
        private ProgressBar progressBar3;
        private Button button14, button15;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productIV = itemView.findViewById(R.id.productIV);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            //tvProductDesc = itemView.findViewById(R.id.tvProductDesc);
            tvSellerName = itemView.findViewById(R.id.tvSellerName);
            productcv = itemView.findViewById(R.id.productcv);
            button14 = itemView.findViewById(R.id.button141);
            button15 = itemView.findViewById(R.id.button15);
            //tvStock = itemView.findViewById(R.id.tvStock);
            //progressBar3 = itemView.findViewById(R.id.progressBar3);
        }
    }
}
