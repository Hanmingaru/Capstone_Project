/*
 * Created by Elliott Rheault on 2022.4.11
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.Listeners;

import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeSearch;

import java.util.List;

public interface RecipeSearchResponseListener {
    void didFetch(List<RecipeSearch> responses, String message);
    void didError(String message);
}
