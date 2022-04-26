/*
 * Created by Elliott Rheault on 2022.4.20
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.Models.RecipeSearch;
import com.example.capstoneproject.entities.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    Context context;
    List<RecipeSearch> searchedRecipes;
    public SearchAdapter(Context context, List<RecipeSearch> searchedRecipes) {
        this.context = context;
        this.searchedRecipes = searchedRecipes;
    }
    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recipe_list_item, parent, false);
        return new SearchAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        holder.recipeName.setText(searchedRecipes.get(position).getTitle());
        Picasso.get().load(searchedRecipes.get(position).getImage()).into(holder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return searchedRecipes.size();
    }

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
