/*
 * Created by Elliott Rheault on 2022.4.26
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.capstoneproject.entities.Grocery;
import com.example.capstoneproject.entities.Recipe;

import java.util.List;

@Dao
public interface GroceryDao {

    /*
    ===================================
    |         Grocery Queries         |
    ===================================
    */
    // Returns all Grocery items
    @Query("SELECT * FROM grocery")
    List<Grocery> getAll();

    // Return all Grocery items with name of recipeName
    @Query("SELECT * FROM grocery WHERE recipeName LIKE :recipeName")
    List<Grocery> findAllByRecipeName(String recipeName);

    // Return all Grocery items with similar names
    @Query("SELECT * FROM grocery WHERE name LIKE :name")
    List<Grocery> findAllByName(String name);

    // Returns all Grocery items that have been found (1 == true)
    @Query("SELECT * FROM grocery WHERE found == 1")
    List<Grocery> findAllFound();

    // Returns all Grocery items that have not been found (0 == false)
    @Query("SELECT * FROM grocery WHERE found == 0")
    List<Grocery> findAllNotFound();

    // Returns all Grocery items that are in the "aisle" aisle
    @Query("SELECT * FROM grocery WHERE aisle LIKE :aisle")
    List<Grocery> findAllByAisle(String aisle);

    /*
    =======================================
    |       Grocery CRUD Operations       |
    =======================================
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGrocery(Grocery grocery);

    // ... notation means that 0 or more Grocery object
    // will be passed in as an array
    @Insert
    void insertAll(Grocery... groceries);

    @Update
    void updateGrocery(Grocery grocery);

    @Update
    void updateGroceries(Grocery... groceries);

    /* *************************************************************
     *  This function can be used to delete all grocery objects    *
     *  that belong to the same Recipe. Use the findAllByRecipeID  *
     *  DAO and pass what it returns to this function.             *
     ***************************************************************/
    @Delete
    void delete(List<Grocery> groceries);

    @Delete
    void delete(Grocery grocery);

    // Delete all items from the grocery database
    @Query("DELETE FROM grocery")
    void deleteAll();
}
