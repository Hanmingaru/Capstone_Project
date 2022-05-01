/*
 * Created by Elliott Rheault on 2022.4.4
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecipeNutritionResponse implements Parcelable{
    public String calories;
    public String carbs;
    public String fat;
    public String protein;

    //default constructor
    public RecipeNutritionResponse() {

    }

    protected RecipeNutritionResponse(Parcel in) {
        calories = in.readString();
        carbs = in.readString();
        fat = in.readString();
        protein = in.readString();
    }

    public static final Creator<RecipeNutritionResponse> CREATOR = new Creator<RecipeNutritionResponse>() {
        @Override
        public RecipeNutritionResponse createFromParcel(Parcel in) {
            return new RecipeNutritionResponse(in);
        }

        @Override
        public RecipeNutritionResponse[] newArray(int size) {
            return new RecipeNutritionResponse[size];
        }
    };

    public String getCalories() {
        return calories;
    }

    public String getCarbs() {
        return carbs;
    }

    public String getFat() {
        return fat;
    }

    public String getProtein() {
        return protein;
    }

    public ArrayList<String> getMacros(){
        ArrayList<String> macroList = new ArrayList<String>();
        macroList.add(calories);
        macroList.add(carbs);
        macroList.add(fat);
        macroList.add(protein);
        return macroList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(calories);
        parcel.writeString(carbs);
        parcel.writeString(fat);
        parcel.writeString(protein);
    }
}
