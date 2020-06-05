package com.example.ecommerceproject.Home.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceproject.Home.Models.Order;
import com.example.ecommerceproject.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>
{
    private List<Order> orderList;
    private Context mContext;

    public OrderAdapter(List<Order> orderList, Context mContext) {
        this.orderList = orderList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Order order = orderList.get(i);
        //Picasso.get().load(order.getProduct_image()).into(viewHolder.imageView10);
        viewHolder.product_name.setText(order.getProduct_name());
        viewHolder.product_price.setText("MAD " + order.getProduct_price());
        viewHolder.seller_name.setText("by " + order.getSeller_name());
        viewHolder.quantity.setText("Quantity: " + order.getQuantity());
        viewHolder.state.setText("state: " + order.getState());
        viewHolder.product_description.setText(order.getProduct_description());
        Picasso.get().load(order.getProduct_image()).into(viewHolder.imageView10, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.progressBar3.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView10;
        private TextView product_name, product_price, product_description, seller_name, quantity, state;
        private CardView productcv;
        private ProgressBar progressBar3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView10 = itemView.findViewById(R.id.imageView10);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_description = itemView.findViewById(R.id.product_description);
            seller_name = itemView.findViewById(R.id.seller_name);
            productcv = itemView.findViewById(R.id.productcv);
            progressBar3 = itemView.findViewById(R.id.progressBar3);
            quantity = itemView.findViewById(R.id.quantity_id);
            state = itemView.findViewById(R.id.state_id);
        }
    }
}
