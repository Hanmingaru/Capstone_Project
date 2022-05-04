/*
 * Created by Elliott Rheault on 2022.5.1
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.adapters;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;

import com.example.capstoneproject.daos.GroceryDao;
import com.example.capstoneproject.entities.Grocery;
import com.example.capstoneproject.globals.RecipeApplication;


import java.util.List;


public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.MyViewHolder> {
    private Context context;
    private List<Grocery> groceries;
    private GroceryDao groceryDao;

    public GroceryAdapter(Context context, List<Grocery> groceries, GroceryDao groceryDao) {
        this.context = context;
        this.groceries = groceries;
        this.groceryDao = groceryDao;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.grocery_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryAdapter.MyViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        holder.groceryName.setText(groceries.get(index).getName());
        holder.deleteGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(index);
            }
        });
    }

    public Context getContext() {
        return context;
    }

    private void delete(int index) {
        groceryDao.deleteByName(groceries.get(index).getName());
        groceries.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index,groceries.size());
    }
    @Override
    public int getItemCount() {
        return groceries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView groceryName;
        private Button deleteGrocery;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            groceryName = itemView.findViewById(R.id.grocery_name);
            deleteGrocery = itemView.findViewById(R.id.delete_grocery);
        }
    }
}
