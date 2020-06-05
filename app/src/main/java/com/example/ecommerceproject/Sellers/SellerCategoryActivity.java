package com.example.ecommerceproject.Sellers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ecommerceproject.R;
import com.example.ecommerceproject.Sellers.Company.CompanyAddProductActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SellerCategoryActivity extends AppCompatActivity {

    private ImageView tShirts, sportsTShirts, femaleDresses, sweathers;
    private ImageView glasses, hatsCaps, walletsBagsPurses, shoes;
    private ImageView headPhonesHandFree, Laptops, watches, mobilePhones;

    private LinearLayout l1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_category);


        tShirts = (ImageView) findViewById(R.id.t_shirts);
        sportsTShirts = (ImageView) findViewById(R.id.sports_t_shirts);
        femaleDresses = (ImageView) findViewById(R.id.female_dresses);
        sweathers = (ImageView) findViewById(R.id.sweathers);

        glasses = (ImageView) findViewById(R.id.glasses);
        hatsCaps = (ImageView) findViewById(R.id.hats_caps);
        walletsBagsPurses = (ImageView) findViewById(R.id.purses_bags_wallets);
        shoes = (ImageView) findViewById(R.id.shoes);

        headPhonesHandFree = (ImageView) findViewById(R.id.headphones_handfree);
        Laptops = (ImageView) findViewById(R.id.laptop_pc);
        watches = (ImageView) findViewById(R.id.watches);
        mobilePhones = (ImageView) findViewById(R.id.mobilephones);

        l1 = findViewById(R.id.lcat_id);

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerCategoryActivity.this, CompanyAddProductActivity.class);
                //intent.putExtra("category", "tShirts");
                startActivity(intent);
            }
        });
//
//        tShirts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(SellerCategoryActivity.this, CompanyAddProductActivity.class);
////                intent.putExtra("category", "tShirts");
////                intent.putExtra("sID", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                startActivity(intent);
//            }
//        });
//
//        sportsTShirts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(SellerCategoryActivity.this, CompanyAddProductActivity.class);
////                intent.putExtra("category", "Sports tShirts");
////                intent.putExtra("sID", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                startActivity(intent);
//            }
//        });
//
//
//        femaleDresses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(SellerCategoryActivity.this, CompanyAddProductActivity.class);
////                intent.putExtra("category", "Female Dresses");
////                intent.putExtra("sID", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                startActivity(intent);
//            }
//        });
//
//
//        sweathers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(SellerCategoryActivity.this, CompanyAddProductActivity.class);
////                intent.putExtra("category", "Sweathers");
////                intent.putExtra("sID", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                startActivity(intent);
//            }
//        });
//
//
//        glasses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(SellerCategoryActivity.this, CompanyAddProductActivity.class);
////                intent.putExtra("category", "Glasses");
////                intent.putExtra("sID", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                startActivity(intent);
//            }
//        });
//
//
//        hatsCaps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(SellerCategoryActivity.this, CompanyAddProductActivity.class);
////                intent.putExtra("category", "Hats Caps");
////                intent.putExtra("sID", FirebaseAuth.getInstance().getCurrentUser().getUid());
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
//                Intent intent = new Intent(SellerCategoryActivity.this, CompanyAddProductActivity.class);
////                intent.putExtra("category", "Wallets Bags Purses");
////                intent.putExtra("sID", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                startActivity(intent);
//            }
//        });
//
//
//        shoes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(SellerCategoryActivity.this, CompanyAddProductActivity.class);
////                intent.putExtra("category", "Shoes");
////                intent.putExtra("sID", FirebaseAuth.getInstance().getCurrentUser().getUid());
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
//                Intent intent = new Intent(SellerCategoryActivity.this, CompanyAddProductActivity.class);
////                intent.putExtra("category", "HeadPhones HandFree");
////                intent.putExtra("sID", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                startActivity(intent);
//            }
//        });
//
//
//        Laptops.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(SellerCategoryActivity.this, CompanyAddProductActivity.class);
////                intent.putExtra("category", "Laptops");
////                intent.putExtra("sID", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                startActivity(intent);
//            }
//        });
//
//
//        watches.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(SellerCategoryActivity.this, CompanyAddProductActivity.class);
////                intent.putExtra("category", "Watches");
////                intent.putExtra("sID", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                startActivity(intent);
//            }
//        });
//
//
//        mobilePhones.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(SellerCategoryActivity.this, CompanyAddProductActivity.class);
////                intent.putExtra("category", "Mobile Phones");
////                intent.putExtra("sID", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                startActivity(intent);
//            }
//        });
    }
}
