package com.example.capstoneproject.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recipe {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

}
