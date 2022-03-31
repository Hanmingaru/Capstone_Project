package com.example.capstoneproject.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.capstoneproject.daos.RecipeDao;
import com.example.capstoneproject.entities.Recipe;

@Database(version=1, entities = {Recipe.class})
public abstract class RecipeDB extends RoomDatabase {
    public abstract RecipeDao recipeDao();
}
