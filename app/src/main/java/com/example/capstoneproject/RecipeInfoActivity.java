/*
 * Created by Elliott Rheault on 2022.4.10
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneproject.Listeners.NutritionAPIResponseListener;
import com.example.capstoneproject.Listeners.RandomAPIResponseListener;
import com.example.capstoneproject.Listeners.RecipeByIdListener;
import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.example.capstoneproject.daos.RecipeDao;
import com.example.capstoneproject.entities.Recipe;
import com.example.capstoneproject.globals.RecipeApplication;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeInfoActivity extends AppCompatActivity {
    private RequestManager manager;
    private ImageView recipeImage;
    private TextView title;
    private TextView price;
    private TextView calories;
    private TextView proteins;
    private TextView fats;
    private TextView carbs;
    private TextView timeInMinutes;
    private TextView ingredients;
    private TextView instructions;
    private TextView link ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        recipeImage = findViewById(R.id.recipeImage5);
        title = findViewById(R.id.recipeInfoTitle);
        price = findViewById(R.id.price_text);
        link = findViewById(R.id.link_text);
        calories = findViewById(R.id.calories_text);
        proteins = findViewById(R.id.protein_text);
        fats = findViewById(R.id.fats_text);
        carbs = findViewById(R.id.carbs_text);
        timeInMinutes = findViewById(R.id.time_requirement_text);
        ingredients = findViewById(R.id.ingredients_text);
        instructions = findViewById(R.id.instructions_text);

        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());

        int recipeID = getIntent().getIntExtra("recipeID", 0);
        final RecipeDao recipeDao = ((RecipeApplication)  getApplicationContext())
                .getRecipeDB().recipeDao();
        if (recipeDao.findByRecipeID(recipeID) != null) {
            setViews(recipeDao.findByRecipeID(recipeID));
        } else {
            manager = new RequestManager(this);
            manager.GetRecipeByID(recipeListener, recipeID);
            manager.GetNutritionByID(nutritionListener, recipeID);
        }


    }
    private void setViews(Recipe recipe) {
        Picasso.get().load(recipe.getImageUrl()).into(recipeImage);
        title.setText(recipe.getName());
        String text = "<a href=" + recipe.getWebsiteLink() + "> Website Link </a>";
        link.setText(Html.fromHtml(text));
        price.append(String.format("$%.2f",recipe.getPricePerServing()/100.0));
        calories.append(recipe.getCalories().substring(0, recipe.getCalories().length()-1));
        proteins.append(recipe.getProtein());
        fats.append(recipe.getFat());
        carbs.append(recipe.getCarbs());
        timeInMinutes.append(recipe.getPreparationTime() + "");
        ingredients.append(recipe.ingredientsToString());
        instructions.append(recipe.instructionsToString());

    }

    private final RecipeByIdListener recipeListener = new RecipeByIdListener() {
        @Override
        public void didFetch(RandomRecipe response, String message) {
            Picasso.get().load(response.getImage()).into(recipeImage);
            title.setText(response.getTitle());
            String text = "<a href=" + response.getSourceUrl() + "> Website Link </a>";
            price.append(String.format("$%.2f",response.getPricePerServing()/100.0));
            link.setText(Html.fromHtml(text));
            timeInMinutes.append(response.getReadyInMinutes() + "");
            ingredients.append(response.ingredientsToString());
            instructions.append(response.instructionsToString());
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeInfoActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final NutritionAPIResponseListener nutritionListener = new NutritionAPIResponseListener() {
        @Override
        public void didFetch(RecipeNutritionResponse response, String message) {
            calories.append(response.getCalories().substring(0, response.getCalories().length()-1));
            proteins.append(response.getProtein());
            fats.append(response.getFat());
            carbs.append(response.getCarbs());
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeInfoActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}