package com.example.ecommerceproject.Sellers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ecommerceproject.R;
import com.example.ecommerceproject.Sellers.Company.CompanyAddProductActivity;

public class AddFragment extends Fragment
{
    View v ;
    private LinearLayout l1;
    private ImageView tShirts, sportsTShirts, femaleDresses, sweathers;
    private ImageView glasses, hatsCaps, walletsBagsPurses, shoes;
    private ImageView headPhonesHandFree, Laptops, watches, mobilePhones;

    public AddFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_seller_category,container, false);

        l1 = v.findViewById(R.id.lcat_id);

        tShirts = v.findViewById(R.id.t_shirts);
        sportsTShirts = v.findViewById(R.id.sports_t_shirts);
        femaleDresses = v.findViewById(R.id.female_dresses);
        sweathers = v.findViewById(R.id.sweathers);

        glasses =v.findViewById(R.id.glasses);
        hatsCaps = v.findViewById(R.id.hats_caps);
        walletsBagsPurses = v.findViewById(R.id.purses_bags_wallets);
        shoes = v.findViewById(R.id.shoes);

        headPhonesHandFree =v.findViewById(R.id.headphones_handfree);
        Laptops = v. findViewById(R.id.laptop_pc);
        watches =v. findViewById(R.id.watches);
        mobilePhones =v. findViewById(R.id.mobilephones);

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), CompanyAddProductActivity.class);
                //intent.putExtra("category", "tShirts");
                startActivity(intent);
            }
        });

//        sportsTShirts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getActivity(), SellerAddNewProductActivity.class);
//                intent.putExtra("category", "Sports tShirts");
//                startActivity(intent);
//            }
//        });
//
//
//        femaleDresses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getActivity(), SellerAddNewProductActivity.class);
//                intent.putExtra("category", "Female Dresses");
//                startActivity(intent);
//            }
//        });
//
//
//        sweathers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getActivity(), SellerAddNewProductActivity.class);
//                intent.putExtra("category", "Sweathers");
//                startActivity(intent);
//            }
//        });
//
//
//        glasses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getActivity(), SellerAddNewProductActivity.class);
//                intent.putExtra("category", "Glasses");
//                startActivity(intent);
//            }
//        });
//
//
//        hatsCaps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getActivity(), SellerAddNewProductActivity.class);
//                intent.putExtra("category", "Hats Caps");
//                startActivity(intent);
//            }
//        });
//
//
//
//        walletsBagsPurses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getActivity(), SellerAddNewProductActivity.class);
//                intent.putExtra("category", "Wallets Bags Purses");
//                startActivity(intent);
//            }
//        });
//
//
//        shoes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getActivity(), SellerAddNewProductActivity.class);
//                intent.putExtra("category", "Shoes");
//                startActivity(intent);
//            }
//        });
//
//
//
//        headPhonesHandFree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getActivity(), SellerAddNewProductActivity.class);
//                intent.putExtra("category", "HeadPhones HandFree");
//                startActivity(intent);
//            }
//        });
//
//
//        Laptops.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getActivity(), SellerAddNewProductActivity.class);
//                intent.putExtra("category", "Laptops");
//                startActivity(intent);
//            }
//        });
//
//
//        watches.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getActivity(), SellerAddNewProductActivity.class);
//                intent.putExtra("category", "Watches");
//                startActivity(intent);
//            }
//        });
//
//
//        mobilePhones.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getActivity(), SellerAddNewProductActivity.class);
//                intent.putExtra("category", "Mobile Phones");
//                startActivity(intent);
//            }
//        });

        return v;

    }

}
