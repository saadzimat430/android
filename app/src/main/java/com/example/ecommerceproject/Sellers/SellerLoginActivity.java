package com.example.ecommerceproject.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerLoginActivity extends AppCompatActivity {

    private Button sellerLoginBtn;
    private EditText  emailInput, passwordInput;
    private TextView textView70;
    private ProgressDialog loadingBar;
    private String parentDbName = "Sellers";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private String sLname="";
    private Toolbar SellerLogintoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);


        SellerLogintoolbar = findViewById(R.id.login_seller_toolbar1);
        SellerLogintoolbar.setTitle("Seller Login");
        setSupportActionBar(SellerLogintoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SellerLogintoolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        SellerLogintoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loadingBar = new ProgressDialog(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        emailInput = (EditText) findViewById(R.id.seller_login_email);
        passwordInput = (EditText) findViewById(R.id.seller_login_password);
        sellerLoginBtn = (Button) findViewById(R.id.seller_login_btn);
        textView70 = findViewById(R.id.textView70);


        sellerLoginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                loginSeller();
            }
        });
        textView70.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passwordIntent = new Intent(SellerLoginActivity.this, ForgotPasswordActivity.class);
                startActivity(passwordIntent);
            }
        });

    }

    private void loginSeller()
    {
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();


        if (email.isEmpty()) {
            loadingBar.dismiss();
            emailInput.setError("Enter Email");
        } else if (password.isEmpty()) {
            loadingBar.dismiss();
            passwordInput.setError("Enter Password");

        }else if (!email.equals("") &&!password.equals("") )
        {
            loadingBar.setTitle("Seller Account Login");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                String user_id = mAuth.getCurrentUser().getUid();
                                mDatabase.child("Sellers").child(user_id).child("email").setValue(email);
                                mDatabase.child("users").child(user_id).child("password").setValue(password);
                                String key = mDatabase.child("users").child(user_id).child("misc").child("login_details").push().getKey();
                                HashMap<String, Object> dataMap = new HashMap<>();
                                dataMap.put("mobile_brand", Build.BRAND);
                                dataMap.put("mobile_manufacturer", Build.MANUFACTURER);
                                dataMap.put("phone_os_sdk_int", Build.VERSION.SDK_INT);
                                dataMap.put("phone_type", Build.MODEL);
                                dataMap.put("key", key);
                                dataMap.put("timestamp", System.currentTimeMillis());
                                mDatabase.child("users").child(user_id).child("misc").child("login_details").child(key).updateChildren(dataMap);

                                loadingBar.dismiss();
                                sendToSplash();
                            }

                            else {
                                loadingBar.dismiss();
                                String errMsg = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), "Error: " + errMsg, Toast.LENGTH_LONG).show();

                        }
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "Please Complete The Login Form. ", Toast.LENGTH_SHORT).show();
        }
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home)
        {
            this.finish();
        }
        return true;
    }

    private void sendToSplash() {
        Intent registerIntent = new Intent(SellerLoginActivity.this, SplashActivity.class);
        //registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(registerIntent);
        finish();
    }
}
