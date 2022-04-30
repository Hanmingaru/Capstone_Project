/*
 * Created by Elliott Rheault on 2022.4.27
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewPageAdapter extends FragmentStateAdapter {

    ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    ArrayList<String> titles = new ArrayList<String>();

    public ViewPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }
    public void setFragments(ArrayList<Fragment> fragments, ArrayList<String> titles) {
        this.fragments = fragments;
        this.titles = titles;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    public String getTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
