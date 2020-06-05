package com.example.ecommerceproject.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceproject.Interface.ItemClickListner;
import com.example.ecommerceproject.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductPrice, txtProductQuantity,updatbtn,txtProductSeller;
    public ImageView imageView1;
    public CardView shipped;
    private ItemClickListner itemClickListner;
    public ProgressBar progressBar31;



    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
        txtProductSeller = itemView.findViewById(R.id.seller_name);
        imageView1 =  itemView.findViewById(R.id.cart_product_image);
        shipped = itemView.findViewById(R.id.shipped_item_id);
        updatbtn = itemView.findViewById(R.id.cartDelbtn);
        progressBar31 = itemView.findViewById(R.id.progressBar3);

    }


    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view, getAdapterPosition(), false);
    }


    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }
}
