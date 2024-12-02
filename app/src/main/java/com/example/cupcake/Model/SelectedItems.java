package com.example.cupcake.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SelectedItems implements Parcelable {
    private  Cupcake cupcake;
    private  int amount;

    public SelectedItems(Cupcake cupcake, int amount) {
        this.cupcake = cupcake;
        this.amount = amount;
    }

    protected SelectedItems(Parcel in) {
        cupcake = in.readParcelable(Cupcake.class.getClassLoader());
        amount = in.readInt();
    }

    public static final Creator<SelectedItems> CREATOR = new Creator<SelectedItems>() {
        @Override
        public SelectedItems createFromParcel(Parcel in) {
            return new SelectedItems(in);
        }

        @Override
        public SelectedItems[] newArray(int size) {
            return new SelectedItems[size];
        }
    };

    public Cupcake getCupcake() {
        return cupcake;
    }

    public int getAmount() {
        return amount;
    }

    public void setCupcake(Cupcake cupcake) {
        this.cupcake = cupcake;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(cupcake, flags);
        dest.writeInt(amount);
    }
}
