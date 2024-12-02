//package com.example.cupcake.Adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TableLayout;
//import android.widget.TableRow;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.cupcake.Model.Order;
//import com.example.cupcake.R;
//
//import java.util.List;
//
//public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
//    private final Context context;
//    private final List<Order> orders;
//
//    public OrderAdapter(Context context, List<Order> orders) {
//        this.context = context;
//        this.orders = orders;
//    }
//
//    @NonNull
//    @Override
//    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.single_order, parent, false);
//        return new OrderViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
//        Order order = orders.get(position);
//        holder.bind(order);
//    }
//
//    @Override
//    public int getItemCount() {
//        return orders.size();
//    }
//
//    public class OrderViewHolder extends RecyclerView.ViewHolder {
//        private final TextView username, date, orderID, totalPrice;
//        private final TableLayout tableLayout;
//
//        public OrderViewHolder(@NonNull View itemView) {
//            super(itemView);
//            username = itemView.findViewById(R.id.orderCellUserName);
//            date = itemView.findViewById(R.id.orderCellData);
//            orderID = itemView.findViewById(R.id.orderCellOrderID);
//            totalPrice = itemView.findViewById(R.id.orderCellTotalPrice);
//            tableLayout = itemView.findViewById(R.id.orderTable);
//        }
//
//        public void bind(Order order) {
//            username.setText(order.getUsername());
//            date.setText(order.getOrderDate());
//            orderID.setText(context.getString( order.getOrderID()));
//            totalPrice.setText(context.getString(R.string.total_price_format, order.getTotalPrice()));
//
//            populateTable(order);
//        }
//
//        private void populateTable(Order order) {
//            tableLayout.removeAllViews();
//            addTableHeader();
//            addTableData(order);
//        }
//
//        private void addTableHeader() {
//            String[] headers = {"Cupcake", "Quantity", "Subtotal"};
//            TableRow headerRow = new TableRow(context);
//            for (String header : headers) {
//                TextView headerText = createTableCell(header, true);
//                headerRow.addView(headerText);
//            }
//            tableLayout.addView(headerRow);
//        }
//
//        private void addTableData(Order order) {
//            for (String[] orderDetail : order.getOrderData()) {
//                TableRow row = new TableRow(context);
//                for (String data : orderDetail) {
//                    TextView cell = createTableCell(data, false);
//                    row.addView(cell);
//                }
//                tableLayout.addView(row);
//            }
//        }
//
//        private TextView createTableCell(String text, boolean isHeader) {
//            TextView textView = new TextView(context);
//            textView.setText(text);
//            TableRow.LayoutParams params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
//            textView.setLayoutParams(params);
//            textView.setPadding(8, 8, 8, 8);
//            if (isHeader) {
//                textView.setTextColor(ContextCompat.getColor(context, R.color.red));
//                textView.setTypeface(null, android.graphics.Typeface.BOLD);
//            }
//            return textView;
//        }
//    }
//}