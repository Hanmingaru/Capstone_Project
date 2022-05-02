package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.example.capstoneproject.adapters.GroceryAdapter;
import com.example.capstoneproject.daos.GroceryDao;
import com.example.capstoneproject.entities.Grocery;
import com.example.capstoneproject.fragments.NavBarFragment;
import com.example.capstoneproject.globals.RecipeApplication;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class GroceryActivity extends AppCompatActivity {
    private List<Grocery> groceries;
    private GroceryAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.grocery);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.swipe ) {
                    Intent intent = new Intent(GroceryActivity.this, SwipeActivity.class);
                    intent.putParcelableArrayListExtra("swipeRecipe", getIntent().getParcelableArrayListExtra("swipeRecipe"));
                    intent.putExtra("swipeMacros", (RecipeNutritionResponse) getIntent().getParcelableExtra("swipeMacros"));
                    intent.putExtra("checkedItems", getIntent().getBooleanArrayExtra("checkedItems"));
                    intent.putExtra("searchQuery", getIntent().getStringExtra("searchQuery"));
                    intent.putExtra("searchResponses", getIntent().getParcelableArrayListExtra("searchResponses"));
                    startActivity(intent);
                    GroceryActivity.this.overridePendingTransition(0, 0);
                    return true;
                }

                if (item.getItemId() == R.id.recipe) {
                    Intent intent = new Intent(GroceryActivity.this, SavedActivity.class);
                    intent.putParcelableArrayListExtra("swipeRecipe", getIntent().getParcelableArrayListExtra("swipeRecipe"));
                    intent.putExtra("swipeMacros", (RecipeNutritionResponse) getIntent().getParcelableExtra("swipeMacros"));
                    intent.putExtra("checkedItems", getIntent().getBooleanArrayExtra("checkedItems"));
                    intent.putExtra("searchQuery", getIntent().getStringExtra("searchQuery"));
                    intent.putExtra("searchResponses", getIntent().getParcelableArrayListExtra("searchResponses"));
                    startActivity(intent);
                    GroceryActivity.this.overridePendingTransition(0, 0);
                    return true;
                }

                if (item.getItemId() == R.id.grocery) {
                    return true;
                }

                if (item.getItemId() == R.id.search) {
                    Intent intent = new Intent(GroceryActivity.this, SearchActivity.class);
                    intent.putParcelableArrayListExtra("swipeRecipe", getIntent().getParcelableArrayListExtra("swipeRecipe"));
                    intent.putExtra("swipeMacros", (RecipeNutritionResponse) getIntent().getParcelableExtra("swipeMacros"));
                    intent.putExtra("checkedItems", getIntent().getBooleanArrayExtra("checkedItems"));
                    intent.putExtra("searchQuery", getIntent().getStringExtra("searchQuery"));
                    intent.putExtra("searchResponses", getIntent().getParcelableArrayListExtra("searchResponses"));
                    startActivity(intent);
                    GroceryActivity.this.overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
        final GroceryDao groceryDao = ((RecipeApplication)  getApplicationContext())
                .getRecipeDB().groceryDao();
        groceries = groceryDao.getAll();
        if (groceries.size() > 0)
            Log.i("FROM GROCERIES", groceries.size() + "");
        recyclerView = findViewById(R.id.grocery_recycler_view);

        adapter = new GroceryAdapter(GroceryActivity.this, groceries, groceryDao);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MaterialToolbar appBar = findViewById(R.id.topAppBar);
        appBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.grocery_clear) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
                    builder.setTitle("Delete Recipe");
                    builder.setMessage("Are you sure you want to clear the grocery list?");
                    builder.setPositiveButton("Confirm", (DialogInterface dialogInterface, int i) -> {
                        groceryDao.deleteAll();
                        groceries.clear();
                        adapter.notifyDataSetChanged();
                    });
                    builder.setNegativeButton(android.R.string.cancel, (DialogInterface dialogInterface, int i) ->
                            adapter.notifyDataSetChanged());
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
                return false;
            }
        });

    }
}