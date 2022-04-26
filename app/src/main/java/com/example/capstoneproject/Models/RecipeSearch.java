/*
 * Created by Elliott Rheault on 2022.4.11
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.Models;

import com.example.capstoneproject.entities.Recipe;

public class RecipeSearch{

    public int id;
    public String title;
    public int readyInMinutes;
    public String image;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public String getImage() {
        return image;
    }

}
