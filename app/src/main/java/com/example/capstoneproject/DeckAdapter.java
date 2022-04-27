package com.example.capstoneproject;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DeckAdapter extends BaseAdapter {

    // on below line we have created variables
    // for our array list and context.
    private List<RandomRecipe> recipeData;
    private RecipeNutritionResponse macros;
    private Context context;

    // on below line we have created constructor for our variables.
    public DeckAdapter(List<RandomRecipe> recipeData, RecipeNutritionResponse macros, Context context) {
        this.recipeData = recipeData;
        this.macros = macros;
        this.context = context;
    }

    @Override
    public int getCount() {
        // in get count method we are returning the size of our array list.
        return recipeData.size();
    }

    @Override
    public Object getItem(int position) {
        // in get item method we are returning the item from our array list.
        return recipeData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // in get item id we are returning the position.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        }
        ((TextView) v.findViewById(R.id.recipeName)).setText(recipeData.get(position).getTitle());
        ((TextView) v.findViewById(R.id.recipeCalories)).setText("Calories: " + macros.getCalories().substring(0, macros.getCalories().length()-1));
        ((TextView) v.findViewById(R.id.recipeTime)).setText("Time (min): " + recipeData.get(position).getReadyInMinutes());
        ((TextView) v.findViewById(R.id.recipeCost)).setText(String.format("Price per serving: $%.2f",recipeData.get(position).getPricePerServing()/100.0));
        Picasso.get().load(recipeData.get(position).getImage()).into((ImageView) v.findViewById(R.id.recipeImage));
        v.findViewById(R.id.recipeName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecipeInfoActivity.class);
                intent.putExtra("recipeID", recipeData.get(0).getId());
                context.startActivity(intent);
            }
        });
        return v;
    }
}
