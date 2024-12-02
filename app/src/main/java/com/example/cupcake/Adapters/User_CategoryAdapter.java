package com.example.cupcake.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cupcake.Helpers.Helper;
import com.example.cupcake.Model.Category;
import com.example.cupcake.Model.Offer;
import com.example.cupcake.R;

import java.util.ArrayList;

public class User_CategoryAdapter extends ArrayAdapter<Category> {

    private ArrayList<Category> categoryList;
    private Context context;


    public User_CategoryAdapter(Context context, ArrayList<Category> categories) {
        super(context, 0, categories);
        this.categoryList = categories;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Category categoryClass = categoryList.get(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_cell, parent, false);
        }

        // Lookup view for data population
        TextView categoryName = convertView.findViewById(R.id.category_cell_name);
        TextView categoryDescription = convertView.findViewById(R.id.category_cell_Info);
        TextView categoryID = convertView.findViewById(R.id.category_ID);
        TextView offerText = convertView.findViewById(R.id.offer);

        // Populate the data into the template view using the data object
        categoryName.setText(categoryClass.getName());
        categoryDescription.setText(categoryClass.getInfo());
        categoryID.setText(String.valueOf(categoryClass.getCategoryId()));

        ArrayList<Offer> offers = Helper.getOffers(getContext());

        if(offers != null){
            for(Offer offer : offers){
                if(offer.getOfferID() == categoryClass.getOfferID()){
                    String offerString = context.getString(R.string.offer_text, offer.getName(), offer.getDiscount());

                    offerText.setText(offerString);
                } else {
                    offerText.setText("");
                }
            }
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
