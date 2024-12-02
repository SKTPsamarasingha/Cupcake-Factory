package com.example.cupcake.Activity;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.Adapters.CategoryAdapter;
import com.example.cupcake.Adapters.OfferAdapter;
import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.Helpers.Helper;
import com.example.cupcake.Model.Category;
import com.example.cupcake.Model.Offer;
import com.example.cupcake.R;

import java.util.ArrayList;
import java.util.Objects;

public class Update_category extends AppCompatActivity {
    Button backBtn, updateBtn, deleteBtn;
    AutoCompleteTextView autoCompleteCategory, autoCompleteOffer;
    EditText description;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_update_category);

        backBtn = findViewById(R.id.categoryUpdatebackBtn);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        ArrayList<Category> categories = Helper.getCategories(getApplicationContext());
        ArrayList<Offer> offers = Helper.getOffers(getApplicationContext());

        autoCompleteCategory = findViewById(R.id.autoCompleteCategory);
        autoCompleteOffer = findViewById(R.id.autoCompleteOffer);

         description = findViewById(R.id.InputDescription);

        if (categories != null && !categories.isEmpty()) {
            // Rendering Dropdown menu
            CategoryAdapter adapter = new CategoryAdapter(this, categories);
            autoCompleteCategory.setAdapter(adapter);
            autoCompleteCategory.setOnItemClickListener((parent, view, position, id) -> {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                autoCompleteCategory.setTag(selectedCategory);
                description.setText(selectedCategory.getInfo());

//                if (offers != null) {
//                    for (Offer offer : offers) {
//                        if (offer.getOfferID() == selectedCategory.getOfferID()) {
//                            autoCompleteOffer.setText(offer.getName());
////                           autoCompleteOffer.setTag(offer);
//                        }
//                    }
//                }
            });
        }
        autoCompleteOffer.setInputType(InputType.TYPE_NULL);
        autoCompleteOffer.setKeyListener(null);
        if (offers != null && !offers.isEmpty()) {
            // Rendering Dropdown menu
            OfferAdapter adapter = new OfferAdapter(this, offers);
            autoCompleteOffer.setAdapter(adapter);
            // Handle item click
            autoCompleteOffer.setOnItemClickListener((parent, view, position, id) -> {
                Offer selectedOffer = (Offer) parent.getItemAtPosition(position);
                autoCompleteOffer.setTag(selectedOffer);
            });
        }


        updateBtn.setOnClickListener(v -> {
            Offer selectedOffer = (Offer) autoCompleteOffer.getTag();
            Category selectedCategory = (Category) autoCompleteCategory.getTag();

            if (selectedCategory == null) {
                Toast.makeText(this, "Please select an category", Toast.LENGTH_SHORT).show();

            } else {

                Integer offerID = selectedOffer != null ? selectedOffer.getOfferID() : null;

                int categoryID = selectedCategory.getCategoryId();
                String name = autoCompleteCategory.getText().toString().trim();
                String info = description.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put("offerID", offerID);
                values.put("name", name);
                values.put("info", info);

                boolean result = DBHelper.updateData(getApplicationContext(), values, "Categorie", "CategoryID = ?", categoryID);
                if (result) {
                    Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show();
                    refreshList();
                } else {
                    Toast.makeText(this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                }
            }

        });
        deleteBtn.setOnClickListener(v -> {
            Category selectedCategory = (Category) autoCompleteCategory.getTag();

            if (selectedCategory != null) {
                int id = selectedCategory.getCategoryId();
                boolean result = DBHelper.deleteData(getApplicationContext(), "Categorie", "CategoryID = ?", id);

                if (result) {
                    Toast.makeText(this, "Offer Deleted", Toast.LENGTH_SHORT).show();
                    refreshList();

                } else {
                    Toast.makeText(this, "Failed to Delete Offer", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No offer selected", Toast.LENGTH_SHORT).show();
            }
        });
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Update_category.this, Manage_category.class);
            startActivity(intent);
        });
    }

    private void refreshList() {
        ArrayList<Category> categories = Helper.getCategories(getApplicationContext());
        if (categories != null && !categories.isEmpty()) {
            CategoryAdapter adapter = new CategoryAdapter(this, categories);
            autoCompleteCategory.setAdapter(adapter);
        }
        clear();
    }

    private void clear() {
        autoCompleteCategory.setText("");
        autoCompleteOffer.setText("");
        description.setText("");
    }
}