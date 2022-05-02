package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.example.capstoneproject.adapters.RecyclerAdapter;
import com.example.capstoneproject.daos.RecipeDao;
import com.example.capstoneproject.entities.Recipe;
import com.example.capstoneproject.fragments.NavBarFragment;
import com.example.capstoneproject.globals.RecipeApplication;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class SavedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView emptyView;
    RecyclerAdapter recyclerAdapter;
    List<Recipe> savedRecipes;
    private BottomNavigationView bottomNavigationView;
    TextView fillerAll;
    TextView fillerSaved;
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
        final RecipeDao recipeDao = ((RecipeApplication)  getApplicationContext())
                .getRecipeDB().recipeDao();

        savedRecipes = recipeDao.getAll();
        fillerAll = (TextView) findViewById(R.id.filler);
        fillerSaved = (TextView) findViewById(R.id.filler2);
        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        fillerSaved.setVisibility(View.INVISIBLE);
        if (savedRecipes.size() != 0) {
            fillerAll.setVisibility(View.INVISIBLE);
        }


        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, savedRecipes, recipeDao);
        this.recyclerAdapter = recyclerAdapter;
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(recyclerAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Setup SearchView
        initSearchWidgets();

        // Create onClickListener for all button
        Button allButton = (Button) findViewById(R.id.allFilter);
        allButton.setBackgroundColor(getResources().getColor(R.color.red));
        allButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                recyclerAdapter.setFavoritesSelected(false);
                // Pass "all" through to specify filter type
                recyclerAdapter.getFilter().filter("all");
                fillerSaved.setVisibility(View.INVISIBLE);
                if (recyclerAdapter.getItemCount() == 0) {
                    fillerAll.setVisibility(View.VISIBLE);
                }
                else {
                    fillerAll.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Create onClickListener for favorites button
        Button favoritesButton = (Button) findViewById(R.id.favoritesFilter);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                recyclerAdapter.setFavoritesSelected(true);
                // Pass "favorites" through to specify filter type
                recyclerAdapter.getFilter().filter("favorites");
                fillerAll.setVisibility(View.INVISIBLE);
                if (recyclerAdapter.getItemCount() == 0) {
                    fillerSaved.setVisibility(View.VISIBLE);
                }
                else {
                    fillerSaved.setVisibility(View.INVISIBLE);
                }
            }
        });
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
                if (recyclerAdapter.getFavoritesSelected()) {
                    s += "*favorites";
                }
                recyclerAdapter.getFilter().filter(s);
                return false;
            }
        });
    }



}