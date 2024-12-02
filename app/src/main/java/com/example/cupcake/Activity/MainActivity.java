package com.example.cupcake.Activity;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.Model.Admin;
import com.example.cupcake.Model.User;
import com.example.cupcake.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //    TextInputEditText name, pass;
    Button loginBtn, createAccountBtn;
    TextInputLayout name, pass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);


        loginBtn = findViewById(R.id.signUpBtn);
        createAccountBtn = findViewById(R.id.CreateAccountBtn);


        name = findViewById(R.id.username);
        pass = findViewById(R.id.password);


        loginBtn.setOnClickListener(v -> {
            TextInputEditText editName = (TextInputEditText) name.getEditText();
            TextInputEditText editPass = (TextInputEditText) pass.getEditText();

            if (editName != null && editPass != null) {
                String username = Objects.requireNonNull(editName.getText()).toString().trim();
                String password = Objects.requireNonNull(editPass.getText()).toString().trim();

                if (username.isEmpty()) {
                    Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
                }
                if (password.isEmpty()) {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                }
                boolean admin = false;
                boolean user = false;
                User userObj = null;

                if (!username.isEmpty() || !password.isEmpty()) {
                     admin = adminValidate(username, password);
                     user = userValidate(username, password);
                     userObj = DBHelper.getUser(getApplicationContext(), username, password);
                }


                if (user) {
                    if (userObj != null) {
                        Intent intent = new Intent(MainActivity.this, User_Home.class);
                        intent.putExtra("userId", userObj.getUserID());
                        intent.putExtra("username", userObj.getUsername());
                        startActivity(intent);
                    }
                } else if (admin) {
                    Intent intent = new Intent(MainActivity.this, Admin_dashboard.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

        });

        createAccountBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, User_register.class);
            startActivity(intent);
        });


    }


    public boolean userValidate(String username, String password) {
        boolean isValid = false;
        User user = DBHelper.getUser(getApplicationContext(), username, password);
        if (user != null) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                isValid = true;
            }
        } else {
//            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    public boolean adminValidate(String username, String password) {
        boolean isValid = false;
        Admin admin = DBHelper.getAdmin(getApplicationContext(), username, password);
        if (admin != null) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                isValid = true;
            }
        } else {
//            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    public static boolean insertAdmin(Context context, String username, String password) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        try (DBHelper dbHelper = new DBHelper(context);
             SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            long result = db.insert("admin", null, values);
            return result != -1;
        } catch (Exception e) {
            Log.e("DBError", "Error inserting admin", e);
            return false;
        }
    }

    public static boolean deleteData(Context context) {
        try (DBHelper DBClass = new DBHelper(context);
             SQLiteDatabase DB = DBClass.getWritableDatabase()) {

            // Execute the delete statement
            DB.execSQL("DELETE FROM user");

            // Check if the table is empty
            Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM orderedItem", null);
            boolean isDeleted = false;
            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                isDeleted = count == 0;
            }
            cursor.close();

            return isDeleted;
        } catch (Exception e) {
            Log.e("DatabaseDelete", "Error deleting data", e);
            return false;
        }
    }
//    public void insertCupcakeCategories(Context context) {
//        String[][] categories = {
//                {null, "Classic Cupcakes", "Traditional flavors like vanilla, chocolate, and red velvet"},
//                {null, "Gourmet Cupcakes", "Unique and sophisticated flavors for the adventurous"},
//                {null, "Vegan Cupcakes", "Dairy-free and egg-free cupcakes for plant-based diets"},
//                {null, "Gluten-Free Cupcakes", "Delicious cupcakes made with gluten-free flour"},
//                {null, "Mini Cupcakes", "Bite-sized versions of our popular flavors"},
//                {null, "Seasonal Cupcakes", "Special flavors that change with the seasons"},
//                {null, "Wedding Cupcakes", "Elegant cupcakes perfect for wedding celebrations"},
//                {null, "Birthday Cupcakes", "Festive cupcakes ideal for birthday parties"},
//                {null, "Savory Cupcakes", "Unexpected savory flavors in cupcake form"},
//                {null, "Custom Cupcakes", "Design your own cupcake with our range of flavors and toppings"}
//        };
//
//        for (String[] category : categories) {
//            String offerIDStr = category[0];
//            String name = category[1];
//            String info = category[2];
//
//            int offerID = (offerIDStr != null) ? Integer.parseInt(offerIDStr) : -1;
//
//            boolean success = insertCategorieData(context, offerID, name, info);
//            if (success) {
//                Log.d("DatabaseInsert", "Successfully inserted category: " + name);
//            } else {
//                Log.e("DatabaseInsert", "Error inserting category: " + name);
//            }
//        }
//    }
//    public boolean insertCategorieData(Context context,int offerID, String name, String info) {
//        DBHelper db = new DBHelper(context);
//        SQLiteDatabase DB = db.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("offerID", offerID);
//        contentValues.put("name", name);
//        contentValues.put("info", info);
//        long result = DB.insert("Categorie", null, contentValues);
//        DB.close();
//        return result != -1;
//    }


    //    insertCupcakeCategories(getApplicationContext());


//    public void insertCupcakeData(Context context) {
//        String[][] cupcakes = {
//                {null, "1", "Vanilla Dream", "200", "Classic vanilla cupcake with creamy frosting"},
//                {null, "1", "Chocolate Delight", "250", "Rich chocolate cupcake with chocolate chips"},
//                {null, "2", "Lavender Lemon", "300", "Gourmet cupcake with lavender-infused cake and lemon frosting"},
//                {null, "2", "Salted Caramel", "350", "Decadent caramel cupcake with sea salt topping"},
//                {null, "3", "Berry Bliss", "300", "Vegan berry-flavored cupcake with coconut cream frosting"},
//                {null, "4", "Almond Joy", "350", "Gluten-free almond cupcake with chocolate ganache"},
//                {null, "5", "Tiny Tiramisu", "400", "Mini coffee-flavored cupcake with mascarpone frosting"},
//                {null, "6", "Pumpkin Spice", "350", "Seasonal fall cupcake with cinnamon cream cheese frosting"},
//                {null, "7", "Champagne Sparkle", "400", "Elegant champagne-flavored cupcake with edible glitter"},
//                {null, "8", "Confetti Explosion", "300", "Colorful birthday cupcake with sprinkle-loaded frosting"}
//        };
//
//        for (String[] cupcake : cupcakes) {
//            String cupcakeIDStr = cupcake[0];
//            String categoryIDStr = cupcake[1];
//            String name = cupcake[2];
//            String priceStr = cupcake[3];
//            String info = cupcake[4];
//
//            int categoryID = Integer.parseInt(categoryIDStr);
//            double price = Double.parseDouble(priceStr);
//
//            boolean success = insertCupcakeData(context, categoryID, name, price, info);
//            if (success) {
//                Log.d("DatabaseInsert", "Successfully inserted cupcake: " + name);
//            } else {
//                Log.e("DatabaseInsert", "Error inserting cupcake: " + name);
//            }
//        }
//    }
//
//    public boolean insertCupcakeData(Context context, int categoryID, String name, double price, String info) {
//        DBHelper db = new DBHelper(context);
//        SQLiteDatabase DB = db.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("CategoryID", categoryID);
//        contentValues.put("name", name);
//        contentValues.put("price", price);
//        contentValues.put("info", info);
//        long result = DB.insert("cupcake", null, contentValues);
//        DB.close();
//        return result != -1;
//    }


}