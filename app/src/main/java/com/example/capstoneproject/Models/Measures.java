package com.example.capstoneproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Measures implements Parcelable {
    public Us us;
    public Metric metric;

    protected Measures(Parcel in) {
    }

    public static final Creator<Measures> CREATOR = new Creator<Measures>() {
        @Override
        public Measures createFromParcel(Parcel in) {
            return new Measures(in);
        }

        @Override
        public Measures[] newArray(int size) {
            return new Measures[size];
        }
    };

    public Us getUs() {
        return us;
    }

    public Metric getMetric() {
        return metric;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}