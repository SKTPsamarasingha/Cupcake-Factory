package com.example.cupcake.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cupcake.Adapters.Cart_adapter;
import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.Helpers.Helper;
import com.example.cupcake.Model.Category;
import com.example.cupcake.Model.Offer;
import com.example.cupcake.Model.SelectedItems;
import com.example.cupcake.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Cart extends AppCompatActivity {
    int userId = 0;

    ListView orderList;

    Button placeOrder, clear, back;
    TextView subtotalText, discountText, totalText;
    ArrayList<SelectedItems> selectedItems;

    int total, subTotal, totalDiscount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra("userId", -1);
        }
        totalText = findViewById(R.id.total);
        discountText = findViewById(R.id.discount);
        subtotalText = findViewById(R.id.subtotal);


        orderList = findViewById(R.id.orderItem_list);
        placeOrder = findViewById(R.id.placeOrderBtn);
        clear = findViewById(R.id.clearBtn);
        back = findViewById(R.id.backBtn);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            ArrayList<?> objects = (ArrayList<?>) bundle.getSerializable("orders");
            selectedItems = Helper.getSelected(objects);
            setTotal();
            Cart_adapter adapter = new Cart_adapter(getApplicationContext(), selectedItems);

            adapter.setOnQuantityChangedListener((position, newQuantity) -> {
                SelectedItems item = selectedItems.get(position);
                setTotal();

            });
            orderList.setAdapter(adapter);
        }


        placeOrder.setOnClickListener(v -> {
            LocalDate currentDate = null;
            DateTimeFormatter formatter = null;
            String formattedDate = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                currentDate = LocalDate.now();
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                formattedDate = currentDate.format(formatter);
            }
            if (!selectedItems.isEmpty()) {

                boolean result = DBHelper.insertOrderWithItems(getApplicationContext(), userId, formattedDate, total, 0, selectedItems);
                if (result) {
                    Intent intent1 = new Intent(getApplicationContext(), viewCategory.class);
                    intent1.putExtra("userId", userId);
                    startActivity(intent1);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to place order", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No items selected", Toast.LENGTH_SHORT).show();
            }

        });

        back.setOnClickListener(view -> {
            Intent intent1 = new Intent(getApplicationContext(), viewCategory.class);
            intent1.putParcelableArrayListExtra("orders", selectedItems);
            intent1.putExtra("userId", userId);
            startActivity(intent1);
        });

        clear.setOnClickListener(view -> {
            selectedItems = new ArrayList<SelectedItems>();
            Intent intent1 = new Intent(getApplicationContext(), viewCategory.class);
            startActivity(intent1);
        });
    }


    private int getDiscount(int id) {
        int discount = 0;
        int offerId = -1;
        ArrayList<Offer> offers = Helper.getOffers(getApplicationContext());
        ArrayList<Category> categories = Helper.getCategories(getApplicationContext());

        if (categories != null) {
            for (Category category : categories) {
                if (category.getCategoryId() == id) {
                    offerId = category.getOfferID();
                }
            }
        }
        if (offers != null && offerId != -1) {
            for (Offer offer : offers) {
                if (offer.getOfferID() == offerId) {
                    discount = offer.getDiscount();
                }
            }
        }
        return discount;
    }

    private void setTotal() {
        subTotal = 0;
        totalDiscount = 0;

        if (selectedItems != null && !selectedItems.isEmpty()) {
            for (SelectedItems item : selectedItems) {
                int price = item.getCupcake().getPrice();
                int amount = item.getAmount();
                int categoryID = item.getCupcake().getCategoryID();

                // Calculate subtotal
                subTotal += price * amount;

                // Calculate discount for  item
                int discountPercentage = getDiscount(categoryID);
                int discount = (price * discountPercentage) / 100 * amount;
                totalDiscount += discount;
            }

            total = subTotal - totalDiscount;
            // Set text with formatted strings
            subtotalText.setText(getString(R.string.SubTotal, String.valueOf(subTotal)));
            discountText.setText(getString(R.string.discount, String.valueOf(totalDiscount)));
            totalText.setText(getString(R.string.total, String.valueOf(total)));
        } else {
            subtotalText.setText(getString(R.string.SubTotal, String.valueOf(0)));
            discountText.setText(getString(R.string.discount, String.valueOf(0)));
            totalText.setText(getString(R.string.total, String.valueOf(0)));
            Toast.makeText(getApplicationContext(), "No items selected", Toast.LENGTH_SHORT).show();
        }
    }

}