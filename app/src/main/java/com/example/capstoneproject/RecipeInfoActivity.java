/*
 * Created by Elliott Rheault on 2022.4.10
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
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

public class RecipeInfoActivity extends AppCompatActivity {
    private RequestManager manager;
    private ImageView recipeImage;
    private TextView title;
    private String price;
    private String calories;
    private String proteins;
    private String fats;
    private String carbs;
    private String timeInMinutes;
    private String ingredients;
    private String instructions;
    private String link ;

    private ViewPageAdapater viewPageAdapter;
    private Fragment recipeDetailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.recipeInfoTab);

        recipeImage = findViewById(R.id.recipeImage5);
        title = findViewById(R.id.recipeInfoTitle);

//        price = recipeDetailFragment.getView().findViewById(R.id.price_text);
//        link = findViewById(R.id.link_text);
//        calories = findViewById(R.id.calories_text);
//        proteins = findViewById(R.id.protein_text);
//        fats = findViewById(R.id.fats_text);
//        carbs = findViewById(R.id.carbs_text);
//        timeInMinutes = findViewById(R.id.time_requirement_text);
//        ingredients = findViewById(R.id.ingredients_text);
//        instructions = findViewById(R.id.instructions_text);
//
//        link.setClickable(true);
//        link.setMovementMethod(LinkMovementMethod.getInstance());

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

        viewPageAdapter = new ViewPageAdapater(getSupportFragmentManager(), getLifecycle());
        viewPageAdapter.addFragment(RecipeDetailFragment.newInstance(link, price, calories, proteins, fats,carbs,timeInMinutes), "Details");
        viewPageAdapter.addFragment(new IngredientsFragment(), "Tab 2");
        viewPageAdapter.addFragment(new IngredientsFragment(), "Tab 3");
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

    private void setViews(Recipe recipe) {
        Picasso.get().load(recipe.getImageUrl()).into(recipeImage);
        title.setText(recipe.getName());
//        String text = "<a href=" + recipe.getWebsiteLink() + "> Website Link </a>";
//        link.setText(Html.fromHtml(text));
//          price.append(String.format("$%.2f",recipe.getPricePerServing()/100.0));
//        calories.append(recipe.getCalories().substring(0, recipe.getCalories().length()-1));
//        proteins.append(recipe.getProtein());
//        fats.append(recipe.getFat());
//        carbs.append(recipe.getCarbs());
//        timeInMinutes.append(recipe.getPreparationTime() + "");
//        ingredients.append(recipe.ingredientsToString());
//        instructions.append(recipe.instructionsToString());

        link = "<a href=" + recipe.getWebsiteLink() + "> Website Link </a>";
        price = String.format("$%.2f",recipe.getPricePerServing()/100.0);
        calories = recipe.getCalories().substring(0, recipe.getCalories().length()-1);
        proteins = recipe.getProtein();
        fats = recipe.getFat();
        carbs = recipe.getCarbs();
        timeInMinutes = recipe.getPreparationTime() + "";
        ingredients = recipe.ingredientsToString();
        instructions = recipe.instructionsToString();
    }

    private RecipeByIdListener recipeListener = new RecipeByIdListener() {
        @Override
        public void didFetch(RandomRecipe response, String message) {
            Picasso.get().load(response.getImage()).into(recipeImage);
            title.setText(response.getTitle());
            String text = "<a href=" + response.getSourceUrl() + "> Website Link </a>";
//            price.append(String.format("$%.2f",response.getPricePerServing()/100.0));
//            link.setText(Html.fromHtml(text));
//            timeInMinutes.append(response.getReadyInMinutes() + "");
//            ingredients.append(response.ingredientsToString());
//            instructions.append(response.instructionsToString());
//
            link = "<a href=" + response.getSourceUrl() + "> Website Link </a>";
            price = String.format("$%.2f",response.getPricePerServing()/100.0);
            timeInMinutes = response.getReadyInMinutes() + "";
            ingredients = response.ingredientsToString();
            instructions = response.instructionsToString();

        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeInfoActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private NutritionAPIResponseListener nutritionListener = new NutritionAPIResponseListener() {
        @Override
        public void didFetch(RecipeNutritionResponse response, String message) {
//            calories.append(response.getCalories().substring(0, response.getCalories().length()-1));
//            proteins.append(response.getProtein());
//            fats.append(response.getFat());
//            carbs.append(response.getCarbs());

            calories = response.getCalories().substring(0, response.getCalories().length()-1);
            proteins = response.getProtein();
            fats = response.getFat();
            carbs = response.getCarbs();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeInfoActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };


}