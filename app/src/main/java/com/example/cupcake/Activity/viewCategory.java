package com.example.cupcake.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.Adapters.User_CategoryAdapter;
import com.example.cupcake.Helpers.Helper;
import com.example.cupcake.Model.Category;
import com.example.cupcake.Model.Cupcake;
import com.example.cupcake.Model.SelectedItems;
import com.example.cupcake.R;

import java.util.ArrayList;
import java.util.Objects;

public class viewCategory extends AppCompatActivity {
    ListView categoryList;
    Button backHome;
    int userId = 0;

    ArrayList<SelectedItems> currentItems = new ArrayList<>();
    ArrayList<SelectedItems> selectedItems;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_view_category);

        backHome = findViewById(R.id.backHomeBtn);
        categoryList = findViewById(R.id.category_list);

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra("userId", -1);
        }

        ArrayList<Category> categories = Helper.getCategories(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        ArrayList<?> objects = null;
        if (bundle != null) {
            objects = (ArrayList<?>) bundle.getSerializable("orders");
            selectedItems = Helper.getSelected(objects);
            if (selectedItems != null) {
                currentItems.addAll(selectedItems);
            }
        }


        if (categories != null) {
            User_CategoryAdapter adapter = new User_CategoryAdapter(getApplicationContext(), categories);
            categoryList.setAdapter(adapter);
        }


        categoryList.setOnItemClickListener((parent, view, position, id) -> {

            Category category = (Category) parent.getItemAtPosition(position);
            ArrayList<Cupcake> cupcakes = Helper.getCupcakeByID(getApplicationContext(), category.getCategoryId());

            if (cupcakes != null) {
                Intent intent1 = new Intent(getApplicationContext(), ViewCupcakes.class);
                intent1.putExtra("category", category);
                intent1.putParcelableArrayListExtra("orders", (ArrayList<? extends Parcelable>) currentItems);
                intent1.putExtra("userId", userId);
                startActivity(intent1);
            } else {
                Toast.makeText(getApplicationContext(), "No cupcakes found for this category", Toast.LENGTH_SHORT).show();
            }

        });

        backHome.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), User_Home.class);
            intent1.putParcelableArrayListExtra("orders", (ArrayList<? extends Parcelable>) currentItems);
            intent1.putExtra("userId", userId);
            startActivity(intent1);
        });
    }
}