/*
 * Created by Elliott Rheault on 2022.4.10
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneproject.Listeners.NutritionAPIResponseListener;
import com.example.capstoneproject.Listeners.RecipeByIdListener;
import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.example.capstoneproject.daos.RecipeDao;
import com.example.capstoneproject.entities.Recipe;
import com.example.capstoneproject.globals.RecipeApplication;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeInfoActivity extends AppCompatActivity {
    private static RequestManager manager;
    private static ImageView recipeImage;
    private static TextView title;
    private static String price;
    private static String calories;
    private static String proteins;
    private static String fats;
    private static String carbs;
    private static String timeInMinutes;
    private int recipeID;
    private String ingredients;
    private String instructions;
    private static String link ;
    private ViewPageAdapter viewPageAdapter;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private RandomRecipe recipe;
    private RecipeNutritionResponse nutrition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.recipeInfoTab);

        recipeImage = findViewById(R.id.recipeImage5);
        title = findViewById(R.id.recipeInfoTitle);
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), getLifecycle());
        recipeID = getIntent().getIntExtra("recipeID", 0);
        recipe = getIntent().getParcelableExtra("recipe");
        nutrition = getIntent().getParcelableExtra("nutrition");
        final RecipeDao recipeDao = ((RecipeApplication)  getApplicationContext())
                .getRecipeDB().recipeDao();
        if (recipeDao.findByRecipeID(recipeID) != null) {
            setSavedView(recipeDao.findByRecipeID(recipeID));
        } else {
            setApiView(recipe, nutrition);
        }

        viewPager.setAdapter(viewPageAdapter);
        new TabLayoutMediator(
                tabLayout,
                viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(viewPageAdapter.getTitle(position));
                    }
                }
        ).attach();
    }

    private void setSavedView(Recipe recipe) {
        Picasso.get().load(recipe.getImageUrl()).into(recipeImage);
        title.setText(recipe.getName());
        link = "<a href=" + recipe.getWebsiteLink() + "> Website Link </a>";
        price = String.format("$%.2f",recipe.getPricePerServing()/100.0);
        calories = recipe.getCalories().substring(0, recipe.getCalories().length()-1);
        proteins = recipe.getProtein();
        fats = recipe.getFat();
        carbs = recipe.getCarbs();
        timeInMinutes = recipe.getPreparationTime() + "";
        ingredients = recipe.ingredientsToString();
        instructions = recipe.instructionsToString();
        viewPageAdapter.addFragment(RecipeDetailFragment.newInstance(link, price, calories, proteins, fats,carbs,timeInMinutes), "Details");
        viewPageAdapter.addFragment(IngredientsFragment.newInstance(ingredients), "Ingredients");
        viewPageAdapter.addFragment(InstructionFragment.newInstance(instructions), "Instructions");
    }
    private void setApiView(RandomRecipe recipe, RecipeNutritionResponse nutrition) {
        Picasso.get().load(recipe.getImage()).into(recipeImage);
        title.setText(recipe.getTitle());
        link = "<a href=" + recipe.getSourceUrl() + "> Website Link </a>";
        price = String.format("$%.2f",recipe.getPricePerServing()/100.0);
        calories = nutrition.getCalories().substring(0, nutrition.getCalories().length()-1);
        proteins = nutrition.getProtein();
        fats = nutrition.getFat();
        carbs = nutrition.getCarbs();
        timeInMinutes = recipe.getReadyInMinutes() + "";
        ingredients = recipe.ingredientsToString();
        instructions = recipe.instructionsToString();
        viewPageAdapter.addFragment(RecipeDetailFragment.newInstance(link, price, calories, proteins, fats,carbs,timeInMinutes), "Details");
        viewPageAdapter.addFragment(IngredientsFragment.newInstance(ingredients), "Ingredients");
        viewPageAdapter.addFragment(InstructionFragment.newInstance(instructions), "Instructions");
    }

}