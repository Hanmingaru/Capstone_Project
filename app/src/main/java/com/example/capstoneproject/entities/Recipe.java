/*
 * Created by Elliott Rheault on 2022.3.31
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

// Documentation: https://developer.android.com/training/data-storage/room/defining-data

package com.example.capstoneproject.entities;

import android.os.AsyncTask;

import androidx.annotation.Size;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.capstoneproject.Models.ExtendedIngredient;
import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.example.capstoneproject.daos.GroceryDao;
import com.example.capstoneproject.globals.Methods;
import com.example.capstoneproject.globals.RecipeApplication;

// Use `@Fts3` only if your app has strict disk space requirements or if you
// require compatibility with an older SQLite version.
@Fts4
@Entity(tableName = "recipe")
public class Recipe {

     /*
    ========================================================
    Instance variables representing the attributes (columns)
    of the Recipe table in the RecipeDB database.
    CREATE TABLE Recipe
    (
        rowid INT UNSIGNED NOT NULL AUTO_INCREMENT,
        name VARCHAR(512) NOT NULL,
        favorite INT NOT NULL DEFAULT_VALUE(0)
    );
    ========================================================
     */

    // Specifying a primary key for an FTS-table-backed entity is optional, but
    // if you include one, it must use this type and column name.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private Integer id;

    // Name of the recipe
    @ColumnInfo(name = "name")
    @Size(min = 1, max = 512) // Not sure if Room/SQLite needs this
    @NotNull
    private String name;

    // Flag to mark recipe as a favorite
    // Stored as an Integer in the database
    @ColumnInfo(name = "favorite")
    @NotNull
    private Boolean favorite;

    // Flag to mark recipe as a favorite
    // Stored as an Integer in the database
    @ColumnInfo(name = "imageUrl")
    @NotNull
    private String imageUrl;

    // Number of calories in the recipe
    @ColumnInfo(name = "calories")
    @NotNull
    private String calories;

    // Amount of protein in the recipe
    @ColumnInfo(name = "protein")
    @NotNull
    private String protein;

    // Amount of fat in the recipe
    @ColumnInfo(name = "fat")
    @NotNull
    private String fat;

    // Number of carbs in the recipe
    @ColumnInfo(name = "carbs")
    @NotNull
    private String carbs;

    // Link to the recipes website
    @ColumnInfo(name = "websiteLink")
    @NotNull
    private String websiteLink;

    // Number of minutes it takes to prepare the recipe
    @ColumnInfo(name = "preparationTime")
    @NotNull
    private Integer preparationTime;

    // Price of a single serving of the recipe
    @ColumnInfo(name = "pricePerServing")
    @NotNull
    private Double pricePerServing;

    // Description of the recipe
    @ColumnInfo(name = "description")
    @NotNull
    private String description;

    // Spoonacular ID of the recipe
    @ColumnInfo(name = "recipeID")
    @NotNull
    private Integer recipeID;

    /* *************************************************************************************
     * Use Serialize/Deserialize functions from globals.Methods to get the List of Strings *
     ***************************************************************************************/

    // List of strings that contain the diets this recipe abides by
    @ColumnInfo(name = "diets")
    @NotNull
    private String diets;

    // List of all ingredients as Strings
    @ColumnInfo(name = "ingredients")
    @NotNull
    private String ingredients;

    // List of Strings corresponding to ingredients that hold
    // the aisle the ingredient is stored in
    @ColumnInfo(name = "aisles")
    @NotNull
    private String aisle;

    // List of all instructions as Strings
    @ColumnInfo(name = "instructions")
    @NotNull
    private String instructions;

    /* more attributes ... */

    /*
    ==============================================================
    Class constructors for instantiating a Recipe entity object to
    represent a row in the Recipe table in the RecipeDB database.
    ==============================================================
    */
    public Recipe() {
    }

    @Ignore
    public Recipe(Integer id) {
        this.id = id;
    }

    /**
     * Create a Database Recipe object from RandomRecipe and
     * RecipeNutritionResponse Objects.
     *
     * @param randomRecipe A RandomRecipe object created from an API call
     * @param recipeNutrition A RecipeNutritionResponse created from an API call
     */
    @Ignore
    public Recipe(RandomRecipe randomRecipe, RecipeNutritionResponse recipeNutrition) {
        this(randomRecipe.getTitle(),
                randomRecipe.getImage(),
                recipeNutrition.getCalories(),
                recipeNutrition.getProtein(),
                recipeNutrition.getFat(),
                recipeNutrition.getCarbs(),
                randomRecipe.getSourceUrl(),
                randomRecipe.getReadyInMinutes(),
                randomRecipe.getPricePerServing(),
                randomRecipe.getSummary(),
                randomRecipe.getExtendedIngredients(),
                randomRecipe.getInstructions(),
                randomRecipe.getDiets(),
                randomRecipe.getId());
    }

    /**
     * Create a Recipe with all fields initialized. Used to create a recipe
     * when you know all of the fields.
     *
     * @param name                Name of the recipe
     * @param imageUrl            Url of the recipes Image
     * @param calories            Number of calories in the recipe
     * @param protein             Amount of protein in the recipe
     * @param fat                 Amount of fat in the recipe
     * @param carbs               Amount of carbs in the recipe
     * @param websiteLink         Link to the Recipe's website
     * @param preparationTime     Number of minutes it takes to prepare the recipe.
     * @param pricePerServing     Price of one serving of the recipe
     * @param description         Description of the recipe
     * @param extendedIngredients List of extendedIngredients objects that contain
     *                            all of the recipes ingredients
     * @param instructions        String of instructions delimited on new lines
     * @param diets               List of Strings of diets the recipe follows
     * @param recipeID            Spoonacular Id of the Recipe
     */
    @Ignore
    public Recipe(@NotNull String name, @NotNull String imageUrl, @NotNull String calories,
                  @NotNull String protein, @NotNull String fat, @NotNull String carbs,
                  @NotNull String websiteLink, @NotNull Integer preparationTime,
                  @NotNull Double pricePerServing, @NotNull String description,
                  List<ExtendedIngredient> extendedIngredients, String instructions,
                  List<String> diets, Integer recipeID) {
        // Fields with default values
        this.favorite = false;

        // Fields that are initialized with passed parameter
        this.name = name;
        this.imageUrl = imageUrl;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.websiteLink = websiteLink;
        this.preparationTime = preparationTime;
        this.pricePerServing = pricePerServing;
        this.recipeID = recipeID;

        // Fields that require more processing

        /* ------- Description ------- */
        this.description = description.replaceAll("\\<.*?>","");

        /* ------- Diets ------- */

        // Get diet info
        List<String> allDiets = diets;

        // Save list as serialized string. Can be deserialized on retrieval
        this.diets = Methods.serializeList(allDiets);

        /* ------- Instructions ------- */

        // Get instructions as an array of instructions
        String parsedInstructions = instructions.replaceAll("\\<.*?>","\n");
        String[] instructionsArray = parsedInstructions.split("\\R+");

        // Parse instructionsArray into a List
        List<String> instructionList = new ArrayList<>();
        for (int i = 0; i < instructionsArray.length; i++) {
            if (instructionsArray[i].length() > 3) {
                // Only add instructions with actual lengths. Avoid " ", ". ", "3)."
                instructionList.add(instructionsArray[i]);
            }
        }

        // Serialize the List into String for database
        this.instructions = Methods.serializeList(instructionList);

        /* ------- Ingredients/Aisle ------- */

        // Iterate through every ExtendedIngredient and add its
        // original String to the ingredientsList
        List<String> ingredientsList = new ArrayList<>();
        List<String> aisleList = new ArrayList<>();
        for (ExtendedIngredient exIng: extendedIngredients) {
            // Need to remove random html tags in string
            ingredientsList.add(exIng.getOriginal().replaceAll("\\<.*?>",""));
            aisleList.add(exIng.getAisle());
        }

        // Serialize these Lists into Strings for database
        this.aisle = Methods.serializeList(aisleList);
        this.ingredients = Methods.serializeList(ingredientsList);

    }

    /*
    ======================================================
    Getter and Setter methods for the attributes (columns)
    of the Recipe table in the RecipeDB database.
    ======================================================
    */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(@NotNull Boolean favorite) {
        this.favorite = favorite;
    }

    @NotNull
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NotNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotNull
    public String getCalories() {
        return calories;
    }

    public void setCalories(@NotNull String calories) {
        this.calories = calories;
    }

    @NotNull
    public String getProtein() {
        return protein;
    }

    public void setProtein(@NotNull String protein) {
        this.protein = protein;
    }

    @NotNull
    public String getFat() {
        return fat;
    }

    public void setFat(@NotNull String fat) {
        this.fat = fat;
    }

    @NotNull
    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(@NotNull String carbs) {
        this.carbs = carbs;
    }

    @NotNull
    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(@NotNull String websiteLink) {
        this.websiteLink = websiteLink;
    }

    @NotNull
    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(@NotNull Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    @NotNull
    public Double getPricePerServing() {
        return pricePerServing;
    }

    public void setPricePerServing(@NotNull Double pricePerServing) {
        this.pricePerServing = pricePerServing;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    @NotNull
    public String getDiets() {
        return diets;
    }

    public void setDiets(@NotNull String diets) {
        this.diets = diets;
    }

    @NotNull
    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(@NotNull String ingredients) {
        this.ingredients = ingredients;
    }

    @NotNull
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(@NotNull String instructions) {
        this.instructions = instructions;
    }

    @NotNull
    public String getAisle() {
        return aisle;
    }

    public void setAisle(@NotNull String aisle) {
        this.aisle = aisle;
    }

    @NotNull
    public Integer getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(@NotNull Integer recipeID) {
        this.recipeID = recipeID;
    }

    /**
     * Getter for Ingredients with deserialization
     *
     * @return List<String> of Ingredients
     */
    public List<String> getIngredientsList() {
        return Methods.deserializeList(this.ingredients);
    }

    /**
     * Getter for Instructions with deserialization
     *
     * @return List<String> of instructions
     */
    public List<String> getInstructionsList() {
        return Methods.deserializeList(this.instructions);
    }

    /**
     * Getter for Diets with deserialization
     *
     * @return List<String> of Diets
     */
    public List<String> getDietsList() {
        return Methods.deserializeList(this.diets);
    }

    /*
    ================================
    Instance Methods Used Internally
    ================================
     */

    /**
     * Use this function when a User wants to add a recipe to their groceries
     * list.
     *
     * @param groceryDao The Database Access Object for the Groceries Table
     */
    public void addIngredientsToGroceries(GroceryDao groceryDao) {

        // Get String List of ingredients and aisles
        List<String> ingredients = Methods.deserializeList(this.ingredients);
        List<String> aisle = Methods.deserializeList(this.aisle);
        int ingredientsSize = ingredients.size();

        // Create an array to store groceries
        Grocery[] groceryList = new Grocery[ingredientsSize];

        // Iterate though every element in ingredients
        for (int i = 0; i < ingredientsSize; i++) {
            // Create a new Grocery object for each ingredient
            groceryList[i] = new Grocery(this.recipeID, this.name, ingredients.get(i), false, aisle.get(i));
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All all members of groceryList to the Grocery Table.
                groceryDao.insertAll(groceryList);
            }
        });
    }

    private void printIngredients() {
        System.out.println("\n---------- Ingredients ----------");
        List<String> ingredients = Methods.deserializeList(this.ingredients);
        for (String ing: ingredients) {
            System.out.println(ing);
        }
        System.out.println();
    }

    private void printInstructions() {
        System.out.println("\n---------- Instructions ----------");
        List<String> instructions = Methods.deserializeList(this.instructions);
        for (String inst: instructions) {
            System.out.println(inst);
        }
        System.out.println();
    }

    private void printDescription() {
        System.out.println("\n---------- Description ----------");
        System.out.println(this.description);
        System.out.println();
    }

    /*
     Checks if the Recipe object identified by 'object' is the same as the Recipe object
     identified by 'id' Parameter object = Recipe object identified by 'object'
     Returns True if the Recipe 'object' and 'id' are the same; otherwise, return False
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    // Return String representation of database primary key id
    @Override
    public String toString() {
        return id.toString();
    }

    public String instructionsToString() {
        List<String> instructions = Methods.deserializeList(this.instructions);
        StringBuilder item = new StringBuilder();
        int step = 1;
        for (String instruction : instructions) {
            item.append(step + ") " + instruction + "\n");
            step++;
        }
        return item.toString();
    }

    public String ingredientsToString() {
        List<String> ingredients = Methods.deserializeList(this.ingredients);
        StringBuilder item = new StringBuilder();
        int step = 1;
        for (String ingredient : ingredients) {
            item.append(step + ") " + ingredient + "\n");
            step++;
        }
        return item.toString();
    }
}
