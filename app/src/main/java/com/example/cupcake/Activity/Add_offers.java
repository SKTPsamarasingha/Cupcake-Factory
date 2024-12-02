package com.example.cupcake.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Add_offers extends AppCompatActivity {
    Button backBtn, addBtn;
    TextInputLayout nameLayout, layoutDiscount;

    RadioButton active, deactivate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_add_offers);

        backBtn = findViewById(R.id.backHomeBtn);
        addBtn = findViewById(R.id.Addbtn);

        nameLayout = findViewById(R.id.offerNameInput);
        layoutDiscount = findViewById(R.id.discountInput);
        active = findViewById(R.id.active);
        deactivate = findViewById(R.id.deactivate);

        addBtn.setOnClickListener(v -> {
            EditText nameEdit = nameLayout.getEditText();
            EditText discountEdit = layoutDiscount.getEditText();

            String name = Objects.requireNonNull(nameEdit).getText().toString();
            String discountStr = Objects.requireNonNull(discountEdit).getText().toString();
            int discount = 0;
            if (!discountStr.isEmpty()) {
                discount = Integer.parseInt(discountStr);
            }

            int isActive = active.isChecked() ? 1 : 0;

            if (name.isEmpty()) {
                Toast.makeText(this, "Enter Offer Name", Toast.LENGTH_SHORT).show();

            } else if (discount == 0) {
                Toast.makeText(this, "Enter Discount", Toast.LENGTH_SHORT).show();
            }

            ContentValues contentValues = new ContentValues();

            contentValues.put("offerName", name);
            contentValues.put("discount", discount);
            contentValues.put("isActive", isActive);
            try (DBHelper DB = new DBHelper(this)) {
                boolean result = DBHelper.insertData(this, contentValues, "offer");
                DB.close();
                if (result) {
                    Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    nameEdit.setText("");
                    discountEdit.setText("");
                    active.setChecked(false);
                    deactivate.setChecked(false);
                } else {
                    Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Admin_dashboard.class);
            startActivity(intent);
        });


    }
}