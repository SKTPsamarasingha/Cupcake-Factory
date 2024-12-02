package com.example.cupcake.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.Helpers.Helper;
import com.example.cupcake.Model.Category;
import com.example.cupcake.Model.Cupcake;
import com.example.cupcake.Model.SelectedItems;
import com.example.cupcake.R;

import java.util.ArrayList;
import java.util.Objects;

public class User_Home extends AppCompatActivity {
    Button categoryBtn,orderBtn;
    int userId = 0;
    SearchView searchView;

    ArrayList<SelectedItems> currentItems = new ArrayList<>();
    ArrayList<SelectedItems> selectedItems;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_user_home);

        categoryBtn = findViewById(R.id.categoryBtn);
        orderBtn = findViewById(R.id.userOrderBtn);
        searchView = findViewById(R.id.searchView);


        Intent intent = getIntent();

        if (intent != null) {
            userId = intent.getIntExtra("userId", -1);
        }

        Bundle bundle = getIntent().getExtras();
        ArrayList<?> objects = null;
        if (bundle != null) {
            objects = (ArrayList<?>) bundle.getSerializable("orders");
            selectedItems = Helper.getSelected(objects);
            if (selectedItems != null) {
                currentItems.addAll(selectedItems);
            }
        }





        categoryBtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(User_Home.this, viewCategory.class);
            intent1.putExtra("userId", userId);
            intent1.putParcelableArrayListExtra("orders", (ArrayList<? extends Parcelable>) currentItems);
            startActivity(intent1);
        });

        orderBtn.setOnClickListener(v ->{
            Intent intent1 = new Intent(User_Home.this, ViewOrders.class);
            intent1.putExtra("userId", userId);
            startActivity(intent1);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Cupcake> cupcakes = Helper.getCupcakes(getApplicationContext());
                int categoryID = -1;
                Category foundCategory = null;

                if (cupcakes != null) {
                    for (Cupcake cupcake : cupcakes) {
                        if (cupcake.getName().toLowerCase().contains(query.toLowerCase().trim())) {
                            categoryID = cupcake.getCategoryID();
                            break;
                        }
                    }
                    if (categoryID != -1) {
                        ArrayList<Category> categories = Helper.getCategories(getApplicationContext());
                        if (categories != null) {
                            for (Category category : categories) {
                                if (category.getCategoryId() == categoryID) {
                                    foundCategory = category;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (foundCategory != null) {
                    Intent intent1 = new Intent(getApplicationContext(), ViewCupcakes.class);
                    intent1.putExtra("userId", userId);
                    intent1.putExtra("category", foundCategory);
                    startActivity(intent1);
                } else {
                    Toast.makeText(getApplicationContext(), "No cupcakes found", Toast.LENGTH_SHORT).show();
                }


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle text changes as the user types
                // For example, filter a list based on `newText`
                return false; // Return true if you handled the change
            }
        });

    }
}