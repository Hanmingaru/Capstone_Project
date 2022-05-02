package com.example.capstoneproject;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SwipeActivity extends AppCompatActivity {

    private SwipeDeck cardStack;
    private RequestManager manager;
    private RecipeNutritionResponse macros;
    private RandomRecipe firstRecipe;
    private ArrayList<RandomRecipe> randomRecipes;
    private BottomNavigationView bottomNavigationView;
    private String[] tagNames;
    private boolean[] checkedItems;
    private ArrayList<String> tags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        macros = null;
        randomRecipes = new ArrayList<>();
        manager = new RequestManager(this);
        cardStack = findViewById(R.id.swipe_deck);
        tags = new ArrayList<>();
        if (getIntent().hasExtra("swipeRecipe") && getIntent().hasExtra("swipeMacros") && getIntent().hasExtra("checkedItems")) {
            Log.i("Intent Bundle", "CARDS MOVED DOWN");
            randomRecipes = getIntent().getParcelableArrayListExtra("swipeRecipe");
            firstRecipe = randomRecipes.get(0);
            macros = getIntent().getParcelableExtra("swipeMacros");
            checkedItems = getIntent().getBooleanArrayExtra("checkedItems");
            final DeckAdapter adapter = new DeckAdapter(randomRecipes, macros, SwipeActivity.this);
            cardStack.setAdapter(adapter);
        }
        else {
            manager.GetRandomRecipes(randomListener, tags);
            checkedItems = new boolean[]{false, false, false, false, false, false, false, false};
        }
        MaterialToolbar appBar = findViewById(R.id.topAppBar);
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
                    intent.putExtra("checkedItems", checkedItems);
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
                    intent.putExtra("checkedItems", checkedItems);
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
                    intent.putExtra("checkedItems", checkedItems);
                    intent.putExtra("searchQuery", getIntent().getStringExtra("searchQuery"));
                    intent.putExtra("searchResponses", getIntent().getParcelableArrayListExtra("searchResponses"));
                    startActivity(intent);
                    SwipeActivity.this.overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
        tagNames = new String[]{"Breakfast", "Lunch", "Dinner", "Dessert", "Gluten Free", "Dairy Free", "Vegetarian", "Ketogenic"};
        appBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Set up the alert builder
                boolean[] originalChecked = Arrays.copyOf(checkedItems, checkedItems.length);
                AlertDialog.Builder builder = new AlertDialog.Builder(SwipeActivity.this);
                builder.setTitle("Filters");
                // Add a checkbox list
                builder.setMultiChoiceItems(tagNames, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which] = isChecked;
                    }
                });

                // Add OK and Cancel buttons
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tags.clear();
                        for (int i = 0; i < tagNames.length; i++){
                            if( checkedItems[i]) {
                                tags.add(tagNames[i].toLowerCase());
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItems = originalChecked;
                    }
                });

                // Create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                manager.GetRandomRecipes(randomListener, tags);
            }

            @Override
            public void cardSwipedRight(int position) {
                manager.GetRandomRecipes(randomListener, tags);
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
            if (responses.size() > 0) {
                randomRecipes = responses;
                firstRecipe = responses.get(0);
                manager.GetNutritionByID(nutritionListener, responses.get(0).getId(), 0);
            } else {
                if(randomRecipes.size() > 0 && macros != null) {
                    final DeckAdapter adapter = new DeckAdapter(randomRecipes, macros, SwipeActivity.this);
                    cardStack.setAdapter(adapter);
                }
                Toast.makeText(SwipeActivity.this, "No recipe found", Toast.LENGTH_SHORT).show();
            }
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
