package com.example.cupcake.Activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.Adapters.OfferAdapter;
import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.Helpers.Helper;
import com.example.cupcake.Model.Offer;
import com.example.cupcake.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class Update_offers extends AppCompatActivity {
    AutoCompleteTextView autoOffers;
    Button addBtn, updateBtn, deleteBtn;
    ArrayAdapter<String> adapterItem;
    TextInputLayout nameLayout, layoutDiscount, discountLay;
    RadioButton active, notActive;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_update_offers);

        updateBtn = findViewById(R.id.saveBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        addBtn = findViewById(R.id.addBtn);

        nameLayout = findViewById(R.id.selectOfferDropdown);
        discountLay = findViewById(R.id.updateDiscount);
        active = findViewById(R.id.updateIsActive);
        notActive = findViewById(R.id.updateIsDeactive);

        ArrayList<Offer> offers = Helper.getOffers(getApplicationContext());
        autoOffers = findViewById(R.id.autoCompleteOffers);


        if (offers != null && !offers.isEmpty()) {
            // Rendering Dropdown menu
            OfferAdapter adapter = new OfferAdapter(this, offers);
            autoOffers.setAdapter(adapter);

            // Handle item click to populate UI elements
            autoOffers.setOnItemClickListener((parent, view, position, id) -> {
                Offer selectedOffer = (Offer) parent.getItemAtPosition(position);
                autoOffers.setTag(selectedOffer);
                EditText discount = discountLay.getEditText();

                discount.setText(String.valueOf(selectedOffer.getDiscount()));


                if (selectedOffer.isActive()) {
                    active.setChecked(true);
                } else {
                    notActive.setChecked(true);
                }
            });
        }



        updateBtn.setOnClickListener(v -> {
            Offer selectedOffer = (Offer) autoOffers.getTag();

            if (selectedOffer != null) {
                ContentValues values = new ContentValues();

                EditText discount = discountLay.getEditText();
                EditText nameEdit = nameLayout.getEditText();
                int isActive = active.isChecked() ? 1 : 0;

                assert nameEdit != null;
                assert discount != null;
                values.put("offerName", nameEdit.getText().toString());
                values.put("discount", discount.getText().toString());
                values.put("isActive", isActive);

                int id = selectedOffer.getOfferID();

                boolean result = DBHelper.updateData(getApplicationContext(), values, "offer", "offerID = ?", id);

                if (result) {
                    Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show();
                    refreshOfferList();

                } else {
                    Toast.makeText(this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No offer selected", Toast.LENGTH_SHORT).show();
            }
        });
//handling delete btn click
        deleteBtn.setOnClickListener(v -> {
            Offer selectedOffer = (Offer) autoOffers.getTag();

            if (selectedOffer != null) {
                boolean result = DBHelper.deleteData(getApplicationContext(), "offer", "offerID = ?", selectedOffer.getOfferID());

                if (result) {
                    Toast.makeText(this, "Offer Deleted", Toast.LENGTH_SHORT).show();
                    refreshOfferList();

                } else {
                    Toast.makeText(this, "Failed to Delete Offer", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No offer selected", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void refreshOfferList() {
        ArrayList<Offer> updatedOffers = Helper.getOffers(getApplicationContext());
        if (updatedOffers != null && !updatedOffers.isEmpty()) {
            OfferAdapter adapter = new OfferAdapter(this, updatedOffers);
            autoOffers.setAdapter(adapter);
        }

//        EditText name = nameLayout.getEditText();
//        EditText discount = discountLay.getEditText();
//        Objects.requireNonNull(name).setText("");
//        Objects.requireNonNull(discount).setText("");
//        active.setChecked(false);
//        notActive.setChecked(false);
        clear();
    }

    private  void clear(){
        nameLayout.getEditText().setText("");
        discountLay.getEditText().setText("");
        active.setChecked(false);
        notActive.setChecked(false);
    }


}