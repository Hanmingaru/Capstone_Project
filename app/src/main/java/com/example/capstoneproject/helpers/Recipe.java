/*
 * Created by Elliott Rheault on 2022.3.24
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.helpers;

public class Recipe {

    /*
    ----------------------------------
    |       Instance Variables       |
    ----------------------------------
     */

    int id;
    String recipeName;

    /*
    ----------------------------
    |       Constructors       |
    ----------------------------
     */
    public Recipe() {

    }

    public Recipe(String recipeName) {
        this.recipeName = recipeName;
    }

    public Recipe(int id, String recipeName) {
        this.id = id;
        this.recipeName = recipeName;
    }

    /*
    -----------------------------------
    |       Getters and Setters       |
    -----------------------------------
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }


}
