/*
 * Created by Elliott Rheault on 2022.4.4
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.Listeners;

import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;

import java.util.ArrayList;

public interface NutritionAPIResponseListener {
    void didFetch(RecipeNutritionResponse response, String message, int index);
    void didError(String message);
}
