/*
 * Created by Elliott Rheault on 2022.3.31
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

/*
Documentation:--------------------------------------------------------------------------------------
                        https://developer.android.com/training/data-storage/room#groovy
                        https://developer.android.com/reference/kotlin/androidx/room/Database
                        https://medium.com/mindorks/using-room-database-android-jetpack-675a89a0e942
Store Custom Data type: https://developer.android.com/training/data-storage/room/referencing-data
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

    // Name of the database file
    private static final String DB_NAME = "recipe_db";

    // Instance of the RecipeDB - See globals.RecipeApplication for access
    private static RecipeDB recipeInstance;

    /**
     * Invoke as RecipeDB.getInstance(this)
     *
     * This method is used to create the database. If there is no
     * instance created yet, set up Room database. If there is already
     * an instance, just return it. This method should only be called
     * by globals.RecipeApplication. Use the reference in that file
     *
     * @return reference to RecipeDB instance
     */
    public static synchronized RecipeDB getInstance(Context context) {
        if (recipeInstance == null) {
            recipeInstance = Room.databaseBuilder(context.getApplicationContext(),
                    RecipeDB.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return  recipeInstance;
    }

    /* Database access object used for CRUD operations for
       the RecipeDB database. */
    public abstract RecipeDao recipeDao();

}
