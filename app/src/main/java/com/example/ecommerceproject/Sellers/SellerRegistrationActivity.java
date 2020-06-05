package com.example.ecommerceproject.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class SellerRegistrationActivity extends AppCompatActivity {


    private Button registerButton;
    private EditText nameInput, phoneInput, emailInput, passwordInput, addressInput;
    private ProgressDialog loadingBar;
    private TextView sellerLoginBegin;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Toolbar sellerRegiToolbar;
    private String sid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);
        loadingBar = new ProgressDialog(this);


        sellerRegiToolbar = findViewById(R.id.registration_seller_toolbar);
        sellerRegiToolbar.setTitle("Seller Registration");
        setSupportActionBar(sellerRegiToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sellerRegiToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        sellerRegiToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        registerButton = (Button) findViewById(R.id.seller_register_btn);
        emailInput = (EditText) findViewById(R.id.seller_email);
        passwordInput = (EditText) findViewById(R.id.seller_password);


        sellerLoginBegin = (TextView) findViewById(R.id.seller_already_have_an_account_btn);
        sellerLoginBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                startActivity(intent);
            }
        });

        if (currentUser != null) {
            sendToMain();
        }


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerSeller();
            }
        });
    }


    //register the seller in the FireBase
    private void registerSeller() {
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();
        loadingBar.setTitle("Seller Account Registration");
        loadingBar.setMessage("Please wait, while we are checking the credentials.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        if (email.isEmpty()) {
            loadingBar.dismiss();
            emailInput.setError("Enter Email");
        } else if (password.isEmpty()) {
            loadingBar.dismiss();
            passwordInput.setError("Enter Password");
        } else if (password.equals("password") || password.equals("123456") || password.equals("pass1234")) {
            loadingBar.dismiss();
            Toast.makeText(getApplicationContext(), "Enter strong password", Toast.LENGTH_LONG).show();
        } else if (password.length() < 5) {
            passwordInput.setError("Please enter strong password");
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(), "Email verification sent", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();


                                    loadingBar.dismiss();

                                } else {
                                    String errMsg = task.getException().getMessage();
                                    Toast.makeText(getApplicationContext(), "Error: " + errMsg, Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } else {
                        loadingBar.dismiss();
                        String errMsg = task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), "Error: " + errMsg, Toast.LENGTH_LONG).show();
                    }
                }
            });

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


    private void sendToMain() {
        Intent mainIntent = new Intent(SellerRegistrationActivity.this, Main2Activity.class);
        startActivity(mainIntent);
        finish();
    }

}
