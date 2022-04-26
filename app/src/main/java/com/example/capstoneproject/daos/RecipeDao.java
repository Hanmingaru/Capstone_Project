/*
 * Created by Elliott Rheault on 2022.3.31
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

// Documentation: https://developer.android.com/training/data-storage/room/accessing-data

package com.example.capstoneproject.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.capstoneproject.entities.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    /*
    ========================================
    |       Favorite Recipes Queries       |
    ========================================
    */
    @Query("SELECT * FROM recipe WHERE name LIKE :name AND favorite == 1")
    List<Recipe> loadFavoritesByName(String name);

    @Query("SELECT * FROM recipe WHERE favorite == 1")
    List<Recipe> getFavorite();

    /* --- Return all not-favorite queries ---*/
    @Query("SELECT * FROM recipe WHERE favorite == 0")
    List<Recipe> getNotFavorite();

    /*
    ===================================
    |       All Recipes Queries       |
    ===================================
    */
    @Query("SELECT * FROM recipe")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipe WHERE rowid IN (:recipeIds)")
    List<Recipe> loadAllByIds(int[] recipeIds);

    @Query("SELECT * FROM recipe WHERE name LIKE :name")
    List<Recipe> findAllByName(String name);

    @Query("SELECT * FROM recipe WHERE name LIKE :name LIMIT 1")
    Recipe findByName(String name);

    /*
    ======================================
    |       Recipe CRUD Operations       |
    ======================================
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipe recipe);

    // ... notation means that 0 or more Recipe object
    // will be passed in as an array
    @Insert
    void insertAll(Recipe... recipes);

    @Update
    void updateRecipe(Recipe recipe);

    @Update
    void updateRecipes(Recipe... recipes);

    @Delete
    void delete(Recipe recipe);

    @Query("DELETE FROM recipe WHERE name LIKE :name")
    void deleteName(String name);
}
