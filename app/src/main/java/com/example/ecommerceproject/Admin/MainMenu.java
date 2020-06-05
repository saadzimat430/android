package com.example.ecommerceproject.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ecommerceproject.Home.MainActivity;
import com.example.ecommerceproject.R;

public class MainMenu extends AppCompatActivity {
    private ImageView Home, check,logout,settings,approuve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Home = (ImageView) findViewById(R.id.Homeadmin);
        check = (ImageView) findViewById(R.id.admincheck);
        approuve = (ImageView) findViewById(R.id.adminapp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.chtl_toolbar);
        setSupportActionBar(toolbar);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.admin_toolbar, null);
        getSupportActionBar().setCustomView(v);

        logout = (ImageView) findViewById(R.id.adminlog);

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainMenu.this, AdminHomeActivity.class);

                startActivity(intent);
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainMenu.this, AdminCheckNewProductsActivity.class);

                startActivity(intent);
            }
        });

        approuve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainMenu.this, AdminCheckNewProductsActivity.class);

                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainMenu.this, MainActivity.class);

                startActivity(intent);
                finish();
            }
        });
    }
}
