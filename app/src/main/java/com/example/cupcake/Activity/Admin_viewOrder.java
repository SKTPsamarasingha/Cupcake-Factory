package com.example.cupcake.Activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cupcake.Adapters.Admin_orderAdapter;
import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.Model.Order;
import com.example.cupcake.R;

import java.util.ArrayList;
import java.util.Objects;

public class Admin_viewOrder extends AppCompatActivity {
    Admin_orderAdapter adapter;

    Button preBtn, newBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_admin_view_order);

        preBtn = findViewById(R.id.PreviousBtn);
        newBtn = findViewById(R.id.NewOrdersBtn);
        setOrders(0);


        preBtn.setOnClickListener(v -> {
            if (adapter != null){
                adapter.clearData();
            }
            setOrders(1);
        });
        newBtn.setOnClickListener(v -> {
            if (adapter != null){
                adapter.clearData();
            }
            setOrders(0);
        });

    }

    private void setOrders(int status) {
        ArrayList<Order> orders = DBHelper.getOrdersByStatus(getApplicationContext(), status);

        if (orders.isEmpty()) {
            Toast.makeText(this, "No orders found", Toast.LENGTH_SHORT).show();
        } else {
            RecyclerView recyclerView = findViewById(R.id.newOrderView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            for (Order order : orders) {
                System.out.println(order.getOrderID());
            }
            adapter = new Admin_orderAdapter(this, orders);
            recyclerView.setAdapter(adapter);
        }
    }


}