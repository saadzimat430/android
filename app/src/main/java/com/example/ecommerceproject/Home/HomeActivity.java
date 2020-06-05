package com.example.ecommerceproject.Home;

import android.os.Bundle;

import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;

import android.view.View;
import android.content.Intent;

import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.example.ecommerceproject.Sellers.AboutActivity;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import android.view.Menu;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
     implements NavigationView.OnNavigationItemSelectedListener
    {


        private DatabaseReference ProductsRef;
        private RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        private String type= ""  ;
        private ImageView settingsbtn, orderIV, imageView7;

        private Toolbar cattoolbar;

        private ImageView  imageView16;
        private TextView textView, textView2, textView3, tvName,textView35,textView32,textView33,textView34,textView31;

        private FirebaseAuth mAuth;
        private FirebaseUser currentUser;
        private DatabaseReference mDatabase;

        private String user_id;
        private String ds_company_name;

        private String name;
        private String email;
        private String phone;
        private int count = 0;
        private int count1 = 0;
        NotificationBadge badge;
        NotificationBadge badge1, badge2;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            user_id = Prevalent.currentOnlineUser.getPhone();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("eCommerce");
            setSupportActionBar(toolbar);



            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();


            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            View headerView = navigationView.getHeaderView(0);
            TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
            CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

            userNameTextView.setText(Prevalent.currentOnlineUser.getName());
            Picasso.get().load(Prevalent.currentOnlineUser.getImage())
                    .placeholder(R.drawable.img_vide)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.profile).into(profileImageView);


            TextView userNameTextView1 = findViewById(R.id.user_profile_name1);
            CircleImageView profileImageView1 = findViewById(R.id.profile1);

            userNameTextView1.setText(Prevalent.currentOnlineUser.getName());
            Picasso.get().load(Prevalent.currentOnlineUser.getImage())
                    .placeholder(R.drawable.img_vide)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.profile).into(profileImageView1);

    //        if (!type.equals("Admin"))
    //        {
    //            userNameTextView.setText(Prevalent.currentOnlineUser.getName());
    //            Picasso.get().load(Prevalent.currentOnlineUser.getImage())
    //                    .placeholder(R.drawable.img_vide)
    //                    .fit()
    //                    .centerCrop()
    //                    .placeholder(R.drawable.profile).into(profileImageView);
    //        }
            textView = findViewById(R.id.textView);
            textView2 = findViewById(R.id.textView2);
            textView3 = findViewById(R.id.textView3);

            tvName = findViewById(R.id.tvName);
            imageView16 = findViewById(R.id.imageView16);
            //orderIV = findViewById(R.id.orderIV);
            //imageView7 = findViewById(R.id.imageView7);
            textView35 = findViewById(R.id.textView35);
            textView32 = findViewById(R.id.textView32);
            textView33 = findViewById(R.id.textView33);
            textView34 = findViewById(R.id.textView34);
            textView31 = findViewById(R.id.textView31);
            //orderIV = findViewById(R.id.orderIV);

            settingsbtn = findViewById(R.id.settingsbtn);



            textView31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productMainListIntent = new Intent(HomeActivity.this, ProductMainSubListActivity.class);
                    productMainListIntent.putExtra("watches", "watches");
                    startActivity(productMainListIntent);
                }
            });

            textView34.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productMainListIntent = new Intent(HomeActivity.this, ProductMainSubListActivity.class);
                    productMainListIntent.putExtra("shoes", "shoes");
                    startActivity(productMainListIntent);
                }
            });

            textView33.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productMainListIntent = new Intent(HomeActivity.this, ProductMainSubListActivity.class);
                    productMainListIntent.putExtra("men dresses", "men dresses");
                    startActivity(productMainListIntent);
                }
            });

            textView32.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productMainListIntent = new Intent(HomeActivity.this, ProductMainSubListActivity.class);
                    productMainListIntent.putExtra("female dresses", "female dresses");
                    startActivity(productMainListIntent);
                }
            });

            textView35.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productMainListIntent = new Intent(HomeActivity.this, ProductMainSubListActivity.class);
                    productMainListIntent.putExtra("laptops", "laptops");
                    startActivity(productMainListIntent);
                }
            });

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productMainListIntent = new Intent(HomeActivity.this, ProductMainSubListActivity.class);
                    productMainListIntent.putExtra("electronics", "electronics");
                    startActivity(productMainListIntent);
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productMainListIntent = new Intent(HomeActivity.this, ProductMainSubListActivity.class);
                    productMainListIntent.putExtra("books", "books");
                    startActivity(productMainListIntent);
                }
            });
            textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productMainListIntent = new Intent(HomeActivity.this, ProductMainSubListActivity.class);
                    productMainListIntent.putExtra("mobile phones", "mobile phones");
                    startActivity(productMainListIntent);
                }
            });

            settingsbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent settingsIntent = new Intent(HomeActivity.this, UserSettingsActivity.class);
                    startActivity(settingsIntent);
                }
            });


        }


        @Override
        public void onBackPressed()
        {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu)
        {
                View view1, view, view2;
                getMenuInflater().inflate(R.menu.menu3,menu);
                view1 = menu.findItem(R.id.notification_orders_id).getActionView();
                badge1 = (NotificationBadge) view1.findViewById(R.id.badge3);
                updateOrders();


                view = menu.findItem(R.id.notification_id3).getActionView();
                badge = (NotificationBadge) view.findViewById(R.id.badge2);
                updateCart();


                view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent cartIntent = new Intent(HomeActivity.this, CartActivity.class);
                        startActivity(cartIntent);

                    }
                });


                view1.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent orderIntent = new Intent(HomeActivity.this, NotificationActivity.class);

                        startActivity(orderIntent);

                    }
                });


                return true;
            }


        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item)
        {
    //            int id = item.getItemId();
    //            if (id == R.id.notification_id3)
    //            {
    //                Intent orderIntent = new Intent(HomeActivity.this, CartActivity.class);
    //                startActivity(orderIntent);
    //            }
    //

                return true;
            }


        @SuppressWarnings("StatementWithEmptyBody")

        public boolean onNavigationItemSelected(MenuItem item)
        {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_cart)
            {
                if (!type.equals("Admin")) {
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                }
            }
            else if (id == R.id.nav_orders)
            {
                Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
                startActivity(intent);
            }
            else if (id == R.id.nav_wishlist)
            {
                Intent intent = new Intent(HomeActivity.this, WishListActivity.class);
                startActivity(intent);
            }
            else if (id == R.id.nav_share)
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "eCommerce";
                String shareSub = "Description";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                startActivity(Intent.createChooser(intent, "Share eCommerce application"));
            }
            else if (id == R.id.nav_about)
            {
                Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intent);
            }
            else if (id == R.id.nav_help)
            {

                Intent intent = new Intent(HomeActivity.this, HelpCenterActivity.class);
                startActivity(intent);
                finish();

            }

            else if (id == R.id.nav_categories)
            {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
            else if (id == R.id.nav_settings)
            {
                if (!type.equals("Admin")) {
                    Intent intent = new Intent(HomeActivity.this, UserSettingsActivity.class);
                    startActivity(intent);
                }
            }
            else if (id == R.id.nav_logout)
            {
                if (!type.equals("Admin")) {
                    Paper.book().destroy();

                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        private void updateCart()
        {
                if (badge==null) return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference().child("cart").child(user_id);
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                count1 = (int) dataSnapshot.getChildrenCount();
                                if (count1==0)
                                {
                                    badge.setVisibility(View.INVISIBLE);
                                }else
                                {
                                    //count = (int) dataSnapshot.getChildrenCount();
                                    badge.setVisibility(View.VISIBLE);
                                    badge.setText(String.valueOf(count1));
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });
            }

         private void updateWishList()
         {
                if (badge2==null) return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference().child("wishList").child(user_id);
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                count1 = (int) dataSnapshot.getChildrenCount();
                                if (count1==0)
                                {
                                    badge2.setVisibility(View.INVISIBLE);
                                }else
                                {
                                    //count = (int) dataSnapshot.getChildrenCount();
                                    badge2.setVisibility(View.VISIBLE);
                                    badge2.setText(String.valueOf(count1));
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });
            }

        private void updateOrders()
        {
                if (badge1==null) return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        Query myRef = database.getReference().child("orders").child(user_id).child("products").orderByChild("state").equalTo("this product is delivered");
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                count = (int) dataSnapshot.getChildrenCount();
                                if (count==0)
                                {
                                    badge1.setVisibility(View.INVISIBLE);
                                }else
                                {
                                    //count = (int) dataSnapshot.getChildrenCount();
                                    badge1.setVisibility(View.VISIBLE);
                                    badge1.setText(String.valueOf(count));
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });
            }


        @Override
        protected void onRestart()
        {
            super.onRestart();
            updateCart();
            updateOrders();
        }



}
