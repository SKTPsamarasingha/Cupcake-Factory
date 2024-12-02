package com.example.cupcake.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.R;

import java.util.Objects;

public class Manage_category extends AppCompatActivity {

    Button addBtn, updateBtn, backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_manage_category);


        addBtn = findViewById(R.id.addBtn);
        updateBtn = findViewById(R.id.updateBtn);
        backBtn = findViewById(R.id.backHomeBtn);
        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Manage_category.this, Add_category.class);
            startActivity(intent);
        });
        updateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Manage_category.this, Update_category.class);
            startActivity(intent);
        });
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Manage_category.this, Admin_dashboard.class);
            startActivity(intent);
        });

    }
}