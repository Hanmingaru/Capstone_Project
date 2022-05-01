/*
 * Created by Elliott Rheault on 2022.4.26
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.Listeners;

import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeSearch;

import java.util.List;
import java.util.Random;

public interface RecipeByIdListener {
    void didFetch(RandomRecipe response, String message, int index);
    void didError(String message);
}