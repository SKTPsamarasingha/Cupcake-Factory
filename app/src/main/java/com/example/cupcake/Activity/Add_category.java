package com.example.cupcake.Activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.Adapters.OfferAdapter;
import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.Helpers.Helper;
import com.example.cupcake.Model.Offer;
import com.example.cupcake.R;

import java.util.ArrayList;
import java.util.Objects;

public class Add_category extends AppCompatActivity {
    Button addBtn, backBtn;
    AutoCompleteTextView offerDropdown;
    ArrayAdapter<String> adapterItem;
    EditText categoryName, description;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_add_category);

        addBtn = findViewById(R.id.addBtn);
        backBtn = findViewById(R.id.backHomeBtn);

        categoryName = findViewById(R.id.categoryNameInput);
        description = findViewById(R.id.descriptionInput);
        offerDropdown = findViewById(R.id.CategoryOfferDropdown);

        ArrayList<Offer> offers = Helper.getOffers(getApplicationContext());

        if (offers != null && !offers.isEmpty()) {
            // Rendering Dropdown menu
            OfferAdapter adapter = new OfferAdapter(this, offers);
            offerDropdown.setAdapter(adapter);
            // Handle item click to populate UI elements
            offerDropdown.setOnItemClickListener((parent, view, position, id) -> {
                Offer selectedOffer = (Offer) parent.getItemAtPosition(position);
                offerDropdown.setTag(selectedOffer);
            });
        }


        addBtn.setOnClickListener(v -> {
            Offer selectedOffer = (Offer) offerDropdown.getTag();
            int offerID = 0;
            if (selectedOffer != null) {
                offerID = selectedOffer.getOfferID();
            }
            String name = categoryName.getText().toString().trim();
            String info = description.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            } else if (info.isEmpty()) {
                Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show();
            }


            ContentValues contentValues = new ContentValues();
            if (offerID == 0) {
                contentValues.put("offerID", (Integer) null);
            } else {
                contentValues.put("offerID", offerID);
            }
            contentValues.put("name", name);
            contentValues.put("info", info);


            try (DBHelper DB = new DBHelper(this)) {
                boolean result = DBHelper.insertData(this, contentValues, "Categorie");
                DB.close();
                if (result) {
                    Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    categoryName.setText("");
                    description.setText("");
                    offerDropdown.setText("");
                } else {
                    Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Add_category.this, Manage_category.class);
            startActivity(intent);
        });


    }


}