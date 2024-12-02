package com.example.cupcake.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.cupcake.Model.Category;
import com.example.cupcake.Model.Cupcake;
import com.example.cupcake.Model.Offer;
import com.example.cupcake.Model.SelectedItems;
import com.example.cupcake.R;

import java.util.ArrayList;

public class Helper {
    static ArrayAdapter<String> adapterItem;


    static public boolean checkEmptyValue(ArrayList<String> data, int inputs) {
        boolean isEmpty = true;
        int count = 0;
        for (String input : data) {
            if (input.length() == 0) {
                count++;
                System.out.println(count);
                System.out.println(input);

            }
        }
        if (count == inputs) {
            isEmpty = false;
        }

        return isEmpty;
    }


    public static ArrayAdapter<String> getList(Object object, String name) {
        ArrayList<String> list = new ArrayList<>();

        if (name.equals("offers")) {

            ArrayList<Offer> offers = Helper.getOffers((Context) object);

            if (offers != null) {
                for (Offer offer : offers) {
                    list.add(offer.getName());
                }

            }
        }

        adapterItem = new ArrayAdapter<String>((Context) object, R.layout.drop_down_item, list);
        return adapterItem;
    }


    static public ArrayList<Offer> getOffers(Context context) {
        try (DBHelper DB = new DBHelper(context)) {
            Cursor offers = DB.viewData("SELECT * FROM offer");
            if (offers.getCount() > 0 && offers.moveToFirst()) {
                int offerIDIndex = offers.getColumnIndex("offerID");
                int offerNameIndex = offers.getColumnIndex("offerName");
                int discountIndex = offers.getColumnIndex("discount");
                int isActiveIndex = offers.getColumnIndex("isActive");

                ArrayList<Offer> offersList = new ArrayList<>();

                do {
                    int offerID = offers.getInt(offerIDIndex);
                    String offerName = offers.getString(offerNameIndex);
                    int discount = offers.getInt(discountIndex);
                    boolean isActive = offers.getInt(isActiveIndex) == 1;
                    Offer offer = new Offer(offerID, offerName, discount, isActive);
                    offersList.add(offer);
                } while (offers.moveToNext());
                return offersList;
            } else {
                return null;
            }
        }
    }


    static public ArrayList<Category> getCategories(Context context) {
        try (DBHelper DB = new DBHelper(context)) {
            Cursor categories = DB.viewData("SELECT * FROM Categorie");
            if (categories.getCount() > 0 && categories.moveToFirst()) {
                int categoryIDIndex = categories.getColumnIndex("CategoryID");
                int categoryNameIndex = categories.getColumnIndex("name");
                int categoryOfferID = categories.getColumnIndex("offerID");
                int categoryInfo = categories.getColumnIndex("info");

                ArrayList<Category> CategoryList = new ArrayList<>();

                do {
                    int categoryID = categories.getInt(categoryIDIndex);
                    String categoryName = categories.getString(categoryNameIndex);
                    int OfferID = categories.getInt(categoryOfferID);
                    String Info = categories.getString(categoryInfo);
                    Category category = new Category(categoryID, categoryName, OfferID, Info);
                    CategoryList.add(category);
                } while (categories.moveToNext());
                return CategoryList;
            } else {
                return null;
            }
        }
    }

    static public ArrayList<Cupcake> getCupcakes(Context context) {

        try (DBHelper DB = new DBHelper(context)) {
            Cursor cupcakes = DB.viewData("SELECT * FROM cupcake");
            if (cupcakes.getCount() > 0 && cupcakes.moveToFirst()) {
                int cupcakeIDIndex = cupcakes.getColumnIndex("cupcakeID");
                int categoryIDIndex = cupcakes.getColumnIndex("CategoryID");
                int cupcakeNameIndex = cupcakes.getColumnIndex("name");
                int cupcakePriceIndex = cupcakes.getColumnIndex("price");
                int cupcakeInfoIndex = cupcakes.getColumnIndex("info");
//                int cupcakeImgIndex = cupcakes.getColumnIndex("img");

                ArrayList<Cupcake> cupcakeList = new ArrayList<>();

                do {
                    int cupcakeID = cupcakes.getInt(cupcakeIDIndex);
                    int categoryID = cupcakes.getInt(categoryIDIndex);
                    String name = cupcakes.getString(cupcakeNameIndex);
                    int price = cupcakes.getInt(cupcakePriceIndex);
                    String info = cupcakes.getString(cupcakeInfoIndex);
//                    byte[] img = cupcakes.getBlob(cupcakeImgIndex);
                    Cupcake cupcake = new Cupcake(cupcakeID, categoryID, name, price, info);
                    cupcakeList.add(cupcake);
                } while (cupcakes.moveToNext());
                return cupcakeList;
            } else {
                return null;
            }

        }
    }


    static public ArrayList<Cupcake> getCupcakeByID(Context context, int id) {
        ArrayList<Cupcake> newCupcake = new ArrayList<>();

        ArrayList<Cupcake> allCupcakes = getCupcakes(context);

        if (allCupcakes != null) {
            for (Cupcake cupcake : allCupcakes) {
                if (cupcake.getCategoryID() == id) {
                    newCupcake.add(cupcake);
                }
            }
        }

        if (!newCupcake.isEmpty()) {
            return newCupcake;
        }
        return null;
    }

    public static ArrayList<SelectedItems> getSelected(ArrayList<?> data) {
        ArrayList<SelectedItems> selectedItems = new ArrayList<>();
        try {
            if (data != null) {
                for (Object objects : data) {
                    if (objects instanceof SelectedItems) {
                        selectedItems.add((SelectedItems) objects);
                        System.out.println(objects);
                    } else {
                        System.out.println("Unexpected data type: " + data.getClass());
                    }
                }
            }
            return selectedItems;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


