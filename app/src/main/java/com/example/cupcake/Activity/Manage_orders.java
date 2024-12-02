package com.example.cupcake.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.R;

import java.util.Objects;

public class Manage_orders extends AppCompatActivity {
Button newOrders, oldOrders,backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_manage_orders);

        newOrders = findViewById(R.id.viewOrders);
        oldOrders = findViewById(R.id.oldOrders);
        backBtn = findViewById(R.id.backHomeBtn);
        newOrders.setOnClickListener(v -> {
            Intent intent = new Intent(Manage_orders.this, Admin_viewOrder.class);
            startActivity(intent);
        });
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Manage_orders.this, Admin_dashboard.class);
            startActivity(intent);
        });

        oldOrders.setOnClickListener(v -> {});

    }
}