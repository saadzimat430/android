package com.example.ecommerceproject.Home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.google.firebase.database.DatabaseReference;

import io.paperdb.Paper;

public class UserSettingsActivity extends AppCompatActivity
{
    private Toolbar settingstoolbar;

    private TextView tvSettingsLogout,  editInfos, about, securityQuestionsBtn;


    private DatabaseReference mDatabase;
    private String currentUser;

    private String b_email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        settingstoolbar = findViewById(R.id.settings_user_toolbar);
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

        currentUser = Prevalent.currentOnlineUser.getPhone();
        tvSettingsLogout = findViewById(R.id.SettingsLogout);
        editInfos = findViewById(R.id.editInfo);
        about = findViewById(R.id.textView15);
        securityQuestionsBtn = findViewById(R.id.security_questions_btn);

        if (currentUser == null)
        {
            sendToLogin();
        }
        else
        {
            securityQuestionsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(UserSettingsActivity.this, ResetPasswordActivity.class);
                    intent.putExtra("check", "settings");
                    startActivity(intent);
                }
            });

            about.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {

                }
            });

            editInfos.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(UserSettingsActivity.this, EditInfoActivity.class);
                    startActivity(intent);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserSettingsActivity.this);
                    builder.setTitle("Are You Sure ? ");
                    builder.setItems(options, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            if (i==0)
                            {
                                Paper.book().destroy();
                                Intent logoutIntent = new Intent(UserSettingsActivity.this, MainActivity.class);
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
        }



    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(UserSettingsActivity.this, MainActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
