/*
 * Created by Elliott Rheault on 2022.3.31
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

// Documentation: https://developer.android.com/training/data-storage/room/defining-data

package com.example.capstoneproject.entities;

import androidx.annotation.Size;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

// Use `@Fts3` only if your app has strict disk space requirements or if you
// require compatibility with an older SQLite version.
@Fts4
@Entity(tableName = "recipe")
public class Recipe {

     /*
    ========================================================
    Instance variables representing the attributes (columns)
    of the Recipe table in the RecipeDB database.
    CREATE TABLE Recipe
    (
        rowid INT UNSIGNED NOT NULL AUTO_INCREMENT,
        name VARCHAR(512) NOT NULL,
        favorite INT NOT NULL DEFAULT_VALUE(0)
    );
    ========================================================
     */

    // Specifying a primary key for an FTS-table-backed entity is optional, but
    // if you include one, it must use this type and column name.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private Integer id;

    // Name of the recipe
    @ColumnInfo(name = "name")
    @Size(min = 1, max = 512) // Not sure if Room/SQLite needs this
    @NotNull
    private String name;

    // Flag to mark recipe as a favorite
    // Stored as an Integer in the database
    @ColumnInfo(name = "favorite")
    @NotNull
    private Boolean favorite;

    // Flag to mark recipe as a favorite
    // Stored as an Integer in the database
    @ColumnInfo(name = "imageUrl")
    @NotNull
    private String imageUrl;

    /* more attributes ... */

    /*
    ==============================================================
    Class constructors for instantiating a Recipe entity object to
    represent a row in the Recipe table in the RecipeDB database.
    ==============================================================
    */
    public Recipe() {
    }

    public Recipe(Integer id) {
        this.id = id;
    }


    /**
     * Use this when creating a recipe will all attributes initialized
     *
     * @param name
     * @param favorite
     */
    public Recipe(@NotNull String name, @NotNull Boolean favorite) {
        this.name = name;
        this.favorite = favorite;
    }

    /**
     * Use this constructor when creating Recipe with default values.
     * Best to use when creating a recipe from swiping to add to database.
     *
     * @param name
     */
    public Recipe(@NotNull String name, @NotNull String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.favorite = false;
    }

    /*
    ======================================================
    Getter and Setter methods for the attributes (columns)
    of the Recipe table in the RecipeDB database.
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
    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(@NotNull Boolean favorite) {
        this.favorite = favorite;
    }

    @NotNull
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NotNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /*
    ================================
    Instance Methods Used Internally
    ================================
     */

    /*
     Checks if the Recipe object identified by 'object' is the same as the Recipe object
     identified by 'id' Parameter object = Recipe object identified by 'object'
     Returns True if the Recipe 'object' and 'id' are the same; otherwise, return False
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    // Return String representation of database primary key id
    @Override
    public String toString() {
        return id.toString();
    }
}
