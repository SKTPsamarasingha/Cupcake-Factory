package com.example.cupcake.Activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.Adapters.CategoryAdapter;
import com.example.cupcake.Adapters.CupcakeAdapter;
import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.Helpers.Helper;
import com.example.cupcake.Model.Category;
import com.example.cupcake.Model.Cupcake;
import com.example.cupcake.R;

import java.util.ArrayList;
import java.util.Objects;

public class Update_cupcake extends AppCompatActivity {
    AutoCompleteTextView cupcakeDropdown, categoryDropdown;
    Button updateBtn, deleteBtn, backBtn;
    EditText price, descriptions;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_update_cupcake);

        ArrayList<Category> categories = Helper.getCategories(getApplicationContext());
        ArrayList<Cupcake> cupcakes = Helper.getCupcakes(getApplicationContext());

        cupcakeDropdown = findViewById(R.id.cupcakeDropdownMenu);
        categoryDropdown = findViewById(R.id.cupcakeCategoryMenu);

        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        backBtn = findViewById(R.id.backHomeBtn);

        price = findViewById(R.id.priceInput);
        descriptions = findViewById(R.id.descriptionInput);


        if (cupcakes != null && !cupcakes.isEmpty()) {
            // Rendering Dropdown menu
            CupcakeAdapter adapter = new CupcakeAdapter(this, cupcakes);
            cupcakeDropdown.setAdapter(adapter);
            cupcakeDropdown.setOnItemClickListener((parent, view, position, id) -> {
                Cupcake selectedCupcake = (Cupcake) parent.getItemAtPosition(position);
                cupcakeDropdown.setTag(selectedCupcake);


                price.setText(String.valueOf(selectedCupcake.getPrice()));
                descriptions.setText(selectedCupcake.getInfo());
                System.out.println(selectedCupcake.getPrice());
            });
        }

        if (categories != null && !categories.isEmpty()) {
            // Rendering Dropdown menu
            CategoryAdapter adapter = new CategoryAdapter(this, categories);
            categoryDropdown.setAdapter(adapter);
            categoryDropdown.setOnItemClickListener((parent, view, position, id) -> {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                categoryDropdown.setTag(selectedCategory);
            });
        }


        updateBtn.setOnClickListener(v -> {

            Category selectedCategory = (Category) categoryDropdown.getTag();

            if (selectedCategory == null) {
                Toast.makeText(this, "Please select an category", Toast.LENGTH_SHORT).show();
            } else {
                Cupcake selectedCupcake = (Cupcake) cupcakeDropdown.getTag();
                if (selectedCupcake == null) {
                    Toast.makeText(this, "Please select a cupcake", Toast.LENGTH_SHORT).show();
                } else {
                    String priceText = price.getText().toString();
                    String info = descriptions.getText().toString();

                    ContentValues values = new ContentValues();
                    values.put("CategoryID", selectedCategory.getCategoryId());
                    values.put("name", cupcakeDropdown.getText().toString());
                    values.put("price", priceText);
                    values.put("info", info);

                    boolean result = DBHelper.updateData(getApplicationContext(), values, "cupcake", "cupcakeID = ?", selectedCupcake.getCupcakeID() );
                    if (result) {
                        Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show();
                        refreshList();
                    } else {
                        Toast.makeText(this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        deleteBtn.setOnClickListener(v -> {
            Cupcake selectedCupcake = (Cupcake) cupcakeDropdown.getTag();

            if(selectedCupcake == null){
                Toast.makeText(this, "Please select a cupcake", Toast.LENGTH_SHORT).show();
            } else {
                boolean result = DBHelper.deleteData(getApplicationContext(), "cupcake", "cupcakeID = ?", selectedCupcake.getCupcakeID());
                if (result) {
                    Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show();
                    refreshList();
                } else {
                    Toast.makeText(this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Update_cupcake.this, Manage_cupcake.class);
            startActivity(intent);
        });


    }

    private void refreshList() {
        ArrayList<Cupcake> cupcakes = Helper.getCupcakes(getApplicationContext());
        if (cupcakes != null && !cupcakes.isEmpty()) {
            CupcakeAdapter adapter = new CupcakeAdapter(this, cupcakes);
            cupcakeDropdown.setAdapter(adapter);
        }
        clear();
    }

    private void clear(){
        cupcakeDropdown.setText("");
        categoryDropdown.setText("");
        price.setText("");
        descriptions.setText("");
    }


}