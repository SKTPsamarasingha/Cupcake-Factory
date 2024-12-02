package com.example.cupcake.Model;


import androidx.annotation.NonNull;

import java.io.Serializable;

public class Category implements Serializable {
    private  final int categoryId;
    private final  String name;

    private  final  int offerID;

    private  final String info;

    public Category(int categoryId, String name, int offerID, String info) {
        this.categoryId = categoryId;
        this.name = name;
        this.offerID = offerID;
        this.info = info;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public int getOfferID() {
        return offerID;
    }

    public String getInfo() {
        return info;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
