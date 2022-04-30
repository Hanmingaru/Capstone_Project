package com.example.capstoneproject;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.capstoneproject.Listeners.NutritionAPIResponseListener;
import com.example.capstoneproject.Listeners.RandomAPIResponseListener;
import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.example.capstoneproject.daos.GroceryDao;
import com.example.capstoneproject.daos.RecipeDao;
import com.example.capstoneproject.database.RecipeDB;
import com.example.capstoneproject.entities.Recipe;
import com.example.capstoneproject.globals.RecipeApplication;

import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends AppCompatActivity {

    private SwipeDeck cardStack;
    private RequestManager manager;
    private RecipeNutritionResponse macros;
    private RandomRecipe firstRecipe;
    private List<RandomRecipe> randomRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        macros = new RecipeNutritionResponse();
        randomRecipes = new ArrayList<RandomRecipe>();
        manager = new RequestManager(this);
        manager.GetRandomRecipes(randomListener, new ArrayList<>());

        cardStack = findViewById(R.id.swipe_deck);
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                manager.GetRandomRecipes(randomListener, new ArrayList<>());
            }

            @Override
            public void cardSwipedRight(int position) {
                manager.GetRandomRecipes(randomListener, new ArrayList<>());

                // FIXME: macros does not work

                // Get randomRecipe object from api
                RandomRecipe recipeResponse = firstRecipe;

                // Create recipe object to store in database
                Recipe recipe = new Recipe(recipeResponse, macros);

                // Get Recipe Database Access Object
                final RecipeDao recipeDao = ((RecipeApplication) getApplicationContext())
                        .getRecipeDB().recipeDao();

                // Run the task
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
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.navbar_fragment_id, NavBarFragment.class, null)
                    .commit();
        }
    }

    private final RandomAPIResponseListener randomListener = new RandomAPIResponseListener() {
        @Override
        public void didFetch(List<RandomRecipe> responses, String message) {
            randomRecipes = responses;
            firstRecipe = responses.get(0);
            manager.GetNutritionByID(nutritionListener, responses.get(0).getId());
        }

        @Override
        public void didError(String message) {
            Toast.makeText(SwipeActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final NutritionAPIResponseListener nutritionListener = new NutritionAPIResponseListener() {
        @Override
        public void didFetch(RecipeNutritionResponse response, String message) {
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
