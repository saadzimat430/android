package com.example.ecommerceproject.Sellers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceproject.Model.Products;
import com.example.ecommerceproject.R;
import com.example.ecommerceproject.Sellers.Company.AboutCompanyActivity;
import com.example.ecommerceproject.Sellers.Company.CompanyAddProductActivity;
import com.example.ecommerceproject.Sellers.Company.CompanyHomeActivity;
import com.example.ecommerceproject.Sellers.Company.EditCompanyInfoActivity;
import com.example.ecommerceproject.Sellers.Company.MapsActivity;
import com.example.ecommerceproject.Sellers.Company.VAPActivity;
import com.example.ecommerceproject.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment
{

    View v;
    private TextView cName, cAddress, cOwner;
    private LinearLayout  cProducts, cEdit,cInfo ;

    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private String user_id;


    private String company_name = null;
    private String owner_name = null;
    private String compnany_address = null;
    private String email_id = null;
    private String email = null;
    private String company_info = null;
    private String key = null;



    public HomeFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.seller_home,container,false);


        cName = v.findViewById(R.id.cName);
        cAddress = v.findViewById(R.id.cAddress);
        cOwner = v.findViewById(R.id.cOwner);
        cProducts = v.findViewById(R.id.shoppp);
        cEdit = v.findViewById(R.id.cEdit);
        cInfo = v.findViewById(R.id.com_info);


        cEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), EditCompanyInfoActivity.class);
                startActivity(intent);
            }
        });



        cProducts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), VAPActivity.class);
                intent.putExtra("company_key",key );
                startActivity(intent);
            }
        });


        cInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), AboutCompanyActivity.class);
                intent.putExtra("company_add",compnany_address );
                intent.putExtra("company_key",key );
                startActivity(intent);

            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        getUserDetails();



        return v;
    }

    private void getUserDetails() {
        mDatabase.child("Sellers").child(currentUser.getUid()).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                email_id = dataSnapshot.getValue().toString();
                getCompanyDetails();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getCompanyDetails() {
        mDatabase.child("business").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    email = ds.child("email").getValue().toString();
                    key = ds.getKey();
                    if (email_id.equals(email)) {
                        fetchCompanyDetails();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchCompanyDetails() {
        mDatabase.child("business").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    company_name = dataSnapshot.child("company_name").getValue().toString();
                    owner_name = dataSnapshot.child("owner_name").getValue().toString();
                    //phone_number = dataSnapshot.child("company_phone_number").getValue().toString();
                    compnany_address = dataSnapshot.child("company_address").getValue().toString();
                    displayDetails();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayDetails() {
        cOwner.setText("Owner: " +owner_name);
        cName.setText(company_name);
        cAddress.setText(compnany_address);

    }




//    public void onStart()
//    {
//        super.onStart();
//
//        FirebaseRecyclerOptions<Products> options =
//                new FirebaseRecyclerOptions.Builder<Products>()
//                        .setQuery(unverifiedProductsRef.orderByChild("sID").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()), Products.class).build();
//
//        FirebaseRecyclerAdapter<Products, ItemViewHolder> adapter =
//                new FirebaseRecyclerAdapter<Products, ItemViewHolder>(options)
//                {
//                    @Override
//                    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull final Products model)
//                    {
//                        holder.txtProductName.setText(model.getPname());
//                        holder.txtProductDescription.setText(model.getDescription());
//                        holder.txtProductState.setText("State : " +model.getProductState());
//                        holder.txtProductPrice.setText("$" + model.getPrice() );
//                        Picasso.get().load(model.getImage()).into(holder.imageView);
//
//                        holder.itemView.setOnClickListener(new View.OnClickListener()
//                        {
//                            @Override
//                            public void onClick(View view)
//                            {
//                                final String productID = model.getPid();
//
//                                CharSequence options[] =new CharSequence[]
//                                        {
//                                                "Yes",
//                                                "No"
//                                        };
//                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                                builder.setTitle("Do you want to Update this Product ?");
//                                builder.setItems(options, new DialogInterface.OnClickListener()
//                                {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int position)
//                                    {
//                                        //si l'admin est cliqué sur yes donc on va changer le statut du
//                                        // produit a approved pour le affiché dans l'accueil du client
//                                        if (position==0)
//                                        {
//                                            //DeleteProduct(productID);
//                                            Intent intent = new Intent(getActivity(), SellerMaintainProductsActivity.class);
//                                            // recuperer l'id du produit selectionné
//                                            intent.putExtra("sID", model.getPid());
//                                            startActivity(intent);
//                                        }
//                                        if (position==1)
//                                        {
//
//                                        }
//                                    }
//                                });
//                                builder.show();
//                            }
//                        });
//                    }
//
//                    @NonNull
//                    @Override
//                    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//                    {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view, parent, false);
//                        ItemViewHolder holder = new ItemViewHolder(view);
//                        return holder;
//                    }
//                };
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//    }
}
