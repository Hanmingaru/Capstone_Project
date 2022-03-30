package com.example.capstoneproject.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.capstoneproject.entities.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipe WHERE uid IN (:recipeIds)")
    List<Recipe> loadAllByIds(int[] recipeIds);

    @Query("SELECT * FROM recipe WHERE name LIKE :name LIMIT 1")
    Recipe findByName(String name);

    @Delete
    void delete(Recipe recipe);
}
