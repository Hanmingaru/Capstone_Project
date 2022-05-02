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
    TextView fillerAll;
    TextView fillerSaved;

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