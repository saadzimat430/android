package com.example.ecommerceproject.Home.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.ecommerceproject.Home.OrderActivity;
import com.example.ecommerceproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>
{
    private List<Order> orderList;
    private Context mContext;
    private DatabaseReference mDatabase;

    public NotificationAdapter(List<Order> orderList, Context mContext) {
        this.orderList = orderList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_item_layout, viewGroup, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationAdapter.ViewHolder viewHolder, int i) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Order order = orderList.get(i);
        //Picasso.get().load(order.getProduct_image()).into(viewHolder.imageView10n);
        Picasso.get().load(order.getProduct_image()).into(viewHolder.imageView10n, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.progressBar3n.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        viewHolder.message.setText(" this Product is delivered By " +order.getSeller_name() + ". \n Please check your orders List" );
        viewHolder.notifcv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent productIntent = new Intent(mContext, OrderActivity.class);
                productIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(productIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView10n, readNotif;
        private TextView message;
        private ProgressBar progressBar3n;
        private CardView notifcv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView10n = itemView.findViewById(R.id.imageView10n);
            notifcv = itemView.findViewById(R.id.notifcv);
            message = itemView.findViewById(R.id.message_id);
            readNotif = itemView.findViewById(R.id.readNotif);
            progressBar3n = itemView.findViewById(R.id.progressBar3n);

        }
    }
}
