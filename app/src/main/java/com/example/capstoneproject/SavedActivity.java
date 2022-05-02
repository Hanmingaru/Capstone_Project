package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.capstoneproject.adapters.RecyclerAdapter;
import com.example.capstoneproject.daos.RecipeDao;
import com.example.capstoneproject.entities.Recipe;
import com.example.capstoneproject.fragments.NavBarFragment;
import com.example.capstoneproject.globals.RecipeApplication;

import java.util.List;

public class SavedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView emptyView;
    RecyclerAdapter recyclerAdapter;
    List<Recipe> savedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.navbar_fragment_id, NavBarFragment.class, null)
                    .commit();
        }

        // Retrieve list of saved recipes from db
        final RecipeDao recipeDao = ((RecipeApplication)  getApplicationContext())
                .getRecipeDB().recipeDao();

        savedRecipes = recipeDao.getAll();

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

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
            }
        });

        // Create onClickListener for favorites button
        Button favoritesButton = (Button) findViewById(R.id.favoritesFilter);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                recyclerAdapter.setFavoritesSelected(true);
                // Pass "favorites" through to specify filter type
                recyclerAdapter.getFilter().filter("favorites");
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