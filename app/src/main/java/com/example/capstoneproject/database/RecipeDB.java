/*
 * Created by Elliott Rheault on 2022.3.31
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

/*
Documentation:  https://developer.android.com/training/data-storage/room#groovy
                https://developer.android.com/reference/kotlin/androidx/room/Database
                https://medium.com/mindorks/using-room-database-android-jetpack-675a89a0e942
*/

package com.example.capstoneproject.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.capstoneproject.daos.RecipeDao;
import com.example.capstoneproject.entities.Recipe;

@Database(entities = {Recipe.class}, version=1)
public abstract class RecipeDB extends RoomDatabase {

    private static final String DB_NAME = "recipe_db";
    private static RecipeDB recipeInstance;

    public static synchronized RecipeDB getInstance(Context context) {
        if (recipeInstance == null) {
            recipeInstance = Room.databaseBuilder(context.getApplicationContext(),
                    RecipeDB.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return  recipeInstance;
    }

    public abstract RecipeDao recipeDao();

}
