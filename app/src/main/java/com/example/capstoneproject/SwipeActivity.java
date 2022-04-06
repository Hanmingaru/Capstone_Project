package com.example.capstoneproject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.capstoneproject.Listeners.NutritionAPIResponseListener;
import com.example.capstoneproject.Listeners.RandomAPIResponseListener;
import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
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
                Toast.makeText(SwipeActivity.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                manager.GetRandomRecipes(randomListener, new ArrayList<>());
                Toast.makeText(SwipeActivity.this, "Card Swiped Right", Toast.LENGTH_SHORT).show();

                // Create recipe object to store in database
                RandomRecipe recipeResponse = randomRecipes.get(0);
                Recipe recipe = new Recipe(recipeResponse.getTitle(), recipeResponse.getImage());

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
                Log.i("TAG", "CARDS MOVED UP");
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
//            Log.d("Response", "" + randomRecipes.get(0).getId());
//            Log.d("Response", "" + response.getCalories());
//            Log.d("Response", "" + response.getCarbs());
//            Log.d("Response", "" + response.getFat());
//            Log.d("Response", "" + response.getProtein());
            final DeckAdapter adapter = new DeckAdapter(randomRecipes, response, SwipeActivity.this);
            cardStack.setAdapter(adapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(SwipeActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}
