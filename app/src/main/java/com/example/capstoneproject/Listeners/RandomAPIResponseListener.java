package com.example.capstoneproject.Listeners;

import com.example.capstoneproject.Models.RandomRecipe;

import java.util.ArrayList;
import java.util.List;

public interface RandomAPIResponseListener {
    void didFetch(ArrayList<RandomRecipe> responses, String message);
    void didError(String message);
}
