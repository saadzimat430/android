package com.example.ecommerceproject.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceproject.Interface.ItemClickListner;
import com.example.ecommerceproject.R;

import java.util.List;

public class CompanyViewHolder extends RecyclerView.Adapter<CompanyViewHolder.ImageViewHolder>
{
    private Context cContext;
    private List<Companies> companies;

    public ItemClickListner listner;

    public CompanyViewHolder(Context context, List<Companies> ccompanies) {
        cContext = context;
        companies = ccompanies;
    }



    @NonNull
    @Override
    public CompanyViewHolder.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(cContext).inflate(R.layout.company_item, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder.ImageViewHolder imageViewHolder, int i)
    {

        Companies comp = companies.get(i);
        imageViewHolder.txtCompanyName.setText(comp.getcName());
        imageViewHolder.txtCompanieOwner.setText(comp.getcOwner());
        imageViewHolder.txtCompanyPhone.setText(comp.getcNumber());
        imageViewHolder.txtCompanyEmail.setText(comp.getcEmail());


    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCompanyName, txtCompanieOwner, txtCompanyPhone,txtCompanyEmail;
        public ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCompanyName = (TextView) itemView.findViewById(R.id.company_name_details1);
            txtCompanieOwner = (TextView) itemView.findViewById(R.id.company_owner_id1);
            txtCompanyPhone = (TextView) itemView.findViewById(R.id.phone_id1);
            txtCompanyEmail = (TextView) itemView.findViewById(R.id.company_email_id1);
        }
    }
}
