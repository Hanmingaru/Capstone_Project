package com.example.capstoneproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Temperature implements Parcelable {
    public int number;
    public String unit;

    protected Temperature(Parcel in) {
        number = in.readInt();
        unit = in.readString();
    }

    public static final Creator<Temperature> CREATOR = new Creator<Temperature>() {
        @Override
        public Temperature createFromParcel(Parcel in) {
            return new Temperature(in);
        }

        @Override
        public Temperature[] newArray(int size) {
            return new Temperature[size];
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