/*
 * Created by Elliott Rheault on 2022.4.1
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

// Documentation: https://guides.codepath.com/android/Room-Guide

package com.example.capstoneproject.globals;

import android.app.Application;
import android.content.Context;

import com.example.capstoneproject.database.RecipeDB;

/**
 * Base class for maintaining global application state.
 */
public class RecipeApplication extends Application {

    RecipeDB recipeDB;

    @Override
    public void onCreate() {
        super.onCreate();

        recipeDB = RecipeDB.getInstance(this);
    }

    /*
     * Get reference to the RecipeDB
     */
    public RecipeDB getRecipeDB() {
        return recipeDB;
    }

}
