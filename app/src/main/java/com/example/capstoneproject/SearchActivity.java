/*
 * Created by Elliott Rheault on 2022.4.11
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.capstoneproject.Listeners.RecipeSearchResponseListener;
import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.example.capstoneproject.Models.RecipeSearch;
import com.example.capstoneproject.adapters.SearchAdapter;
import com.example.capstoneproject.fragments.NavBarFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RequestManager manager;
    private EmptyRecyclerView recyclerView;
    private Context context;
    private BottomNavigationView bottomNavigationView;
    private String searchQuery;
    private ArrayList<RecipeSearch> searchResponses;
    private TextView filler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.search);
        searchResponses = new ArrayList<RecipeSearch>();
        context = SearchActivity.this;
        manager = new RequestManager(this);
        recyclerView = findViewById(R.id.search_recycler_view);

        SearchView searchView = findViewById(R.id.search_bar);
        filler = (TextView) findViewById(R.id.filler);

        searchQuery = getIntent().getStringExtra("searchQuery") != null ? getIntent().getStringExtra("searchQuery") : "";
        searchResponses = getIntent().getParcelableArrayListExtra("searchResponses") != null ? getIntent().getParcelableArrayListExtra("searchResponses") : searchResponses;
        searchView.setQuery(searchQuery, false);
        SearchAdapter searchAdapter = new SearchAdapter(SearchActivity.this , searchResponses, manager);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.swipe ) {
                    Intent intent = new Intent(SearchActivity.this, SwipeActivity.class);
                    intent.putParcelableArrayListExtra("swipeRecipe", getIntent().getParcelableArrayListExtra("swipeRecipe"));
                    intent.putExtra("swipeMacros", (RecipeNutritionResponse) getIntent().getParcelableExtra("swipeMacros"));
                    intent.putExtra("checkedItems", getIntent().getBooleanArrayExtra("checkedItems"));
                    intent.putExtra("searchQuery", searchQuery);
                    intent.putExtra("searchResponses", searchResponses);
                    startActivity(intent);
                    SearchActivity.this.overridePendingTransition(0, 0);
                    return true;
                }

                if (item.getItemId() == R.id.recipe) {
                    Intent intent = new Intent(SearchActivity.this, SavedActivity.class);
                    intent.putParcelableArrayListExtra("swipeRecipe", getIntent().getParcelableArrayListExtra("swipeRecipe"));
                    intent.putExtra("swipeMacros", (RecipeNutritionResponse) getIntent().getParcelableExtra("swipeMacros"));
                    intent.putExtra("checkedItems", getIntent().getBooleanArrayExtra("checkedItems"));
                    intent.putExtra("searchQuery", searchQuery);
                    intent.putExtra("searchResponses", searchResponses);
                    startActivity(intent);
                    SearchActivity.this.overridePendingTransition(0, 0);
                    return true;
                }

                if (item.getItemId() == R.id.grocery) {
                    Intent intent = new Intent(SearchActivity.this, GroceryActivity.class);
                    intent.putParcelableArrayListExtra("swipeRecipe", getIntent().getParcelableArrayListExtra("swipeRecipe"));
                    intent.putExtra("swipeMacros", (RecipeNutritionResponse) getIntent().getParcelableExtra("swipeMacros"));
                    intent.putExtra("checkedItems", getIntent().getBooleanArrayExtra("checkedItems"));
                    intent.putExtra("searchQuery", searchQuery);
                    intent.putExtra("searchResponses", searchResponses);
                    startActivity(intent);
                    SearchActivity.this.overridePendingTransition(0, 0);
                    return true;
                }

                if (item.getItemId() == R.id.search) {
                    return true;
                }
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.i("From Query", s);
                searchQuery = s;
                manager.SearchRecipe(recipeSearchResponseListener, s);
                recyclerView.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recyclerView.setEmptyView(filler);
                searchResponses.clear();
                recyclerView.setVisibility(View.INVISIBLE);
                return false;
            }
        });
    }

    private final RecipeSearchResponseListener recipeSearchResponseListener = new RecipeSearchResponseListener() {
        @Override
        public void didFetch(ArrayList<RecipeSearch> responses, String message) {
            searchResponses = responses;
            SearchAdapter searchAdapter = new SearchAdapter(SearchActivity.this , searchResponses, manager);
            recyclerView.setAdapter(searchAdapter);
        }

        @Override
        public void didError(String message) {
            Log.d("didError", "This is an error");
        }
    };
}