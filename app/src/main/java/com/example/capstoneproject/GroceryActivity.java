package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.capstoneproject.adapters.GroceryAdapter;
import com.example.capstoneproject.daos.GroceryDao;
import com.example.capstoneproject.entities.Grocery;
import com.example.capstoneproject.fragments.NavBarFragment;
import com.example.capstoneproject.globals.RecipeApplication;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class GroceryActivity extends AppCompatActivity {
    private List<Grocery> groceries;
    private GroceryAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.navbar_fragment_id, NavBarFragment.class, null)
                    .commit();
        }
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
                    groceryDao.deleteAll();
                    groceries.clear();
                    adapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

    }
}