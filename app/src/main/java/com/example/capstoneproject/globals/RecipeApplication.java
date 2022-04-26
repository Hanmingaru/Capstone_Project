/*
 * Created by Elliott Rheault on 2022.4.1
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

// Documentation: https://guides.codepath.com/android/Room-Guide

package com.example.capstoneproject.globals;

import android.app.Application;

import com.example.capstoneproject.database.RecipeDB;

/**
 * Base class for maintaining global application state.
 *
 * See bottom for how to use database
 *
 */
public class RecipeApplication extends Application {

    private RecipeDB recipeDB;

    /*
    =====================================
    |       Getter/Setter Methods       |
    =====================================
     */
    public RecipeDB getRecipeDB() {
        return recipeDB;
    }

    /*
    ============================================
    |        Method runs on app startup        |
    ============================================
     */
    @Override
    public void onCreate() {
        super.onCreate();

        recipeDB = RecipeDB.getInstance(this);
    }

    /*
    =====================================================================================
    |                                How to use Database                                |
    =====================================================================================
    1) Get a reference to the Recipe Database Access Object (see RecipeDao for more info)
    final RecipeDao recipeDao = ((RecipeApplication) getApplicationContext())
            .getRecipeDB().recipeDao();

    2) Set up an Asynchronous Task to execute database operation on a different thread
    AsyncTask.execute(new Runnable() {
        @Override
        public void run() {

            3) Use RecipeDao to perform CRUD operations on the database
            recipeDao.insertRecipe(recipe);
        }
    });
    =====================================================================================
     */

}