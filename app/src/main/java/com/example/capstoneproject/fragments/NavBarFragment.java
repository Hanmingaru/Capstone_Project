/*
 * Created by Elliott Rheault on 2022.5.1
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstoneproject.GroceryActivity;
import com.example.capstoneproject.R;
import com.example.capstoneproject.SavedActivity;
import com.example.capstoneproject.SearchActivity;
import com.example.capstoneproject.SwipeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavBarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private BottomNavigationView bottomNavigationView;
    public NavBarFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static NavBarFragment newInstance() {
        NavBarFragment fragment = new NavBarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_nav_bar, container, false);
        bottomNavigationView = myView.findViewById(R.id.bottom_nav_bar);
        if(getActivity().getClass().getSimpleName().equals("SwipeActivity"))
            bottomNavigationView.setSelectedItemId(R.id.swipe);
        if(getActivity().getClass().getSimpleName().equals("SavedActivity"))
            bottomNavigationView.setSelectedItemId(R.id.recipe);
        if(getActivity().getClass().getSimpleName().equals("GroceryActivity"))
            bottomNavigationView.setSelectedItemId(R.id.grocery);
        if(getActivity().getClass().getSimpleName().equals("SearchActivity"))
            bottomNavigationView.setSelectedItemId(R.id.search);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.swipe ) {
                    if(bottomNavigationView.getSelectedItemId() != R.id.swipe) {
                        startActivity(new Intent(getActivity(), SwipeActivity.class));
                        getActivity().overridePendingTransition(0, 0);
                    }
                    return true;
                }

                if (item.getItemId() == R.id.recipe) {
                    if(bottomNavigationView.getSelectedItemId() != R.id.recipe) {
                        startActivity(new Intent(getActivity(), SavedActivity.class));
                        getActivity().overridePendingTransition(0, 0);
                    }
                    return true;
                }

                if (item.getItemId() == R.id.grocery) {
                    if(bottomNavigationView.getSelectedItemId() != R.id.grocery) {
                        startActivity(new Intent(getActivity(), GroceryActivity.class));
                        getActivity().overridePendingTransition(0, 0);
                    }
                    return true;
                }

                if (item.getItemId() == R.id.search) {
                    if(bottomNavigationView.getSelectedItemId() != R.id.search) {
                        startActivity(new Intent(getActivity(), SearchActivity.class));
                        getActivity().overridePendingTransition(0, 0);
                    }
                    return true;
                }
                return false;
            }
        });
        return myView;
        }
}