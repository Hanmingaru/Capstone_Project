/*
 * Created by Elliott Rheault on 2022.4.5
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.daos.RecipeDao;
import com.example.capstoneproject.entities.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<Recipe> savedRecipes;
    List<Recipe> savedRecipesFull;
    RecipeDao recipeDao;
    boolean favoritesSelected;

    public RecyclerAdapter(Context context, List<Recipe> savedRecipes, RecipeDao recipeDao) {
        this.context = context;
        this.savedRecipes = savedRecipes;
        savedRecipesFull = new ArrayList<>(savedRecipes);
        this.recipeDao = recipeDao;
        this.favoritesSelected = false;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.saved_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recipeName.setText(savedRecipes.get(position).getName());
        Picasso.get().load(savedRecipes.get(position).getImageUrl()).into(holder.recipeImage);
        holder.recipeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecipeInfoActivity.class);
                intent.putExtra("recipeID", savedRecipes.get(holder.getAdapterPosition()).getRecipeID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedRecipes.size();
    }

    public Context getContext() {
        return context;
    }

    public void deleteRecipe(int position) {
        recipeDao.deleteRecipeID(savedRecipes.get(position).getRecipeID());
        savedRecipesFull.remove(position);
        savedRecipes.remove(position);
        notifyItemRemoved(position);
    }

    public void updateFavorite(int position) {
        Recipe recipe = savedRecipes.get(position);
        String message;
        if (recipe.getFavorite()) { // If true, set to false
            recipe.setFavorite(false);
            message = "Removed from favorites";
        } else {    // If false, set to true
            recipe.setFavorite(true);
            message = "Added to favorites";
        }
        recipeDao.updateRecipeID(recipe.getRecipeID(), recipe.getFavorite());
        // Show popup message
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
        // If in favorites tab, remove from favorites list
        if (favoritesSelected) {
            savedRecipes.remove(position);
        }
    }

    public boolean getFavoritesSelected() {
        return favoritesSelected;
    }

    public void setFavoritesSelected(boolean favoritesSelected) {
        this.favoritesSelected = favoritesSelected;
    }

    @Override
    public Filter getFilter() {
        return recipeFilter;
    }

    // Filter object that filters recipes based on filter type and constraint
    private Filter recipeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Recipe> filteredList = new ArrayList<>();

            // Filter based on filter type (favorites/search)
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(savedRecipesFull);
            } else if (charSequence.toString() == "all") { // All button pressed
                filteredList.addAll(savedRecipesFull);
            } else if (charSequence.toString() == "favorites") { // Favorites button pressed
                // Filter recipes that have favorite flag
                for (Recipe recipe: savedRecipes) {
                    if (recipe.getFavorite()) {
                        filteredList.add(recipe);
                    }
                }
            } else if (charSequence.toString().contains("*favorites")) { // Search in favorites
                // Remove "*favorites" flag from string
                String filterPattern = charSequence.toString().replace("*favorites", "")
                        .toLowerCase().trim();
                System.out.println(filterPattern);
                for (Recipe recipe: savedRecipes) {
                    if (recipe.getName().toLowerCase().contains(filterPattern)
                        && recipe.getFavorite()) {
                        filteredList.add(recipe);
                    }
                }

            } else { // Search in all
                String filterPattern = charSequence.toString().toLowerCase().trim();
                // Filter recipes that have name containing search constraint
                for (Recipe recipe: savedRecipes) {
                    if (recipe.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(recipe);
                    }
                }
            }
            // Create and return filter results
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            savedRecipes.clear();
            savedRecipes.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;
        ImageView recipeImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeNameList);
            recipeImage = itemView.findViewById(R.id.recipeImageList);
        }
    }
}
