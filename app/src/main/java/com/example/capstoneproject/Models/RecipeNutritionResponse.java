/*
 * Created by Elliott Rheault on 2022.4.4
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.Models;

import java.util.ArrayList;

public class RecipeNutritionResponse {
    public String calories;
    public String carbs;
    public String fat;
    public String protein;

    public String getCalories() {
        return calories;
    }

    public String getCarbs() {
        return carbs;
    }

    public String getFat() {
        return fat;
    }

    public String getProtein() {
        return protein;
    }

    public ArrayList<String> getMacros(){
        ArrayList<String> macroList = new ArrayList<String>();
        macroList.add(calories);
        macroList.add(carbs);
        macroList.add(fat);
        macroList.add(protein);
        return macroList;
    }

}
