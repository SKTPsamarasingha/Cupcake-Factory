package com.example.cupcake.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.R;
import java.util.Objects;

public class Manage_offers extends AppCompatActivity {
    Button backBtn, addBtn, updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_manage_offers);

        backBtn = findViewById(R.id.backHomeBtn);
        addBtn = findViewById(R.id.addBtn);
        updateBtn = findViewById(R.id.updateBtn);


        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Manage_offers.this, Admin_dashboard.class);
            startActivity(intent);
        });
        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Manage_offers.this, Add_offers.class);
            startActivity(intent);
        });
        updateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Manage_offers.this, Update_offers.class);
            startActivity(intent);
        });


    }
}