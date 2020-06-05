package com.example.ecommerceproject.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private TextView textView41;
    private Button button8;

    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView41 = findViewById(R.id.textView41);
        button8 = (Button) findViewById(R.id.continuebtn);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        if (currentUser == null) {
            sendToStart();


        } else if (!mAuth.getCurrentUser().isEmailVerified()) {
                Toast.makeText(getApplicationContext(), "Please verify your email address", Toast.LENGTH_LONG).show();
                Intent startActivity = new Intent(SplashActivity.this, EmailVerifyActivity.class);
                startActivity(startActivity);
                finish();


            } else {
                user_id = currentUser.getUid();
                mDatabase.child("Sellers").child(user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("name").exists()) {
                            String name = dataSnapshot.child("name").getValue().toString();
                            textView41.setText("Welcome " + name);
                        } else {
                            Intent addDetailsIntent = new Intent(SplashActivity.this, AddSellerDetailsActivity.class);
                            startActivity(addDetailsIntent);
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                button8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendToMain();
                    }
                });
            }
        }


        private void sendToMain () {
            Intent registerIntent = new Intent(SplashActivity.this, Main2Activity.class);
            registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(registerIntent);
            finish();
        }

        private void sendToStart () {
            Intent registerIntent = new Intent(SplashActivity.this, SellerLoginActivity.class);
            startActivity(registerIntent);
            finish();
        }

}

