package com.example.cupcake.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cupcake implements Parcelable {
    private final int cupcakeID;
    private final int categoryID;
    private final String name;
    private final int price;
    private final String info;
//    private final byte[] image;

    public Cupcake(int cupcakeID, int categoryID, String name, int price, String info) {
        this.cupcakeID = cupcakeID;
        this.categoryID = categoryID;
        this.name = name;
        this.price = price;
        this.info = info;
//        this.image = image;
    }

    protected Cupcake(Parcel in) {
        cupcakeID = in.readInt();
        categoryID = in.readInt();
        name = in.readString();
        price = in.readInt();
        info = in.readString();
//        image = in.createByteArray();
    }

    public static final Creator<Cupcake> CREATOR = new Creator<Cupcake>() {
        @Override
        public Cupcake createFromParcel(Parcel in) {
            return new Cupcake(in);
        }

        @Override
        public Cupcake[] newArray(int size) {
            return new Cupcake[size];
        }
    };

    public int getCupcakeID() {
        return cupcakeID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getInfo() {
        return info;
    }

//    public byte[] getImage() {
//        return image;
//    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(cupcakeID);
        dest.writeInt(categoryID);
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeString(info);
//        dest.writeByteArray(image);
    }
}