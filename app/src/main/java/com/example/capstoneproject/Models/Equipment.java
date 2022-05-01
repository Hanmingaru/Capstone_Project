package com.example.capstoneproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Equipment implements Parcelable {
    public int id;
    public String name;
    public String localizedName;
    public String image;
    public Temperature temperature;

    protected Equipment(Parcel in) {
        id = in.readInt();
        name = in.readString();
        localizedName = in.readString();
        image = in.readString();
        this.temperature = in.readParcelable(Temperature.class.getClassLoader());
    }

    public static final Creator<Equipment> CREATOR = new Creator<Equipment>() {
        @Override
        public Equipment createFromParcel(Parcel in) {
            return new Equipment(in);
        }

        @Override
        public Equipment[] newArray(int size) {
            return new Equipment[size];
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

    public Temperature getTemperature() {
        return temperature;
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
        parcel.writeParcelable(temperature, i);
    }
}