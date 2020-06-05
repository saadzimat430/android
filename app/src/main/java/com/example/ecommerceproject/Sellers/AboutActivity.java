package com.example.ecommerceproject.Sellers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.ecommerceproject.R;

public class AboutActivity extends AppCompatActivity {

    private Toolbar toolbar7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageView imageView12 = findViewById(R.id.imageView12);
        ImageView imageView9 = findViewById(R.id.imageView9);
        imageView12.setImageResource(R.drawable.back2);

    }
}
