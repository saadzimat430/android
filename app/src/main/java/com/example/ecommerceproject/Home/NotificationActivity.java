package com.example.ecommerceproject.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ecommerceproject.Home.Adapters.NotificationAdapter;
import com.example.ecommerceproject.Home.Models.Order;
import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity
{
    private Toolbar toolbarNotification;

    private TextView textView34n;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    private String user_id;
    private LottieAnimationView emptynotification;

    private RecyclerView notificationRV;
    private List<Order> notifList = new ArrayList<>();
    private NotificationAdapter mNotifAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mContext = getApplicationContext();

        toolbarNotification = findViewById(R.id.toolbarNotification);
        toolbarNotification.setTitle("Your Notifications");
        setSupportActionBar(toolbarNotification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarNotification.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbarNotification.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        notificationRV = findViewById(R.id.notificationRV);
        mNotifAdapter = new NotificationAdapter(notifList, mContext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        notificationRV.setLayoutManager(layoutManager);
        notificationRV.setAdapter(mNotifAdapter);
        textView34n = findViewById(R.id.textView34n);
        emptynotification = findViewById(R.id.empty_notificationn);


        user_id = Prevalent.currentOnlineUser.getPhone();
        mDatabase.child("orders").child(user_id).child("products").orderByChild("state").equalTo("this product is delivered").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    textView34n.setVisibility(View.INVISIBLE);
                    emptynotification.setVisibility(View.INVISIBLE);
                    Order order = dataSnapshot.getValue(Order.class);
                    notifList.add(order);
                    mNotifAdapter.notifyDataSetChanged();
                } else {
                    textView34n.setVisibility(View.VISIBLE);
                    emptynotification.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
