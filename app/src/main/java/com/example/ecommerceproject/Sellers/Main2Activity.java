package com.example.ecommerceproject.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceproject.Home.MainActivity;
import com.example.ecommerceproject.R;
import com.google.android.material.tabs.TabLayout;
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

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity {

    //BottomNavigationView  bottomNavigationView;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private TextView textView42;

    private ImageView settingsbtn;
    private TextView tvName;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private String ds_company_name;

    private Toolbar toolbar;

    private String user_id;
    private String name, phone;

    private String email;
    private int count = 0;

    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final Toolbar toolbar = findViewById(R.id.seller_toolbar);
        setSupportActionBar(toolbar);

        tvName = findViewById(R.id.tvName);
        textView42 = findViewById(R.id.textView42);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        user_id = currentUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (currentUser == null) {
            sendToStart();
        } else {
            if (!mAuth.getCurrentUser().isEmailVerified()) {
                Toast.makeText(getApplicationContext(), "Please verify your email address", Toast.LENGTH_LONG).show();
                Intent startActivity = new Intent(Main2Activity.this, EmailVerifyActivity.class);
                startActivity(startActivity);
                finish();
            } else {

                user_id = currentUser.getUid();
                checkUserDetails();

            }
        }
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewPager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        adapter.AddFragment(new HomeFragment(),"Your Company");
        adapter.AddFragment(new AddFragment(),"add Products");
        adapter.AddFragment(new CheckFragment(),"check Orders");

        settingsbtn = findViewById(R.id.settingsbtn);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(Main2Activity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });


        mDatabase.child("Sellers").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("name").exists()) {
                    name = dataSnapshot.child("name").getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }








    //verifivation du phone et gmail.
    private void checkVerification()
    {
        mDatabase.child("Sellers").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean email = (boolean) dataSnapshot.child("email").getValue();
                boolean phone = (boolean) dataSnapshot.child("phone").getValue();

                if (email == false) {
                    Intent emailIntent = new Intent(Main2Activity.this, EmailVerifyActivity.class);
                    startActivity(emailIntent);
                    finish();
                } else if (phone == false) {
                    Intent phoneIntent = new Intent(Main2Activity.this, PhoneVerifyActivity.class);
                    startActivity(phoneIntent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkUserDetails()
    {
        mDatabase.child("Sellers").child(user_id).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "Add your details first", Toast.LENGTH_LONG).show();
                    sendToAddDetails();
                }
                else {
                    if (!dataSnapshot.child("email").exists()) {
                        Toast.makeText(getApplicationContext(), "Please login again", Toast.LENGTH_LONG).show();
                        mAuth.signOut();
                        Intent detailsActivity = new Intent(Main2Activity.this, SellerLoginActivity.class);
                        startActivity(detailsActivity);
                        finish();
                    } else if (!dataSnapshot.child("name").exists()) {
                        Toast.makeText(getApplicationContext(), "Add your name first", Toast.LENGTH_LONG).show();
                        sendToEdit();
                    } else if (!dataSnapshot.child("phone").exists()) {
                        Toast.makeText(getApplicationContext(), "Add your phone first", Toast.LENGTH_LONG).show();
                        sendToEdit();
                    } else if (!dataSnapshot.child("address").exists()) {
                        Toast.makeText(getApplicationContext(), "Add your address first", Toast.LENGTH_LONG).show();

                    } else if(!dataSnapshot.child("verification").exists()) {
                        Map<String, Object> dataMap = new HashMap<>();
                        dataMap.put("email", false);
                        dataMap.put("phone", false);
                        mDatabase.child("Sellers").child(user_id).child("verification").updateChildren(dataMap);
                    } else {
                        getUserDetails();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




//ajouter les details de fournisseurs


    private void getUserDetails()
    {
        mDatabase.child("Sellers").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("name").exists()) {
                    name = dataSnapshot.child("name").getValue().toString();
                }
                if (dataSnapshot.child("email").exists()) {
                    email = dataSnapshot.child("email").getValue().toString();
                }
                if (dataSnapshot.child("phone").exists()) {
                    phone = dataSnapshot.child("phone").getValue().toString();
                }

                if (dataSnapshot.child("image").exists())
                {
                    CircleImageView profileImageView = findViewById(R.id.imageView6);
                    String image = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(image)
                            .placeholder(R.drawable.img_vide)
                            .fit()
                            .centerCrop()
                            .placeholder(R.drawable.profile).into(profileImageView);
                    tvName.setText(name);
                }
                businessActivity();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void businessActivity()
    {
        mDatabase.child("business").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        final String ds_email = ds.child("email").getValue().toString();
                        if (ds_email.equals(email)) {
                            if (ds.child("company_name").exists()) {
                                ds_company_name = ds.child("company_name").getValue().toString();
                            }

                            final String key = ds.getKey();
                            settingsbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent settingsIntent = new Intent(Main2Activity.this, SettingsActivity.class);
                                    settingsIntent.putExtra("ds_email", ds_email);
                                    settingsIntent.putExtra("ds_company_name", ds_company_name);
                                    settingsIntent.putExtra("key", key);
                                    startActivity(settingsIntent);
                                }
                            });

                        }


                    }
                } else
                    {
                        return;
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void sendToAddDetails()
    {
        Intent detailsActivity = new Intent(Main2Activity.this, AddSellerDetailsActivity.class);
        startActivity(detailsActivity);
        finish();
    }

    private void sendToEdit()
    {
        Intent editIntent = new Intent(Main2Activity.this, EditUserInfoActivity.class);
        startActivity(editIntent);
        finish();
    }

    private void sendToStart ()
    {
        Intent registerIntent = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(registerIntent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        View view = menu.findItem(R.id.notification_id).getActionView();
        badge = (NotificationBadge) view.findViewById(R.id.badge);
        updateOrders1();

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LayoutInflater layoutInflater = getLayoutInflater();
                View viewLyout;
                viewLyout = layoutInflater.inflate(R.layout.ztoast_layout1, (ViewGroup)findViewById(R.id.custom_layout1));
                TextView textView = viewLyout.findViewById(R.id.textView55);
                Toast toast = Toast.makeText(getApplicationContext(),"Toast:Gravity.TOP",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                textView.setText("You have " + count + " orders to check out");
                toast.setView(viewLyout);
                toast.show();
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
//        int id = item.getItemId();
//       if (id == R.id.Settings_id)
//        {
//            Intent settingsIntent = new Intent(Main2Activity.this, EditInfoActivity.class);
//            startActivity(settingsIntent);
//        }

        return true;
    }


    private void updateOrders1()
    {
        if (badge==null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                user_id = currentUser.getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                Query myRef = database.getReference().child("orders").orderByChild("state").equalTo("not shipped");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        count = (int) dataSnapshot.getChildrenCount();
                        if (count==0)
                        {
                            badge.setVisibility(View.INVISIBLE);
                        }else
                        {
                            badge.setVisibility(View.VISIBLE);
                            badge.setText(String.valueOf(count));
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
    protected void onRestart() {
        super.onRestart();
        updateOrders1();
    }
}
