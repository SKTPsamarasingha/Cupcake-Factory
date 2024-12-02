package com.example.cupcake.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cupcake.Model.Offer;
import com.example.cupcake.R;

import java.util.ArrayList;

public class OfferAdapter extends ArrayAdapter<Offer> {
    public OfferAdapter(Context context, ArrayList<Offer> offers) {
        super(context, 0, offers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object object = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drop_down_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.dropdownItem);
        textView.setText(((Offer) object).getName().toString());

        return convertView;
    }
}
