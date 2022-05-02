/*
 * Created by Elliott Rheault on 2022.4.10
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneproject.Models.ExtendedIngredient;
import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.example.capstoneproject.adapters.ViewPageAdapter;
import com.example.capstoneproject.daos.GroceryDao;
import com.example.capstoneproject.daos.RecipeDao;
import com.example.capstoneproject.entities.Grocery;
import com.example.capstoneproject.entities.Recipe;
import com.example.capstoneproject.fragments.IngredientsFragment;
import com.example.capstoneproject.fragments.InstructionFragment;
import com.example.capstoneproject.fragments.RecipeDetailFragment;
import com.example.capstoneproject.globals.RecipeApplication;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

public class RecipeInfoActivity extends AppCompatActivity {
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
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private Recipe dbRecipe;
    private RandomRecipe apiRecipe;
    private RecipeNutritionResponse nutrition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.recipeInfoTab);
        MaterialToolbar appBar = findViewById(R.id.topAppBar);
        recipeImage = findViewById(R.id.recipeImage5);
        title = findViewById(R.id.recipeInfoTitle);
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), getLifecycle());
        recipeID = getIntent().getIntExtra("recipeID", 0);
        apiRecipe = getIntent().getParcelableExtra("recipe");
        nutrition = getIntent().getParcelableExtra("nutrition");
        dbRecipe = null;

        final RecipeDao recipeDao = ((RecipeApplication)  getApplicationContext())
                .getRecipeDB().recipeDao();
        if (recipeDao.findByRecipeID(recipeID) != null) {
            dbRecipe = recipeDao.findByRecipeID(recipeID);
            setSavedView(dbRecipe);
        } else {
            setApiView(apiRecipe, nutrition);
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

        appBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.add_to_groceries) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RecipeInfoActivity.this);
                    builder.setTitle("Add to grocery");
                    builder.setMessage("Are you sure you want to add this item to grocery list?");
                    builder.setPositiveButton("Confirm", (DialogInterface dialogInterface, int i) -> {
                        addToGrocery();
                        Toast.makeText(RecipeInfoActivity.this, "Recipe added", Toast.LENGTH_SHORT).show();
                    });
                    builder.setNegativeButton(android.R.string.cancel, (DialogInterface dialogInterface, int i) -> {
                        Toast.makeText(RecipeInfoActivity.this, "Recipe not added", Toast.LENGTH_SHORT).show();
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
                return false;
            }
        });
        appBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setSavedView(Recipe dbRecipe) {
        Picasso.get().load(dbRecipe.getImageUrl()).into(recipeImage);
        title.setText(dbRecipe.getName());
        link = "<a href=" + dbRecipe.getWebsiteLink() + "> Website Link </a>";
        price = String.format("$%.2f",dbRecipe.getPricePerServing()/100.0);
        calories = dbRecipe.getCalories().substring(0, dbRecipe.getCalories().length()-1);
        proteins = dbRecipe.getProtein();
        fats = dbRecipe.getFat();
        carbs = dbRecipe.getCarbs();
        timeInMinutes = dbRecipe.getPreparationTime() + "";
        ingredients = dbRecipe.ingredientsToString();
        instructions = dbRecipe.instructionsToString();
        viewPageAdapter.addFragment(RecipeDetailFragment.newInstance(link, price, calories, proteins, fats,carbs,timeInMinutes), "Details");
        viewPageAdapter.addFragment(IngredientsFragment.newInstance(ingredients), "Ingredients");
        viewPageAdapter.addFragment(InstructionFragment.newInstance(instructions), "Instructions");
    }
    private void setApiView(RandomRecipe apiRecipe, RecipeNutritionResponse nutrition) {
        Picasso.get().load(apiRecipe.getImage()).into(recipeImage);
        title.setText(apiRecipe.getTitle());
        link = "<a href=" + apiRecipe.getSourceUrl() + "> Website Link </a>";
        price = String.format("$%.2f",apiRecipe.getPricePerServing()/100.0);
        calories = nutrition.getCalories().substring(0, nutrition.getCalories().length()-1);
        proteins = nutrition.getProtein();
        fats = nutrition.getFat();
        carbs = nutrition.getCarbs();
        timeInMinutes = apiRecipe.getReadyInMinutes() + "";
        ingredients = apiRecipe.ingredientsToString();
        instructions = apiRecipe.instructionsToString();
        viewPageAdapter.addFragment(RecipeDetailFragment.newInstance(link, price, calories, proteins, fats,carbs,timeInMinutes), "Details");
        viewPageAdapter.addFragment(IngredientsFragment.newInstance(ingredients), "Ingredients");
        viewPageAdapter.addFragment(InstructionFragment.newInstance(instructions), "Instructions");
    }

    private boolean addToGrocery(){
        final GroceryDao groceryDao = ((RecipeApplication)  getApplicationContext())
                .getRecipeDB().groceryDao();
        if (dbRecipe != null) {
            for (String ingredient : dbRecipe.getIngredientsList()) {
                Grocery groceryItem = new Grocery(dbRecipe.getRecipeID(), dbRecipe.getName(), ingredient, false, null);
                groceryDao.insertGrocery(groceryItem);
            }
            return true;
        } else if (apiRecipe != null) {
            for (ExtendedIngredient ingredient : apiRecipe.getExtendedIngredients())    {
                Grocery groceryItem = new Grocery(apiRecipe.getId(), apiRecipe.getTitle(), ingredient.getOriginal(), false, null);
                groceryDao.insertGrocery(groceryItem);
            }
            return true;
        }
        return false;
    }

}