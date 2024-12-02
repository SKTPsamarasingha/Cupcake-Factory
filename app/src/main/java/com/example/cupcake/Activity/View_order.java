//package com.example.cupcake.Activity;
//
//
//import android.os.Bundle;
//import android.view.WindowManager;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.cupcake.Adapters.Order_Adapter;
//import com.example.cupcake.Model.Order;
//import com.example.cupcake.R;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//public class View_order extends AppCompatActivity {
//    public static ArrayList<Order> orderList = new ArrayList<Order>();
//    //    private ListView listView;
//    String[][] orderData = {
//            {"Vanilla Cupcake", "2", "10"},
//            {"Chocolate Cupcake", "3", "15"},
//            {"Strawberry Cupcake", "1", "5"},
//            {"Strawberry Cupcake", "1", "5"}
//    };
//    RecyclerView orderRecyclerView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        Objects.requireNonNull(getSupportActionBar()).hide();
//        setContentView(R.layout.activity_view_order);
//
//        String[] columnHeaders = {"Cupcake Name", "Quantity", "Subtotal"};
//
//
//
//
//        orderRecyclerView = findViewById(R.id.newOrderView);
//        Order_Adapter orderAdapter = new Order_Adapter(this, orderList);
//        orderRecyclerView.setAdapter(orderAdapter);
//        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//////////////////////////////////////
//        setupData();
//        setupList();
//        setupOnclickListener();
//    }
//
//    private void setupData() {
//
//        Order one = new Order("John", 11235, "Monday at 12.30", "55002", "false", orderData);
//        Order two = new Order("Sam", 2, "Tuesday at 12.30", "7650", "false", orderData);
//        Order three = new Order("Smith", 3, "Wednesday at 12.30", "56550", "false", orderData);
//        Order four = new Order("Whick", 4, "Friday at 12.30", "4550", "false", orderData);
//        Order one1 = new Order("John", 11235, "Monday at 12.30", "55002", "false", orderData);
//        Order two1 = new Order("Sam", 2, "Tuesday at 12.30", "7650", "false", orderData);
//        Order three1 = new Order("Smith", 3, "Wednesday at 12.30", "56550", "false", orderData);
//        Order four1 = new Order("Whick", 4, "Friday at 12.30", "4550", "false", orderData);
//
//        orderList.add(one);
//        orderList.add(two);
//        orderList.add(three);
////        orderList.add(four);
//
//        orderList.add(one1);
//        orderList.add(two1);
//        orderList.add(three1);
////        orderList.add(four1);
//        orderList.add(one);
//        orderList.add(two);
//        orderList.add(three);
//        orderList.add(four);
//
//    }
//
//    private void setupList() {
//        listView = (ListView) findViewById(R.id.newOrderListView);
//        Order_Adapter adapter = new Order_Adapter(getApplicationContext(), 0, orderList);
//        listView.setAdapter(adapter);
//    }
//
//    ;
//
//    private void setupOnclickListener() {
//    }
//}