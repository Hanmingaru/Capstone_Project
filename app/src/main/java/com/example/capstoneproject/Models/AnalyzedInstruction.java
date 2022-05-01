package com.example.capstoneproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class AnalyzedInstruction implements Parcelable {
    public String name;
    public List<Step> steps = new ArrayList<>();

    protected AnalyzedInstruction(Parcel in) {
        name = in.readString();
        in.readTypedList(steps, Step.CREATOR);
    }

    public static final Creator<AnalyzedInstruction> CREATOR = new Creator<AnalyzedInstruction>() {
        @Override
        public AnalyzedInstruction createFromParcel(Parcel in) {
            return new AnalyzedInstruction(in);
        }

        @Override
        public AnalyzedInstruction[] newArray(int size) {
            return new AnalyzedInstruction[size];
        }
    };

    public String getName() {
        return name;
    }

    public List<Step> getSteps() {
        return steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeTypedList(steps);
    }
}