/*
 * Created by Elliott Rheault on 2022.3.31
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

// Documentation: https://developer.android.com/training/data-storage/room/accessing-data

package com.example.capstoneproject.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.capstoneproject.entities.FavoriteRecipe;

import java.util.List;

@Dao
public interface FavoriteRecipeDao {

    @Query("SELECT * FROM favorite_recipe")
    List<FavoriteRecipe> getAll();

    @Query("SELECT * FROM favorite_recipe WHERE rowid IN (:favoriteRecipeIds)")
    List<FavoriteRecipe> loadAllByIds(int[] favoriteRecipeIds);

    @Query("SELECT * FROM favorite_recipe WHERE name LIKE :name")
    List<FavoriteRecipe> findAllByName(String name);

    @Query("SELECT * FROM favorite_recipe WHERE name LIKE :name LIMIT 1")
    FavoriteRecipe findByName(String name);

    // ... notation means that 0 or more FavoriteRecipe object
    // will be passed in as an array
    @Insert
    void insertAll(FavoriteRecipe... favoriteRecipes);

    @Update
    void updateFavoriteRecipes(FavoriteRecipe... favoriteRecipes);

    @Delete
    void delete(FavoriteRecipe favoriteRecipe);
}
