package com.example.capstoneproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Step implements Parcelable {
    public int number;
    public String step;
    public List<Ingredient> ingredients = new ArrayList<>();
    public List<Equipment> equipment = new ArrayList<>();
    public Length length;

    protected Step(Parcel in) {
        number = in.readInt();
        step = in.readString();
        in.readTypedList(ingredients, Ingredient.CREATOR);
        in.readTypedList(equipment, Equipment.CREATOR);
        this.length = in.readParcelable(Length.class.getClassLoader());
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getNumber() {
        return number;
    }

    public String getStep() {
        return step;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public Length getLength() {
        return length;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(number);
        parcel.writeString(step);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(equipment);
        parcel.writeParcelable(length, i);
    }
}