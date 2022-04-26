/*
 * Created by Elliott Rheault on 2022.3.24
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.globals;

import androidx.room.TypeConverter;

import com.example.capstoneproject.Models.AnalyzedInstruction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*
 * Use this class to set commonly used methods
 */
public class Methods {

    /**
     * Converts a List<String> to a single String.
     * Used to save String lists to Database
     *
     * @param listOfString A list of Strings to be serialized
     * @return String representation of the list
     */
    @TypeConverter
    public static String serializeList(List<String> listOfString) {
        return new Gson().toJson(listOfString);
    }

    /**
     * Converts a Serialized String back into its List representation.
     * Used to get the List out of the database.
     *
     * @param serializedList a serialized string
     * @return List of Strings
     */
    @TypeConverter
    public static List<String> deserializeList(String serializedList) {
        return new Gson().fromJson(serializedList, new TypeToken<List<String>>() {}.getType());
    }


}
