package com.example.capstoneproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ExtendedIngredient implements Parcelable {
    public int id;
    public String aisle;
    public String image;
    public String consistency;
    public String name;
    public String nameClean;
    public String original;
    public String originalString;
    public String originalName;
    public double amount;
    public String unit;
    public List<String> meta;
    public List<String> metaInformation;

    protected ExtendedIngredient(Parcel in) {
        id = in.readInt();
        aisle = in.readString();
        image = in.readString();
        consistency = in.readString();
        name = in.readString();
        nameClean = in.readString();
        original = in.readString();
        originalString = in.readString();
        originalName = in.readString();
        amount = in.readDouble();
        unit = in.readString();
        meta = in.createStringArrayList();
        metaInformation = in.createStringArrayList();
    }

    public static final Creator<ExtendedIngredient> CREATOR = new Creator<ExtendedIngredient>() {
        @Override
        public ExtendedIngredient createFromParcel(Parcel in) {
            return new ExtendedIngredient(in);
        }

        @Override
        public ExtendedIngredient[] newArray(int size) {
            return new ExtendedIngredient[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getAisle() {
        return aisle;
    }

    public String getImage() {
        return image;
    }

    public String getConsistency() {
        return consistency;
    }

    public String getName() {
        return name;
    }

    public String getNameClean() {
        return nameClean;
    }

    public String getOriginal() {
        return original;
    }

    public String getOriginalString() {
        return originalString;
    }

    public String getOriginalName() {
        return originalName;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public List<String> getMeta() {
        return meta;
    }

    public List<String> getMetaInformation() {
        return metaInformation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(aisle);
        parcel.writeString(image);
        parcel.writeString(consistency);
        parcel.writeString(name);
        parcel.writeString(nameClean);
        parcel.writeString(original);
        parcel.writeString(originalString);
        parcel.writeString(originalName);
        parcel.writeDouble(amount);
        parcel.writeString(unit);
        parcel.writeStringList(meta);
        parcel.writeStringList(metaInformation);
    }
}