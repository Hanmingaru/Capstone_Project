/*
 * Created by Elliott Rheault on 2022.5.1
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.adapters;

import android.content.Context;
import android.content.Intent;
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

import com.example.capstoneproject.R;
import com.example.capstoneproject.RecipeInfoActivity;
import com.example.capstoneproject.daos.RecipeDao;
import com.example.capstoneproject.entities.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    Context context;
    List<Recipe> savedRecipes;
    RecipeDao recipeDao;
    boolean favoritesSelected;

    public RecyclerAdapter(Context context, List<Recipe> savedRecipes, RecipeDao recipeDao) {
        this.context = context;
        this.savedRecipes = savedRecipes;
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

        // Set visibility of favorite icon based off recipes favorite flag
        holder.favoriteIcon.setVisibility(savedRecipes.get(position).getFavorite() ? View.VISIBLE : View.INVISIBLE);
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
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;
        ImageView recipeImage;
        ImageView favoriteIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeNameList);
            recipeImage = itemView.findViewById(R.id.recipeImageList);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
        }
    }
}
