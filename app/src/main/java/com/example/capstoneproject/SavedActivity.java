package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.example.capstoneproject.adapters.RecyclerAdapter;
import com.example.capstoneproject.daos.RecipeDao;
import com.example.capstoneproject.entities.Recipe;
import com.example.capstoneproject.globals.RecipeApplication;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SavedActivity extends AppCompatActivity {

    EmptyRecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    List<Recipe> savedRecipes;
    private BottomNavigationView bottomNavigationView;
    TextView fillerAll;
    boolean isFavorite = false;
    private ItemTouchHelper itemTouchHelper;
    private RecipeDao recipeDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.recipe);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.swipe ) {
                    Intent intent = new Intent(SavedActivity.this, SwipeActivity.class);
                    intent.putParcelableArrayListExtra("swipeRecipe", getIntent().getParcelableArrayListExtra("swipeRecipe"));
                    intent.putExtra("swipeMacros", (RecipeNutritionResponse) getIntent().getParcelableExtra("swipeMacros"));
                    intent.putExtra("checkedItems", getIntent().getBooleanArrayExtra("checkedItems"));
                    intent.putExtra("searchQuery", getIntent().getStringExtra("searchQuery"));
                    intent.putExtra("searchResponses", getIntent().getParcelableArrayListExtra("searchResponses"));
                    startActivity(intent);
                    SavedActivity.this.overridePendingTransition(0, 0);
                    return true;
                }

                if (item.getItemId() == R.id.recipe) {
                    return true;
                }

                if (item.getItemId() == R.id.grocery) {
                    Intent intent = new Intent(SavedActivity.this, GroceryActivity.class);
                    intent.putParcelableArrayListExtra("swipeRecipe", getIntent().getParcelableArrayListExtra("swipeRecipe"));
                    intent.putExtra("swipeMacros", (RecipeNutritionResponse) getIntent().getParcelableExtra("swipeMacros"));
                    intent.putExtra("checkedItems", getIntent().getBooleanArrayExtra("checkedItems"));
                    intent.putExtra("searchQuery", getIntent().getStringExtra("searchQuery"));
                    intent.putExtra("searchResponses", getIntent().getParcelableArrayListExtra("searchResponses"));
                    startActivity(intent);
                    SavedActivity.this.overridePendingTransition(0, 0);
                    return true;
                }

                if (item.getItemId() == R.id.search) {
                    Intent intent = new Intent(SavedActivity.this, SearchActivity.class);
                    intent.putExtra("searchQuery", getIntent().getStringExtra("searchQuery"));
                    intent.putParcelableArrayListExtra("swipeRecipe", getIntent().getParcelableArrayListExtra("swipeRecipe"));
                    intent.putExtra("swipeMacros", (RecipeNutritionResponse) getIntent().getParcelableExtra("swipeMacros"));
                    intent.putExtra("checkedItems", getIntent().getBooleanArrayExtra("checkedItems"));
                    intent.putExtra("searchResponses", getIntent().getParcelableArrayListExtra("searchResponses"));
                    startActivity(intent);
                    SavedActivity.this.overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
        // Retrieve list of saved recipes from db
        recipeDao = ((RecipeApplication)  getApplicationContext())
                .getRecipeDB().recipeDao();

        savedRecipes = recipeDao.getAll();
        fillerAll = (TextView) findViewById(R.id.fillerSaved);
//        fillerFav = (TextView) findViewById(R.id.fillerFav);
        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
//        fillerFav.setVisibility(View.INVISIBLE);
        fillerAll.setText("~ You have no saved recipes ~");
        if (savedRecipes.size() != 0) {
            fillerAll.setVisibility(View.INVISIBLE);
        }


        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, savedRecipes, recipeDao);
        this.recyclerAdapter = recyclerAdapter;
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setEmptyView(fillerAll);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(recyclerAdapter, isFavorite));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Setup SearchView
        initSearchWidgets();
        // Setup tab layout
        setupTabLayout();
    }



    private void initSearchWidgets() {
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                savedRecipes = recipeDao.getAll();
                List<Recipe> filterList = new ArrayList<>();
                    if (isFavorite) {
                        for (Recipe recipe : savedRecipes) {
                            if (recipe.getFavorite() && recipe.getName().toLowerCase().contains(s.toLowerCase()))
                                filterList.add(recipe);
                        }
                    }
                    else {
                        for (Recipe recipe : savedRecipes) {
                            if (recipe.getName().toLowerCase().contains(s.toLowerCase()))
                                filterList.add(recipe);
                        }
                    }
                itemTouchHelper.attachToRecyclerView(null);
                final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(SavedActivity.this, filterList, recipeDao);
                itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(recyclerAdapter, isFavorite));
                itemTouchHelper.attachToRecyclerView(recyclerView);
                recyclerView.setAdapter(recyclerAdapter);
                return false;
            }
        });
    }

    private void setupTabLayout() {
        TabLayout tabLayout = findViewById(R.id.savedRecipesTab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                savedRecipes = recipeDao.getAll();
                List<Recipe> filterList = new ArrayList<>();
                if (tab.getPosition() == 1) {
                    isFavorite = true;
                    for (Recipe recipe : savedRecipes) {
                        if (recipe.getFavorite())
                            filterList.add(recipe);
                    }
                }
                else {
                    isFavorite = false;
                    for (Recipe recipe : savedRecipes) {
                        filterList.add(recipe);
                    }
                }
                itemTouchHelper.attachToRecyclerView(null);
                final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(SavedActivity.this, filterList, recipeDao);
                itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(recyclerAdapter, isFavorite));
                itemTouchHelper.attachToRecyclerView(recyclerView);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}