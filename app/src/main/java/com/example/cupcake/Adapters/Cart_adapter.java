package com.example.cupcake.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cupcake.Model.SelectedItems;
import com.example.cupcake.R;

import java.util.ArrayList;

public class Cart_adapter extends ArrayAdapter<SelectedItems> {
    private ArrayList<SelectedItems> selectedItems;
    private OnQuantityChangedListener quantityChangedListener;

    public Cart_adapter(Context context, ArrayList<SelectedItems> selectedItems) {
        super(context, 0, selectedItems);
        this.selectedItems = selectedItems;
    }

    public interface OnQuantityChangedListener {
        void onQuantityChanged(int position, int newQuantity);
    }

    public void setOnQuantityChangedListener(OnQuantityChangedListener listener) {
        this.quantityChangedListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SelectedItems selectedItem = selectedItems.get(position);

        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.cart_cell, null);
        }

        TextView name = convertView.findViewById(R.id.item_name);
        TextView quantity = convertView.findViewById(R.id.count);
        TextView price = convertView.findViewById(R.id.subTotal);
        Button decrement = convertView.findViewById(R.id.cartBtn);
        Button increment = convertView.findViewById(R.id.incrementBtn);

//        Button clearBtn = findViewById(R.id.clearBtn);

        name.setText(selectedItem.getCupcake().getName());
        quantity.setText(String.valueOf(selectedItem.getAmount()));
        updatePrice(price, selectedItem.getCupcake().getPrice(), selectedItem.getAmount());

        increment.setOnClickListener(v -> {
            int newQuantity = selectedItem.getAmount() + 1;
            selectedItem.setAmount(newQuantity);
            quantity.setText(String.valueOf(newQuantity));
            updatePrice(price, selectedItem.getCupcake().getPrice(), newQuantity);
            if (quantityChangedListener != null) {
                quantityChangedListener.onQuantityChanged(position, newQuantity);
            }
            notifyDataSetChanged();
        });

        decrement.setOnClickListener(v -> {
            if (selectedItem.getAmount() > 1) {
                int newQuantity = selectedItem.getAmount() - 1;
                selectedItem.setAmount(newQuantity);
                quantity.setText(String.valueOf(newQuantity));
                updatePrice(price, selectedItem.getCupcake().getPrice(), newQuantity);
                if (quantityChangedListener != null) {
                    quantityChangedListener.onQuantityChanged(position, newQuantity);
                }
                notifyDataSetChanged();
            }
        });

//        clearBtn.setOnClickListener(v ->{
//            selectedItems.remove(position);
//            notifyDataSetChanged();
//        });

        return convertView;
    }


    private void updatePrice(TextView priceTextView, int itemPrice, int quantity) {
        int totalPrice = itemPrice * quantity;
        priceTextView.setText("RS. " + totalPrice);
    }

    @Override
    public int getCount() {
        return selectedItems.size();
    }

    @Nullable
    @Override
    public SelectedItems getItem(int position) {
        return selectedItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
