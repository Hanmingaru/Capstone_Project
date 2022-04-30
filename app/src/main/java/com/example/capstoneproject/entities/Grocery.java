/*
 * Created by Elliott Rheault on 2022.4.22
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;


/**
 * See Recipe.java addIngredientsToGroceries(GroceryDao groceryDao)
 * for how to add a recipe's data to Grocery list
 */
@Fts4
@Entity(tableName = "grocery")
public class Grocery {

    /*
    ========================================================
    Instance variables representing the attributes (columns)
    of the Grocery table in the RecipeDB database.
    CREATE TABLE Grocery
    (
        rowid INT UNSIGNED NOT NULL AUTO_INCREMENT,
        recipeId INT UNSIGNED NOT NULL,
        name VARCHAR NOT NULL,
        found INT NOT NULL,
        aisle VARCHAR NOT NULL
    );
    ========================================================
     */

    // Specifying a primary key for an FTS-table-backed entity is optional, but
    // if you include one, it must use this type and column name.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private Integer id;

    // id of the recipe this ingredient belongs to
    @ColumnInfo(name = "recipeID")
    @NotNull
    private Integer recipeID;

    // name of the recipe this ingredient belongs to
    @ColumnInfo(name = "recipeName")
    @NotNull
    private String recipeName;

    // name of this ingredient
    @ColumnInfo(name = "name")
    @NotNull
    private String name;

    // Boolean representing if the user has checked off the ingredient from the list
    @ColumnInfo(name = "found")
    @NotNull
    private Boolean found;

    // Aisle this ingredient is located
    @ColumnInfo(name = "aisle")
    @NotNull
    private String aisle;

    /*
    ===============================================================
    Class constructors for instantiating a Grocery entity object to
    represent a row in the Grocery table in the RecipeDB database.
    ===============================================================
    */

    public Grocery() {
        found = null;
    }

    /**
     *
     * @param recipeID   ID of the recipe this grocery item belongs to
     * @param recipeName Name of the recipe this grocery item belongs to
     * @param name       Name of the ingredient
     * @param found      Boolean to update status of ingredient (used to keep
     *                     track if user marked item as found or not marked yet)
     * @param aisle      Name of the isle this ingredient is found in
     */
    @Ignore
    public Grocery(@NotNull Integer recipeID, @NotNull String recipeName, @NotNull String name, @NotNull Boolean found,
                   @NotNull String aisle) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.name = name;
        this.found = found;
        if (aisle == null || aisle == "?") {
            this.aisle = "Other";
        }
        else {
            this.aisle = aisle;
        }
    }
    /*
    ======================================================
    Getter and Setter methods for the attributes (columns)
    of the Grocery table in the RecipeDB database.
    ======================================================
    */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public Boolean getFound() {
        return found;
    }

    public void setFound(@NotNull Boolean found) {
        this.found = found;
    }

    @NotNull
    public String getAisle() {
        return aisle;
    }

    public void setAisle(@NotNull String aisle) {
        this.aisle = aisle;
    }

    @NotNull
    public Integer getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(@NotNull Integer recipeID) {
        this.recipeID = recipeID;
    }

    @NotNull
    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(@NotNull String recipeName) {
        this.recipeName = recipeName;
    }

    /*
    ================================
    Instance Methods Used Internally
    ================================
     */

    /*
     Checks if the Grocery object identified by 'object' is the same as the Grocery object
     identified by 'id' Parameter object = Grocery object identified by 'object'
     Returns True if the Grocery 'object' and 'id' are the same; otherwise, return False
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Grocery)) {
            return false;
        }
        Grocery other = (Grocery) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    // Return String representation of database primary key id
    @Override
    public String toString() {
        return id.toString();
    }
}
