package com.example.cupcake.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.Adapters.User_CupcakesAdapter;
import com.example.cupcake.Helpers.Helper;
import com.example.cupcake.Model.Category;
import com.example.cupcake.Model.Cupcake;
import com.example.cupcake.Model.SelectedItems;
import com.example.cupcake.R;

import java.util.ArrayList;
import java.util.Objects;

public class ViewCupcakes extends AppCompatActivity implements User_CupcakesAdapter.OnAddToCartClickListener {

    ListView cupcakeList;
    Button cartBtn, backBtn;
    ArrayList<SelectedItems> currentItems = new ArrayList<>();
    ArrayList<SelectedItems> selectedItems;

    ArrayList<SelectedItems> itemsSelected = new ArrayList<>();
    int userId = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_view_cupcakes);


        cupcakeList = findViewById(R.id.cupcake_list);
        cartBtn = findViewById(R.id.cartBtn);
        backBtn = findViewById(R.id.backHomeBtn);

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra("userId", -1);
        }

        Category category = (Category) getIntent().getSerializableExtra("category");
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            ArrayList<?> objects = (ArrayList<?>) bundle.getSerializable("orders");
            selectedItems = Helper.getSelected(objects);
            if (selectedItems != null) {
                currentItems.addAll(selectedItems);
            }
        }


        if (category != null) {
            int categoryID = category.getCategoryId();
            ArrayList<Cupcake> cupcakes = Helper.getCupcakeByID(getApplicationContext(), categoryID);

            if (cupcakes != null) {
                User_CupcakesAdapter adapter = new User_CupcakesAdapter(getApplicationContext(), cupcakes, this);
                cupcakeList.setAdapter(adapter);
            }
        }


        cartBtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), Cart.class);
            intent1.putParcelableArrayListExtra("orders", currentItems);
            intent1.putExtra("userId", userId);
            startActivity(intent1);
        });

        backBtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), viewCategory.class);
            intent1.putExtra("userId", userId);
            intent1.putParcelableArrayListExtra("orders", currentItems);
            startActivity(intent1);
        });

    }


    @Override
    public void addBtn(Cupcake cupcake) {
        Toast.makeText(this, cupcake.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        SelectedItems selectedItem = new SelectedItems(cupcake, 1);
        currentItems.add(selectedItem);
    }
}