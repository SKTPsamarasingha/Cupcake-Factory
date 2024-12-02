package com.example.cupcake.Activity;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.R;

import java.util.ArrayList;
import java.util.Objects;

public class User_register extends AppCompatActivity {
    EditText name, contact, address, password;
    Button createAccountBtn, signUpBtn;
    ArrayList<String> dataList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_user_register);

//        User inputs
        name = findViewById(R.id.username);
        contact = findViewById(R.id.contact);
        address = findViewById(R.id.address);
        password = findViewById(R.id.password);


        createAccountBtn = findViewById(R.id.CreateAccountBtn);
        signUpBtn = findViewById(R.id.signUpBtn);


        createAccountBtn.setOnClickListener(v -> {
            String username = name.getText().toString().trim();
            String number = contact.getText().toString().trim();
            String useradd = address.getText().toString().trim();
            String userPassword = password.getText().toString().trim();

            if (username.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Username is Empty", Toast.LENGTH_SHORT).show();
            } else if (number.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Number is Empty", Toast.LENGTH_SHORT).show();
            } else if (useradd.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Address is Empty", Toast.LENGTH_SHORT).show();
            } else if (userPassword.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Password is Empty", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "You are good", Toast.LENGTH_SHORT).show();
                // Save data in DB
                ContentValues values = new ContentValues();
                values.put("username", username);
                values.put("number", number);  // Store as string
                values.put("address", useradd);
                values.put("password", userPassword);

                boolean result = DBHelper.insertData(this, values, "user");
                if (result) {
                    name.setText("");
                    contact.setText("");
                    address.setText("");
                    password.setText("");
                    // Redirect to user dashboard
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        moving to login
        signUpBtn.setOnClickListener(v -> {
            Intent intent = new Intent(User_register.this, MainActivity.class);
            startActivity(intent);
        });
    }
}