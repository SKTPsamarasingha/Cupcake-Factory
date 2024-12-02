package com.example.cupcake.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cupcake.Model.Cupcake;
import com.example.cupcake.R;

import java.util.ArrayList;

public class CupcakeAdapter extends ArrayAdapter<Cupcake> {
    public CupcakeAdapter(Context context, ArrayList<Cupcake> cupcakes) {
        super(context, 0, cupcakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cupcake cupcake = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drop_down_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.dropdownItem);
        if (cupcake != null) {
            textView.setText(cupcake.getName().toString());
        }

        return convertView;
    }
}


