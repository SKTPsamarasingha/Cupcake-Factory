package com.example.cupcake.Model;

import androidx.annotation.NonNull;

public class Offer {
    private final  int offerID;
   private final String name;
    private final int discount;
    private final boolean isActive;



    public Offer(int offerID,String name, int discount, boolean isActive) {
        this.offerID = offerID;
        this.name = name;
        this.discount = discount;
        this.isActive = isActive;
    }
    public int getOfferID() {
        return offerID;
    }

    public String getName() {
        return name;
    }

    public int getDiscount() {
        return discount;
    }

    public boolean isActive() {
        return isActive;
    }
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
