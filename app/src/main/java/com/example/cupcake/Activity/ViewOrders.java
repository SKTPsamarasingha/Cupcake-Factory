package com.example.cupcake.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cupcake.Adapters.User_OrderAdapter;
import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.Model.Order;
import com.example.cupcake.R;

import java.util.ArrayList;
import java.util.Objects;

public class ViewOrders extends AppCompatActivity {

    RecyclerView orderView;
    int userId;
    Button preBtn, newBtn;
    User_OrderAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_view_orders);


        orderView = findViewById(R.id.userOrderView);
        preBtn = findViewById(R.id.preBtn);
        newBtn = findViewById(R.id.newBtn);

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra("userId", -1);
            setOrders(userId, 0);
        }

        preBtn.setOnClickListener(v -> {
            if (adapter != null){
                adapter.clearData();
            }

            setOrders(userId, 1);
        });
        newBtn.setOnClickListener(v -> {
            if (adapter != null){
                adapter.clearData();
            }
            setOrders(userId, 0);
        });


    }

    private void setOrders(int userId, int status) {
        ArrayList<Order> orders = DBHelper.getOrdersByUserID(getApplicationContext(), userId, status);

        if (orders.isEmpty()) {
            Toast.makeText(this, "No orders found", Toast.LENGTH_SHORT).show();
        } else {
            orderView.setLayoutManager(new LinearLayoutManager(this));

            for (Order order : orders) {
                System.out.println(order.getOrderID());
            }
            adapter = new User_OrderAdapter(this, orders);
            orderView.setAdapter(adapter);
        }

    }
}