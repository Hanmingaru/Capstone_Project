/*
 * Created by Elliott Rheault on 2022.4.11
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.capstoneproject.Listeners.RandomAPIResponseListener;
import com.example.capstoneproject.Listeners.RecipeSearchResponseListener;
import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeSearch;
import com.example.capstoneproject.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RequestManager manager;
    private RecyclerView recyclerView;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.navbar_fragment_id, NavBarFragment.class, null)
                    .commit();
        }
        context = SearchActivity.this;
        manager = new RequestManager(this);
        recyclerView = findViewById(R.id.search_recycler_view);
        SearchAdapter searchAdapter = new SearchAdapter(SearchActivity.this , new ArrayList<>());
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        SearchView searchView = findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                manager.SearchRecipe(recipeSearchResponseListener, s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
    private final RecipeSearchResponseListener recipeSearchResponseListener = new RecipeSearchResponseListener() {
        @Override
        public void didFetch(List<RecipeSearch> responses, String message) {
            SearchAdapter searchAdapter = new SearchAdapter(SearchActivity.this , responses);
            recyclerView.setAdapter(searchAdapter);
        }

        @Override
        public void didError(String message) {
            Log.d("didError", "This is an error");
        }
    };


}