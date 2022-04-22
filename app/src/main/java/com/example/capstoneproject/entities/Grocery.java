/*
 * Created by Elliott Rheault on 2022.4.22
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

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
        name VARCHAR(512) NOT NULL
    );
    ========================================================
     */

    // Specifying a primary key for an FTS-table-backed entity is optional, but
    // if you include one, it must use this type and column name.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private Integer id;

}
