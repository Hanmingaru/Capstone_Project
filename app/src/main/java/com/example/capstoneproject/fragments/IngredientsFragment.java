/*
 * Created by Elliott Rheault on 2022.5.1
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstoneproject.R;

public class IngredientsFragment extends Fragment {

    private static final String INGREDIENTS = "ingredients";

    private TextView ingredient_view;

    private String ingredients;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    public static IngredientsFragment newInstance(String in) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putString(INGREDIENTS, in);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ingredients = getArguments().getString(INGREDIENTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ingredient_view = myView.findViewById(R.id.ingredients_text);

        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(ingredients != null) {
            ingredient_view.setText(ingredients);
        }
    }

}