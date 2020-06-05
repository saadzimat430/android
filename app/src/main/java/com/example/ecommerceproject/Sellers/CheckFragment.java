package com.example.ecommerceproject.Sellers;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ecommerceproject.Buyers.CartActivity;
import com.example.ecommerceproject.Model.AdminOrders;
import com.example.ecommerceproject.Model.Cart;
import com.example.ecommerceproject.Model.CompayOrders;
import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.example.ecommerceproject.Sellers.Company.UserLocation;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CheckFragment extends Fragment
{
    View v;
    private RecyclerView orderList;
    private DatabaseReference ordersRef, productsordersRef;
    private String userShippingAddress = null;
    private int total_product_count1=0;
    private ProgressDialog loadingBar;
    private LottieAnimationView lottieAnimationView;

    public CheckFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.seller_check_orders,container, false);
        ordersRef = FirebaseDatabase.getInstance().getReference().child("orders");

        CheckOrderState();
        loadingBar = new ProgressDialog(getActivity());
        lottieAnimationView = v.findViewById(R.id.empty_notification2);
        orderList = v.findViewById(R.id.order_list1);
        orderList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }


    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseRecyclerOptions<CompayOrders> options =
                new FirebaseRecyclerOptions.Builder<CompayOrders>()
                        .setQuery(ordersRef.orderByChild("state").equalTo("not shipped"), CompayOrders.class)
                        .build();
        FirebaseRecyclerAdapter<CompayOrders, SellerNewOrdersActivity.AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<CompayOrders, SellerNewOrdersActivity.AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final SellerNewOrdersActivity.AdminOrdersViewHolder holder, final int position, @NonNull final CompayOrders model) {
                        holder.username.setText( model.getName());
                        holder.userPhoneNumber.setText( model.getPhone());
                        holder.userTotalPrice.setText("MAD " + model.getTotal_price() );
                        holder.userDateTime.setText(model.getDate() +" at :" +model.getTime() );
                        holder.userShippingAddress.setText( model.getAddress());

                        holder.imgcall.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String num;
                                num = model.getUser_id();
                                Intent i = new Intent(Intent.ACTION_CALL);

                                i.setData(Uri.parse("tel:" +num));
                                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                                {
                                    Toast.makeText(getActivity(),"Please grant the permission to call .", Toast.LENGTH_SHORT).show();
                                    requestPermission();
                                }else
                                {
                                    startActivity(i);
                                }

                            }
                            private void requestPermission()
                            {
                                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE}, 1);
                            }
                        });


                        holder.showOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {

                                Intent intent = new Intent(getActivity(), SellerUseProductsActivity.class);

                                String uID = getRef(position).getKey();
                                String Companyname = model.getSeller_name();

                                //get the specific phone number when showOrdersBtn is clicked
                                intent.putExtra("uid",uID);
                                intent.putExtra("companyName", Companyname);

                                startActivity(intent);
                            }
                        });

                        holder.location.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(getActivity(), UserLocation.class);
                                String uID = getRef(position).getKey();
                                //get the specific location when locationLayout is clicked
                                intent.putExtra("uid",uID);
                                intent.putExtra("name",model.getName());
                                intent.putExtra("phone",model.getPhone());
                                intent.putExtra("address",model.getAddress());
                                intent.putExtra("total_price",model.getTotal_price());
                                intent.putExtra("location",holder.userShippingAddress.getText().toString() );
                                startActivity(intent);
                            }

                        });

                        holder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Have You Shipped This Order Products ? ");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        if (i==0)
                                        {
                                            String uID = getRef(position).getKey();
                                            HashMap<String, Object> Map = new HashMap<>();
                                            Map.put("state", "this order is delivered");
                                            ordersRef.child(uID).updateChildren(Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    Toast.makeText(getActivity(), "Done.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            //RemoveOrder(uID);
                                        }
                                        else
                                        {
                                            return;
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public SellerNewOrdersActivity.AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_orders_layout, parent, false);
                        return new SellerNewOrdersActivity.AdminOrdersViewHolder(view);
                    }
                };
        orderList.setAdapter(adapter);
        adapter.startListening();
    }


    private void CheckOrderState()
    {
        Query ordersRef ;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("orders").orderByChild("state").equalTo("not shipped");

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {

                    lottieAnimationView.setVisibility(View.INVISIBLE);
                }
                else if (!dataSnapshot.exists())
                {

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //remove shipped orders
    private void RemoveOrder(String uID)
    {

        ordersRef.child(uID).removeValue();
    }

    //delete product method
//    private void deleteThisProduct(String uID)
//    {
//
//        productsordersRef.child("Admin View").child(uID)
//                .child("Products")
//                .removeValue()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task)
//                    {
//                        if (task.isSuccessful())
//                        {
//                            Toast.makeText(getActivity(),"Item removed successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
}
