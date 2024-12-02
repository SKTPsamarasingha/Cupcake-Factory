package com.example.cupcake.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.Adapters.CategoryAdapter;
import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.Helpers.Helper;
import com.example.cupcake.Model.Category;
import com.example.cupcake.Model.Offer;
import com.example.cupcake.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class Add_cupcake extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Button addBtn, backBtn, addImg;
    AutoCompleteTextView categoryDropdown;
    EditText nameInput, priceInput, descriptionInput;
    ImageView img;

    byte[] imgArr;
    private ActivityResultLauncher<Intent> pickImageLaunch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_add_cupcake);

        pickImageLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        img.setImageURI(imageUri);
                    }
                }
        );


        ArrayList<Category> categories = Helper.getCategories(getApplicationContext());
        ArrayList<Offer> offers = Helper.getOffers(getApplicationContext());

        addBtn = findViewById(R.id.addBtn);
        backBtn = findViewById(R.id.backHomeBtn);
        addImg = findViewById(R.id.addImgBtn);
        nameInput = findViewById(R.id.cName);
        priceInput = findViewById(R.id.price);
        descriptionInput = findViewById(R.id.description);
        img = findViewById(R.id.img);
        categoryDropdown = findViewById(R.id.cupCakeCategoryMenu);


        categoryDropdown.setInputType(InputType.TYPE_NULL);
        categoryDropdown.setKeyListener(null);
        if (categories != null && !categories.isEmpty()) {
            // Rendering Dropdown menu
            CategoryAdapter adapter = new CategoryAdapter(this, categories);
            categoryDropdown.setAdapter(adapter);
            categoryDropdown.setOnItemClickListener((parent, view, position, id) -> {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                categoryDropdown.setTag(selectedCategory);

            });
        }


        addBtn.setOnClickListener(v -> {
            Category selectedCategory = (Category) categoryDropdown.getTag();

            if (selectedCategory == null) {
                Toast.makeText(this, "Please select an category", Toast.LENGTH_SHORT).show();
            } else {
                int categoryID = selectedCategory.getCategoryId();
                String name = nameInput.getText().toString().trim();
                String priceStr = Objects.requireNonNull(priceInput).getText().toString();
                int price = 0;
                if (!priceStr.isEmpty()) {
                    price = Integer.parseInt(priceStr);
                }
                String info = descriptionInput.getText().toString().trim();


                // Get image from ImageView

                if (img.getDrawable() != null) {
                    if (img.getDrawable() instanceof BitmapDrawable) {
                        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                        imgArr = getImgArray(bitmap);
                    }
                }
                if(name.isEmpty()){
                    Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
                }
                if(price == 0){
                    Toast.makeText(this, "Please enter a price", Toast.LENGTH_SHORT).show();
                }
                if(info.isEmpty()){
                    Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show();
                }
                if(imgArr == null){
                    Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues contentValues = new ContentValues();
                contentValues.put("CategoryID", categoryID);
                contentValues.put("name", name);
                contentValues.put("price", price);
                contentValues.put("info", info);
                contentValues.put("img", imgArr);
                try (DBHelper DB = new DBHelper(this)) {
                    boolean result = DBHelper.insertData(this, contentValues, "cupcake");
                    DB.close();
                    if (result) {
                        Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        nameInput.setText("");
                        priceInput.setText("");
                        descriptionInput.setText("");
                        categoryDropdown.setText("");
                        img.setImageBitmap(null);
                    } else {
                        Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Add_cupcake.this, Manage_cupcake.class);
            startActivity(intent);
        });

        addImg.setOnClickListener(v -> {
            openGallery();
        });

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        pickImageLaunch.launch(intent);
    }

    private byte[] getImgArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri imageUri = data.getData();
//
//            img.setImageURI(imageUri);
//        }
//    }


}