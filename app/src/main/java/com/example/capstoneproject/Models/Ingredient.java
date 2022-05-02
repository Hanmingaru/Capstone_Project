package com.example.capstoneproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    public int id;
    public String name;
    public String localizedName;
    public String image;

    protected Ingredient(Parcel in) {
        id = in.readInt();
        name = in.readString();
        localizedName = in.readString();
        image = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(localizedName);
        parcel.writeString(image);
    }
}