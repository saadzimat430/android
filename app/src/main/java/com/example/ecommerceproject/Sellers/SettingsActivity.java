package com.example.ecommerceproject.Sellers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ecommerceproject.Home.MainActivity;
import com.example.ecommerceproject.R;
import com.example.ecommerceproject.Sellers.Company.BusinessRegisterActivity;
import com.example.ecommerceproject.Sellers.Company.CompanyHomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity
{

    private Toolbar settingstoolbar;

    private TextView tvSettingsLogout, tvSettingsCreateB, tvSettingsBAP, tvSettingsECI, textView15, textView11, textView55, tvSettingsVAP;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;

    private String b_email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        settingstoolbar = findViewById(R.id.settings_toolbar);
        settingstoolbar.setTitle("Settings");
        setSupportActionBar(settingstoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        settingstoolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        settingstoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        tvSettingsLogout = findViewById(R.id.tvSettingsLogout);
        tvSettingsCreateB = findViewById(R.id.tvSettingsCreateB);
//        tvSettingsBAP = findViewById(R.id.tvSettingsBAP);
        tvSettingsECI = findViewById(R.id.tvSettingsECI);
        textView15 = findViewById(R.id.textView15);
        textView11 = findViewById(R.id.textView11);
//        tvSettingsVAP = findViewById(R.id.tvSettingsVAP);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Sellers");
        currentUser = mAuth.getCurrentUser();


        if (currentUser == null) {
            sendToLogin();
        } else {
            String user_id = mAuth.getCurrentUser().getUid();
            Bundle b = getIntent().getExtras();
            if (b != null) {
                b_email = b.get("ds_email").toString();
                final String key = b.get("key").toString();
                //String b_company_name = b.get("ds_company_name").toString();


                tvSettingsCreateB.setText("Edit company info");
//                tvSettingsBAP.setVisibility(View.VISIBLE);
//                tvSettingsVAP.setVisibility(View.VISIBLE);
                textView11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent vapIntent = new Intent(SettingsActivity.this, EditUserInfoActivity.class);
                        vapIntent.putExtra("company_key", key);
                        startActivity(vapIntent);
                    }
                });

//                tvSettingsVAP.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent vapIntent = new Intent(EditInfoActivity.this, VAPActivity.class);
//                        vapIntent.putExtra("company_key", key);
//                        startActivity(vapIntent);
//                    }
//                });

            } else {
                tvSettingsCreateB.setVisibility(View.VISIBLE);
//                tvSettingsBAP.setVisibility(View.INVISIBLE);
//                tvSettingsVAP.setVisibility(View.INVISIBLE);
            }
            if (tvSettingsCreateB.getText().toString().equals("Create a new business account")) {
                tvSettingsCreateB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent businessIntent = new Intent(SettingsActivity.this, BusinessRegisterActivity.class);
                        startActivity(businessIntent);
                    }
                });
            } else {
                tvSettingsCreateB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent editIntent = new Intent(SettingsActivity.this, CompanyHomeActivity.class);
                        startActivity(editIntent);
                    }
                });

            }

//            tvSettingsBAP.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent addProductIntent = new Intent(EditInfoActivity.this, CompanyAddProductActivity.class);
//                    startActivity(addProductIntent);
//                }
//            });
            textView15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent aboutIntent = new Intent(SettingsActivity.this, AboutActivity.class);
                    startActivity(aboutIntent);
                }
            });


            tvSettingsLogout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    CharSequence options[] = new CharSequence[]
                            {
                                    "Yes",
                                    "No"
                            };
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setTitle("Are You Sure ? ");
                    builder.setItems(options, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            if (i==0)
                            {
                                mAuth.signOut();
                                Intent logoutIntent = new Intent(SettingsActivity.this, MainActivity.class);
                                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(logoutIntent);
                                finish();
                            }
                            if (i==1)
                            {
                                return;
                            }
                        }
                    });

                    builder.show();


                }
            });

//            textView55.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent burnabIntent = new Intent(EditInfoActivity.this, BunrabActivity.class);
//                    startActivity(burnabIntent);
//                }
//            });



        }

    }





    private void sendToLogin() {
        Intent loginIntent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
