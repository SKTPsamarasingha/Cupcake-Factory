package com.example.cupcake.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cupcake.Model.Order;
import com.example.cupcake.R;

import java.util.ArrayList;

public class User_OrderAdapter extends RecyclerView.Adapter<User_OrderAdapter.OrderViewHolder> {


    private ArrayList<Order> orders;
    private Context context;

    public User_OrderAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public User_OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_order_cell, parent, false);
        return new User_OrderAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull User_OrderAdapter.OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void clearData() {
        if (orders != null) {
            orders.clear();
        }
        notifyDataSetChanged();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView total;
        private final TextView date;
        private final TableLayout orderTable;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            total = itemView.findViewById(R.id.userOrderTotal);
            date = itemView.findViewById(R.id.date);
            orderTable = itemView.findViewById(R.id.userOrderTable);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Order order) {
            //setting title and status
            int status = Integer.parseInt(order.getOrderStatus());

            if (status == 0) {
                title.setText("We Still baking up this one");
            } else {
                title.setText("Your order has been accepted!");
            }

            total.setText("Total Rs: " + order.getTotalPrice());


            // Clear existing rows except the header
            int childCount = orderTable.getChildCount();
            if (childCount > 1) {
                orderTable.removeViews(1, childCount - 1);
            }

            // Add order items to the table
            for (String[] item : order.getOrderData()) {
                TableRow row = new TableRow(context);

                TextView cupcake = createTableCell(item[0], 4);
                TextView quantity = createTableCell(item[1], 3);
                TextView subtotal = createTableCell(item[2], 4);

                row.addView(cupcake);
                row.addView(quantity);
                row.addView(subtotal);

                orderTable.addView(row);
            }

        }


        private TextView createTableCell(String text, int weight) {
            TextView textView = new TextView(context);
            textView.setText(text);
            textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weight));
            textView.setPadding(10, 10, 10, 10);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            return textView;
        }
    }
}
