package com.example.capstoneproject;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.capstoneproject.Listeners.NutritionAPIResponseListener;
import com.example.capstoneproject.Listeners.RandomAPIResponseListener;
import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.example.capstoneproject.adapters.DeckAdapter;
import com.example.capstoneproject.daos.RecipeDao;
import com.example.capstoneproject.entities.Recipe;
import com.example.capstoneproject.fragments.NavBarFragment;
import com.example.capstoneproject.globals.RecipeApplication;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends AppCompatActivity {

    private SwipeDeck cardStack;
    private RequestManager manager;
    private RecipeNutritionResponse macros;
    private RandomRecipe firstRecipe;
    private ArrayList<RandomRecipe> randomRecipes;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        macros = new RecipeNutritionResponse();
        randomRecipes = new ArrayList<>();
        manager = new RequestManager(this);
        cardStack = findViewById(R.id.swipe_deck);
        if (getIntent().hasExtra("swipeRecipe") && getIntent().hasExtra("swipeMacros")) {
            Log.i("Intent Bundle", "CARDS MOVED DOWN");
            randomRecipes = getIntent().getParcelableArrayListExtra("swipeRecipe");
            firstRecipe = randomRecipes.get(0);
            macros = getIntent().getParcelableExtra("swipeMacros");
            final DeckAdapter adapter = new DeckAdapter(randomRecipes, macros, SwipeActivity.this);
            cardStack.setAdapter(adapter);
        }
        else {
            manager.GetRandomRecipes(randomListener, new ArrayList<>());
        }

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.swipe);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.swipe ) {
                    return true;
                }

                if (item.getItemId() == R.id.recipe) {
                    Intent intent = new Intent(SwipeActivity.this, SavedActivity.class);
                    intent.putParcelableArrayListExtra("swipeRecipe", randomRecipes);
                    intent.putExtra("swipeMacros", macros);
                    intent.putExtra("searchQuery", getIntent().getStringExtra("searchQuery"));
                    intent.putExtra("searchResponses", getIntent().getParcelableArrayListExtra("searchResponses"));
                    startActivity(intent);
                    SwipeActivity.this.overridePendingTransition(0, 0);
                    return true;
                }

                if (item.getItemId() == R.id.grocery) {
                    Intent intent = new Intent(SwipeActivity.this, GroceryActivity.class);
                    intent.putParcelableArrayListExtra("swipeRecipe", randomRecipes);
                    intent.putExtra("swipeMacros", macros);
                    intent.putExtra("searchQuery", getIntent().getStringExtra("searchQuery"));
                    intent.putExtra("searchResponses", getIntent().getParcelableArrayListExtra("searchResponses"));
                    startActivity(intent);
                    SwipeActivity.this.overridePendingTransition(0, 0);
                    return true;
                }

                if (item.getItemId() == R.id.search) {
                    Intent intent = new Intent(SwipeActivity.this, SearchActivity.class);
                    intent.putParcelableArrayListExtra("swipeRecipe", randomRecipes);
                    intent.putExtra("swipeMacros", macros);
                    intent.putExtra("searchQuery", getIntent().getStringExtra("searchQuery"));
                    intent.putExtra("searchResponses", getIntent().getParcelableArrayListExtra("searchResponses"));
                    startActivity(intent);
                    SwipeActivity.this.overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                manager.GetRandomRecipes(randomListener, new ArrayList<>());
            }

            @Override
            public void cardSwipedRight(int position) {
                manager.GetRandomRecipes(randomListener, new ArrayList<>());
                RandomRecipe recipeResponse = firstRecipe;
                Recipe recipe = new Recipe(recipeResponse, macros);
                final RecipeDao recipeDao = ((RecipeApplication) getApplicationContext())
                        .getRecipeDB().recipeDao();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        recipeDao.insertRecipe(recipe);
                    }
                });
            }

            @Override
            public void cardsDepleted() {
            }

            @Override
            public void cardActionDown() {
                Log.i("TAG", "CARDS MOVED DOWN");
            }

            @Override
            public void cardActionUp() {
            }
        });
    }

    private final RandomAPIResponseListener randomListener = new RandomAPIResponseListener() {
        @Override
        public void didFetch(ArrayList<RandomRecipe> responses, String message) {
            randomRecipes = responses;
            firstRecipe = responses.get(0);
            manager.GetNutritionByID(nutritionListener, responses.get(0).getId(), 0);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(SwipeActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final NutritionAPIResponseListener nutritionListener = new NutritionAPIResponseListener() {
        @Override
        public void didFetch(RecipeNutritionResponse response, String message, int index) {
            macros = response;
            final DeckAdapter adapter = new DeckAdapter(randomRecipes, response, SwipeActivity.this);
            cardStack.setAdapter(adapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(SwipeActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}
