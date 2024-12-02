package com.example.cupcake.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cupcake.Helpers.DBHelper;
import com.example.cupcake.Model.Cupcake;
import com.example.cupcake.R;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class User_CupcakesAdapter extends ArrayAdapter<Cupcake> {
    ArrayList<Cupcake> cupcakeList;
    private OnAddToCartClickListener mListener;

    public interface OnAddToCartClickListener {
        void addBtn(Cupcake cupcake);
    }

    public User_CupcakesAdapter(Context context, ArrayList<Cupcake> cupcakeList, OnAddToCartClickListener listener) {
        super(context, 0, cupcakeList);
        this.cupcakeList = cupcakeList;
        mListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Cupcake cupcakeClass = cupcakeList.get(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cupcake_cell, parent, false);
        }

        // Lookup view for data population
        TextView Name = convertView.findViewById(R.id.cupcake_name);
        TextView description = convertView.findViewById(R.id.cupcake_info);
        TextView ID = convertView.findViewById(R.id.cupcake_ID);
        TextView price = convertView.findViewById(R.id.cupcake_price);
        ImageView img = convertView.findViewById(R.id.cupcakeImg);
        Button addBtn = convertView.findViewById(R.id.addBtn);

        Cupcake cupcake = getItem(position);

        // Populate the data into the template view using the data object
        Name.setText(cupcakeClass.getName());
        description.setText(cupcakeClass.getInfo());
        ID.setText(String.valueOf(cupcakeClass.getCupcakeID()));
        price.setText((String.format("RS: " + cupcakeClass.getPrice())));

        byte[] bytesArr = DBHelper.getImage(getContext(),cupcakeClass.getCupcakeID());
        Bitmap bitmap = getBitmap(bytesArr);
        if(bitmap != null){
            img.setImageBitmap(bitmap);
        }




        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.addBtn(cupcake);
                }
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

    public static Bitmap blobToImage(Blob blob) {
        if (blob == null) {
            return null;
        }
        try {
            InputStream inputStream = blob.getBinaryStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (SQLException e) {
            return null;
        }
    }

    private Bitmap getBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }


}
