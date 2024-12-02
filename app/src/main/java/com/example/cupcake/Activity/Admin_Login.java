package com.example.cupcake.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.Model.Admin;
import com.example.cupcake.R;

import java.util.Objects;


public class Admin_Login extends AppCompatActivity {
    TextView nameInput, passwordInput;
    Button AdminLoginBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_admin_loign);


        AdminLoginBtn = findViewById(R.id.adminLoginBtn);


        nameInput = findViewById(R.id.admin_username);
        passwordInput = findViewById(R.id.admin_password);

        System.out.println(nameInput);
        System.out.println(passwordInput);

        AdminLoginBtn.setOnClickListener(v -> {
            String username = nameInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            }

            System.out.println(username);
            System.out.println(password);
            boolean admin = adminValidate(username, password);
            if (admin) {
                Intent intent = new Intent(Admin_Login.this, Admin_dashboard.class);
                startActivity(intent);
            }
        });


    }

    public boolean adminValidate(String username, String password) {
        boolean isValid = false;
        Admin admin = DBHelper.getAdmin(getApplicationContext(), username, password);
        if (admin != null) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                System.out.println(admin.getAdminID());
                System.out.println(admin.getUsername());
                System.out.println(admin.getPassword());
                isValid = true;
            }
        } else {
            Toast.makeText(this, "Admin Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }
}