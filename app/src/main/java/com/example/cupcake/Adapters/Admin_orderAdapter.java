package com.example.cupcake.Adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.Model.Order;
import com.example.cupcake.R;

import java.util.ArrayList;

public class Admin_orderAdapter extends RecyclerView.Adapter<Admin_orderAdapter.OrderViewHolder> {


    private ArrayList<Order> orders;
    private Context context;

    public  Admin_orderAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_cell, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_orderAdapter.OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
    }



    @Override
    public int getItemCount() {
        return orders.size();
    }
    public void clearData() {
        orders.clear();
        notifyDataSetChanged();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView username;
        private final TextView date;
        private final TextView orderId;
        private final TextView totalPrice;
        private final TableLayout orderTable;
        private final Button acceptButton;
        private final Button declineButton;
//        private final TextView messageText;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.orderCellUserName);
            date = itemView.findViewById(R.id.orderCellData);
            orderId = itemView.findViewById(R.id.orderCellOrderID);
            totalPrice = itemView.findViewById(R.id.orderCellTotalPrice);
            orderTable = itemView.findViewById(R.id.orderTable);
            acceptButton = itemView.findViewById(R.id.button5);
            declineButton = itemView.findViewById(R.id.button7);
//            messageText = itemView.findViewById(R.id.textView);
        }

        public void bind(Order order) {
            username.setText(order.getUsername());
            date.setText(order.getOrderDate());
            orderId.setText("OrderID: " + order.getOrderID());
            totalPrice.setText("Total: " + order.getTotalPrice());

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


            acceptButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Order acceptedOrder = orders.get(position);
                    orders.remove(position);
                    notifyItemRemoved(position);

                    handleAcceptedOrder(acceptedOrder);
                }
            });

            declineButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Order declinedOrder = orders.get(position);
                    orders.remove(position);
                    notifyItemRemoved(position);

                    handleDeclinedOrder(declinedOrder);
                }
            });

            // Set message if needed
//            messageText.setText("");
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

        private void handleAcceptedOrder(Order order) {
           int id = order.getOrderID();
          boolean result =   DBHelper.acceptOrder(context.getApplicationContext(),id );

          if(result) {
              Toast.makeText(context, "Order Accepted", Toast.LENGTH_SHORT).show();
          } else {
              Toast.makeText(context, "Failed to accept order", Toast.LENGTH_SHORT).show();
          }
        }

        private void handleDeclinedOrder(Order order) {
            int id = order.getOrderID();
            boolean result =   DBHelper.deleteOrder(context.getApplicationContext(),id );

            if(result) {
                Toast.makeText(context, "Order Declined", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to Declined order", Toast.LENGTH_SHORT).show();
            }
        }
    }
}