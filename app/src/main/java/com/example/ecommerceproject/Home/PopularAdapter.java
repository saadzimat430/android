package com.example.ecommerceproject.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceproject.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ImageViewHolder>
{

    private Context mContext;
    private List<Popular> mPopular;

    public PopularAdapter (Context context, List<Popular> populars)
    {
        mContext = context;
        mPopular = populars;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(mContext).inflate(R.layout.popular_item, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i)
    {
        Popular popularCur = mPopular.get(i);
        imageViewHolder.prod_name.setText(popularCur.getPname());
        imageViewHolder.prod_price.setText(popularCur.getPrice() + "$");

        Picasso.get().load(popularCur.getImage())
                .centerCrop()
                .fit()
                .into(imageViewHolder.prod_img);
    }

    @Override
    public int getItemCount() {
        return mPopular.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView prod_name, prod_price;
        public ImageView prod_img;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            prod_name = itemView.findViewById(R.id.prodName);
            prod_price = itemView.findViewById(R.id.prodPrice);
            prod_img = itemView.findViewById(R.id.prodImage);
        }
    }
}
