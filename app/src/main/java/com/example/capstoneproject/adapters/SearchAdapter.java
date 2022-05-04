/*
 * Created by Elliott Rheault on 2022.5.1
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.Listeners.NutritionAPIResponseListener;
import com.example.capstoneproject.Listeners.RecipeByIdListener;
import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.example.capstoneproject.Models.RecipeSearch;
import com.example.capstoneproject.R;
import com.example.capstoneproject.RecipeInfoActivity;
import com.example.capstoneproject.RequestManager;
import com.example.capstoneproject.daos.RecipeDao;
import com.example.capstoneproject.entities.Recipe;
import com.example.capstoneproject.globals.RecipeApplication;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private Context context;
    private List<RecipeSearch> searchedRecipes;
    private RequestManager manager;
    private int recipeId;
    private RandomRecipe[] randomRecipes;
    private RecipeNutritionResponse[] nutritionResponses;

    private TextView recipeName;
    private Button addToSaved;
    public SearchAdapter(Context context, List<RecipeSearch> searchedRecipes, RequestManager manager) {
        this.context = context;
        this.searchedRecipes = searchedRecipes;
        this.manager = manager;
    }
    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.search_list_item, parent, false);
        randomRecipes = new RandomRecipe[searchedRecipes.size()];
        nutritionResponses = new RecipeNutritionResponse[searchedRecipes.size()];
        return new SearchAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        int index = holder.getAdapterPosition();
        recipeId = searchedRecipes.get(holder.getAdapterPosition()).getId();
        manager.GetRecipeByID(recipeListener, recipeId, index);
        manager.GetNutritionByID(nutritionListener, recipeId, index);

        recipeName.setText(searchedRecipes.get(position).getTitle());
        Picasso.get().load(searchedRecipes.get(position).getImage()).into(holder.recipeImage);

        recipeName.setClickable(false);
        addToSaved.setClickable(false);

        recipeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecipeInfoActivity.class);
                intent.putExtra("recipeID", searchedRecipes.get(index).getId());
                intent.putExtra("recipe", randomRecipes[index]);
                intent.putExtra("nutrition", nutritionResponses[index]);
                context.startActivity(intent);
            }
        });
        addToSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe recipe = new Recipe(randomRecipes[index], nutritionResponses[index]);
                Animator scale = ObjectAnimator.ofPropertyValuesHolder(view,
                        PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 0.75f, 1),
                        PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 0.75f, 1)
                );
                scale.setDuration(400);
                scale.start();
                // Get Recipe Database Access Object
                final RecipeDao recipeDao = ((RecipeApplication) context.getApplicationContext())
                        .getRecipeDB().recipeDao();

                if (recipeDao.findByRecipeID(recipeId) != null ) {
                    Toast.makeText(context, "Recipe already added", Toast.LENGTH_SHORT).show();
                } else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            recipeDao.insertRecipe(recipe);
                        }
                    });
                    Toast.makeText(context, "Recipe added to saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchedRecipes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView recipeImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeNameList2);
            recipeImage = itemView.findViewById(R.id.recipeImageList2);
            addToSaved = itemView.findViewById(R.id.search_list_button);
        }
    }
    private RecipeByIdListener recipeListener = new RecipeByIdListener() {
        @Override
        public void didFetch(RandomRecipe response, String message, int index) {
            randomRecipes[index] = response;
            if (nutritionResponses[index] != null) {
                recipeName.setClickable(true);
                addToSaved.setClickable(true);
            }
        }

        @Override
        public void didError(String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    };

    private NutritionAPIResponseListener nutritionListener = new NutritionAPIResponseListener() {
        @Override
        public void didFetch(RecipeNutritionResponse response, String message, int index) {
            nutritionResponses[index] = response;
            if (randomRecipes[index] != null) {
                recipeName.setClickable(true);
                addToSaved.setClickable(true);
            }
        }

        @Override
        public void didError(String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    };
}
