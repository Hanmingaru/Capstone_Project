/*
 * Created by Elliott Rheault on 2022.4.11
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.Models;

import com.example.capstoneproject.entities.Recipe;

import java.util.List;

public class RecipeSearchResponse {
    List<RecipeSearch> results;

    public List<RecipeSearch> getRecipes() {
        return results;
    }
}
