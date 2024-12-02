package com.example.cupcake.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.R;

import java.util.Objects;

public class Admin_dashboard extends AppCompatActivity {
    Button offerBtn, categoryBtn, cupcakeBtn, orderBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_admin_dashboard);

        offerBtn = findViewById(R.id.offerBtn);
        categoryBtn = findViewById(R.id.categoryBtn);
        cupcakeBtn = findViewById(R.id.cupcakeBtn);
        orderBtn = findViewById(R.id.orderBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        offerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_dashboard.this, Manage_offers.class);
            startActivity(intent);
        });
        categoryBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_dashboard.this, Manage_category.class);
            startActivity(intent);
        });
        cupcakeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_dashboard.this, Manage_cupcake.class );
                startActivity(intent);
        });
        orderBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_dashboard.this, Admin_viewOrder.class );
                startActivity(intent);
        });

        logoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_dashboard.this, MainActivity.class );
            startActivity(intent);
        });




    }
}