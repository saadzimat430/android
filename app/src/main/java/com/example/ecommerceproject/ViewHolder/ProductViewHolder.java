package com.example.ecommerceproject.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecommerceproject.Interface.ItemClickListner;

import com.example.ecommerceproject.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imageView;
    public TextView txtProductName, txtProductPrice, txtProductDesc, tvSellerName;
    public CardView productcv;
    public ProgressBar progressBar3;
    public Button button14, button15;
    public ItemClickListner listner;


    public ProductViewHolder(View itemView)
    {
        super(itemView);

        imageView = itemView.findViewById(R.id.productIV);
        txtProductName = itemView.findViewById(R.id.tvProductName);
        txtProductPrice = itemView.findViewById(R.id.tvProductPrice);
        txtProductDesc = itemView.findViewById(R.id.tvProductDesc);
        tvSellerName = itemView.findViewById(R.id.tvSellerName);
        productcv = itemView.findViewById(R.id.productcv);
        button14 = itemView.findViewById(R.id.button141);
        button15 = itemView.findViewById(R.id.button15);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
