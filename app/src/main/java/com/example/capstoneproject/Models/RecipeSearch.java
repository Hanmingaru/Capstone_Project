/*
 * Created by Elliott Rheault on 2022.4.11
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.capstoneproject.entities.Recipe;

public class RecipeSearch implements Parcelable {

    public int id;
    public String title;
    public int readyInMinutes;
    public String image;

    protected RecipeSearch(Parcel in) {
        id = in.readInt();
        title = in.readString();
        readyInMinutes = in.readInt();
        image = in.readString();
    }

    public static final Creator<RecipeSearch> CREATOR = new Creator<RecipeSearch>() {
        @Override
        public RecipeSearch createFromParcel(Parcel in) {
            return new RecipeSearch(in);
        }

        @Override
        public RecipeSearch[] newArray(int size) {
            return new RecipeSearch[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public int getReadyInMinutes() {
        return readyInMinutes;
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
        parcel.writeString(title);
        parcel.writeInt(readyInMinutes);
        parcel.writeString(image);
    }
}
