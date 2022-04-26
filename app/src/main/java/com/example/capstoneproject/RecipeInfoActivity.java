/*
 * Created by Elliott Rheault on 2022.4.10
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;

public class RecipeInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        RandomRecipe recipeData = getIntent().getParcelableExtra("recipeData");
        RecipeNutritionResponse macroData = getIntent().getParcelableExtra("macroData");
        TextView tv = findViewById(R.id.recipeInfoTitle);
        tv.setText(recipeData.getTitle());
    }
}