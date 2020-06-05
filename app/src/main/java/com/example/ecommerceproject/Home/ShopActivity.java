package com.example.ecommerceproject.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.ecommerceproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    ViewFlipper imgBanner;
    private RecyclerView mRecyclerView, cRecyclerView;
    private PopularAdapter mAdapter;
    private CompanyViewHolder cAdapter;
    RecyclerView.LayoutManager layoutManager;

    private DatabaseReference mDatabaseReference, CompanyRef;
    private List<Popular> mPopulars;
    private List<Companies> companies;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        imgBanner = findViewById(R.id.imgBanner);

        int sliders[] = {
                R.drawable.banner1, R.drawable.applogo, R.drawable.banner3
        };

        for(int slide:sliders)
        {
            bannerFliper(slide);
        }

        showPopularProducts();
        showCompanies();
    }

//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//        FirebaseRecyclerOptions<Companies> options =
//                new FirebaseRecyclerOptions.Builder<Companies>()
//                .setQuery(CompanyRef, Companies.class)
//                .build();
//
//        FirebaseRecyclerAdapter<Companies, CompanyViewHolder> adapter =
//                new FirebaseRecyclerAdapter<Companies, CompanyViewHolder>(options)
//                {
//                    @Override
//                    protected void onBindViewHolder(@NonNull CompanyViewHolder holder, int i, @NonNull Companies model)
//                    {
//                        holder.txtCompanyName.setText(model.getcName());
//                        holder.TxtCompanyOwner.setText(model.getcOwner());
//                        holder.TxtCompanyPhone.setText(model.getcNumber());
//                        holder.TxtCompanyEmail.setText(model.getcEmail());
//                    }
//
//                    @NonNull
//                    @Override
//                    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false);
//                        CompanyViewHolder holder = new CompanyViewHolder(view);
//                        return holder;
//                    }
//                };
//        cRecyclerView.setAdapter(adapter);
//        adapter.startListening();
//    }

    private void showPopularProducts()
    {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mPopulars = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Products");
        mDatabaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Popular popular = postSnapshot.getValue(Popular.class);
                    mPopulars.add(popular);
                }

                mAdapter = new PopularAdapter(ShopActivity.this, mPopulars);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(ShopActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCompanies()
    {
        cRecyclerView = findViewById(R.id.company_list_id);
        cRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(layoutManager);

        companies = new ArrayList<>();
        CompanyRef = FirebaseDatabase.getInstance().getReference().child("business");
        CompanyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Companies companies1 = postSnapshot.getValue(Companies.class);
                    companies.add(companies1);
                }

                cAdapter = new CompanyViewHolder(ShopActivity.this, companies);
                cRecyclerView.setAdapter(cAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(ShopActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void bannerFliper(int image)
    {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(image);
        imgBanner.addView(imageView);
        imgBanner.setFlipInterval(6000);
        imgBanner.setAutoStart(true);
        imgBanner.setInAnimation(this, android.R.anim.fade_in);
        imgBanner.setOutAnimation(this, android.R.anim.fade_out);

    }
}
