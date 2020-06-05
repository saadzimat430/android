package com.example.ecommerceproject.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {

    private String check = "";
    private TextView resetTitle, titleQuestions;
    private EditText phoneNumber, question1, question2;
    private Button verifyBtn;
    private Toolbar forget_password_toolbar;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //recuperer le check (login ou settings)
        check = getIntent().getStringExtra("check");
        loadingBar = new ProgressDialog(this);


        //resetTitle = findViewById(R.id.reset_title);
        forget_password_toolbar = findViewById(R.id.forget_password_toolbar);
        forget_password_toolbar.setTitle("Forget Password");
        setSupportActionBar(forget_password_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        forget_password_toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        forget_password_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        titleQuestions = findViewById(R.id.answerTxt);
        phoneNumber = findViewById(R.id.find_phone_number);
        question1 = findViewById(R.id.q1);
        question2 = findViewById(R.id.q2);
        verifyBtn = findViewById(R.id.verify_btn);

    }


    @Override
    protected void onStart()
    {
        super.onStart();

        phoneNumber.setVisibility(View.GONE);


        if (check.equals("settings"))
        {
            forget_password_toolbar = findViewById(R.id.forget_password_toolbar);
            forget_password_toolbar.setTitle("Set Questions");
            setSupportActionBar(forget_password_toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            forget_password_toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
            forget_password_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            //resetTitle.setText("Set Questions");
            titleQuestions.setText("Please Set Answers For The Following Security Questions?");
            verifyBtn.setText("Set");

            displayPreviousAnswers();

            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    setAnswers();
                }
            });
        }
        else if (check.equals("login"))
        {
            phoneNumber.setVisibility(View.VISIBLE);

            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    verifyUser();
                }
            });
        }
    }

    private void setAnswers()
    {
        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();

        if (answer1.isEmpty())
        {
            loadingBar.dismiss();
            question1.setError("Please Answer the Question.");
        }
        else if (answer2.isEmpty())
        {
            loadingBar.dismiss();
            question2.setError("Please Answer the Question.");
        }
        else
        {
            loadingBar.setTitle("Verification");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            //sauvgarder les reponses dans la base de donnée
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(Prevalent.currentOnlineUser.getPhone());

            HashMap<String, Object> userdataMap = new HashMap<>();
            userdataMap.put("answer1", answer1);
            userdataMap.put("answer2", answer2);

            ref.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(ResetPasswordActivity.this, "You Have Set The Security Questions Successfully.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Intent intent = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });

        }
    }


    //display the previous answers
    private void displayPreviousAnswers()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(Prevalent.currentOnlineUser.getPhone());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String a1 = dataSnapshot.child("answer1").getValue().toString();
                    String a2 = dataSnapshot.child("answer2").getValue().toString();

                    question1.setText(a1);
                    question2.setText(a2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

//verifier si le user est deja ajouter les reponses aux questions de securité
    private void verifyUser()
    {
        loadingBar.setTitle("Verification");
        loadingBar.setMessage("Please wait, while we are checking the credentials.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        final String phone = phoneNumber.getText().toString();
        final String answer1 = question1.getText().toString().toLowerCase();
        final String answer2 = question2.getText().toString().toLowerCase();

        if (answer1.isEmpty())
        {
            loadingBar.dismiss();
            question1.setError("Please Answer the Question.");
        }
        else if (answer2.isEmpty())
        {
            loadingBar.dismiss();
            question2.setError("Please Answer the Question.");
        }
        else if (phone.isEmpty())
        {
            loadingBar.dismiss();
            phoneNumber.setError("Enter Phone Number");
        }

        else {
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(phone);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                    {
                        String mPhone = dataSnapshot.child("phone").getValue().toString();
                        if (dataSnapshot.hasChild("Security Questions")) {
                            String a1 = dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                            String a2 = dataSnapshot.child("Security Questions").child("answer2").getValue().toString();
                             if (!a1.equals(answer1)) {
                                loadingBar.dismiss();
                                question1.setError("Your Answer Is Not Correct.");
                                Toast.makeText(ResetPasswordActivity.this, "Your First Answer Is Not Correct.", Toast.LENGTH_SHORT).show();
                            } else if (!a2.equals(answer2)) {
                                loadingBar.dismiss();
                                question2.setError("Your Answer Is Not Correct.");
                                Toast.makeText(ResetPasswordActivity.this, "Your Second Answer Is Not Correct.", Toast.LENGTH_SHORT).show();
                            } else {
                                loadingBar.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                                builder.setTitle("New Password");

                                final EditText newPassword = new EditText(ResetPasswordActivity.this);
                                newPassword.setHint("Write New Password Here...");
                                builder.setView(newPassword);

                                builder.setPositiveButton("change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (newPassword.getText().toString().equals(""))
                                        {
                                            newPassword.setError("Enter new password");
                                        }
                                        else {
                                            ref.child("password").setValue(newPassword.getText()
                                                    .toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                loadingBar.dismiss();
                                                                Toast.makeText(ResetPasswordActivity.this, "Password Changed Successfully.", Toast.LENGTH_SHORT).show();

                                                                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });

                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        dialogInterface.cancel();
                                    }
                                });

                                builder.show();
                            }
                        }

                        //si elle sont existes alors on va le coparer avec le reponses tapée
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(ResetPasswordActivity.this, "You Have Not Set The Security Questions", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        loadingBar.dismiss();
                        phoneNumber.setError("Your Phone Number Is Not Correct.");
                        Toast.makeText(ResetPasswordActivity.this, "This Phone Number Not Exist.", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }
}
