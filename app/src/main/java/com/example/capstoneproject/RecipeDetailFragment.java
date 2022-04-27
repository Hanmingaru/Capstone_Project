/*
 * Created by Elliott Rheault on 2022.4.27
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecipeDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String LINK = "link";
    public static final String PRICE = "price";
    public static final String CALORIES = "calories";
    public static final String PROTEINS = "proteins";
    public static final String FATS = "fats";
    public static final String CARBS = "carbs";
    public static final String TIME = "time";

    private TextView link_view;
    private TextView price_view;
    private TextView calories_view;
    private TextView proteins_view;
    private TextView fats_view;
    private TextView carbs_view;
    private TextView time_view;

    // TODO: Rename and change types of parameters
    private String link_t;
    private String price_t;
    private String calories_t;
    private String proteins_t;
    private String fats_t;
    private String carbs_t;
    private String time_t;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

   public static RecipeDetailFragment newInstance(String link, String price, String calories, String proteins, String fats, String carbs, String time) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putString(LINK, link);
        args.putString(PRICE, price);
        args.putString(CALORIES, calories);
        args.putString(PROTEINS, proteins);
        args.putString(FATS, fats);
        args.putString(CARBS, carbs);
        args.putString(TIME, time);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
                link_t = getArguments().getString(LINK);
                price_t = getArguments().getString(PRICE);
                calories_t = getArguments().getString(CALORIES);
                proteins_t = getArguments().getString(PROTEINS);
                fats_t = getArguments().getString(FATS);
                carbs_t = getArguments().getString(CARBS);
                time_t = getArguments().getString(TIME);
        }
        Log.i("RECIPEDETAILFRAG", link_t + " " + price_t + " " + calories_t);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        price_view = myView.findViewById(R.id.price_text);
        link_view = myView.findViewById(R.id.link_text);
        calories_view = myView.findViewById(R.id.calories_text);
        proteins_view = myView.findViewById(R.id.protein_text);
        fats_view = myView.findViewById(R.id.fats_text);
        carbs_view = myView.findViewById(R.id.carbs_text);
        time_view = myView.findViewById(R.id.time_requirement_text);

        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(price_t != null) {
            link_view.setText(Html.fromHtml(link_t));
            link_view.setClickable(true);
            link_view.setMovementMethod(LinkMovementMethod.getInstance());
            price_view.append(price_t);
            calories_view.append(calories_t);
            proteins_view.append(proteins_t);
            fats_view.append(fats_t);
            carbs_view.append(carbs_t);
            time_view.append(time_t);
        }
    }
}