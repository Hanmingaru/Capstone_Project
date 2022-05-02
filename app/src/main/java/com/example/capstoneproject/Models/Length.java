package com.example.capstoneproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Length implements Parcelable {
    public int number;
    public String unit;

    protected Length(Parcel in) {
        number = in.readInt();
        unit = in.readString();
    }

    public static final Creator<Length> CREATOR = new Creator<Length>() {
        @Override
        public Length createFromParcel(Parcel in) {
            return new Length(in);
        }

        @Override
        public Length[] newArray(int size) {
            return new Length[size];
        }
    };

    public int getNumber() {
        return number;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(number);
        parcel.writeString(unit);
    }
}