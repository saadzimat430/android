package com.example.ecommerceproject.Home.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceproject.Home.Models.WishList;
import com.example.ecommerceproject.Home.ProductActivity;
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

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder>
{
    private List<WishList> wishlist;
    private Context mContext;

    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    public WishListAdapter(List<WishList> wishlist, Context mContext) {
        this.wishlist = wishlist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item, viewGroup, false);
        return new WishListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.cartDelbtn.setVisibility(View.VISIBLE);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        final WishList wish = wishlist.get(i);
        //Picasso.get().load(wish.getProduct_image()).into(viewHolder.imageView10);
        viewHolder.product_name.setText(wish.getProduct_name());
        viewHolder.product_price.setText("MAD " + wish.getProduct_price());
        viewHolder.seller_name.setText("by " + wish.getSeller_name());
        viewHolder.product_description.setText(wish.getProduct_description());
        viewHolder.state.setVisibility(View.INVISIBLE);
        viewHolder.quantity.setVisibility(View.INVISIBLE);
        Picasso.get().load(wish.getProduct_image()).into(viewHolder.imageView10, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.progressBar3.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        viewHolder.productcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productIntent = new Intent(mContext, ProductActivity.class);
                productIntent.putExtra("product_key", wish.getProduct_key());
                productIntent.putExtra("product_category", wish.getProduct_category());
                productIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(productIntent);
            }
        });

        viewHolder.cartDelbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Remove product");
                    builder.setMessage("Are you sure you want to Remove this product from your wishList it?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDatabase.child("wishList").child(Prevalent.currentOnlineUser.getPhone()).child(wish.getCart_key()).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(mContext, "Product Removed successfully", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(mContext, "Requires android version 5.0 and up", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView10;
        private TextView product_name, product_price, product_description, seller_name, state, quantity;
        private CardView productcv;
        private ImageView cartDelbtn;
        private ProgressBar progressBar3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView10 = itemView.findViewById(R.id.imageView10);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_description = itemView.findViewById(R.id.product_description);
            seller_name = itemView.findViewById(R.id.seller_name);
            productcv = itemView.findViewById(R.id.productcv);
            cartDelbtn = itemView.findViewById(R.id.cartDelbtn);
            progressBar3 = itemView.findViewById(R.id.progressBar3);
            state = itemView.findViewById(R.id.state_id);
            quantity = itemView.findViewById(R.id.quantity_id);
        }
    }
}
