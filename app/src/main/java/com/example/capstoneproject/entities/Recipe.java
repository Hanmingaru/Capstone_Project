/*
 * Created by Elliott Rheault on 2022.3.31
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

// Documentation: https://developer.android.com/training/data-storage/room/defining-data

package com.example.capstoneproject.entities;

import androidx.annotation.Size;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.capstoneproject.Models.ExtendedIngredient;
import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.example.capstoneproject.globals.Methods;

// Use `@Fts3` only if your app has strict disk space requirements or if you
// require compatibility with an older SQLite version.
@Fts4
@Entity(tableName = "recipe", indices = {@Index("name"), @Index(value = {"name"})})
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
    public Recipe(RandomRecipe randomRecipe, RecipeNutritionResponse recipeNutrition) {
        this(randomRecipe.getSourceName(),
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
                randomRecipe.isVegetarian(),
                randomRecipe.isVegan(),
                randomRecipe.isGlutenFree(),
                randomRecipe.isDairyFree(),
                randomRecipe.getDiets());
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
     * @param vegetarian          Boolean if recipe is vegetarian
     * @param vegan               Boolean if recipe is vegan
     * @param glutenFree          Boolean if recipe is gluten free
     * @param dairyFree           Boolean if recipe is dairy free
     * @param diets               List of Strings of diets the recipe follows
     */
    public Recipe(@NotNull String name, @NotNull String imageUrl, @NotNull String calories,
                  @NotNull String protein, @NotNull String fat, @NotNull String carbs,
                  @NotNull String websiteLink, @NotNull Integer preparationTime,
                  @NotNull Double pricePerServing, @NotNull String description,
                  List<ExtendedIngredient> extendedIngredients, String instructions,
                  boolean vegetarian, boolean vegan, boolean glutenFree, boolean dairyFree,
                  List<String> diets) {
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
        this.description = description;

        // Fields that require more processing

        /*
        instructions: a string of instructions for the recipe, delimenated by new line characters,
            parse into List<String> then serialize.

         diet: check boolean values and append them to List<String> diets. Then serialize the list.

         Ingredients:
         */

        /* ------- Diets ------- */

        // Get diet info
        List<String> allDiets = diets;

        // Append additional diet info to diets
        if (vegetarian)
            allDiets.add("vegetarian");
        if (vegan)
            allDiets.add("vegan");
        if (glutenFree)
            allDiets.add("gluten free");
        if (dairyFree)
            allDiets.add("dairy free");

        // Communicate if there are no diet the recipe follows
        if (allDiets.size() == 0)
            allDiets.add("none");

        // Save list as serialized string. Can be deserialized on retrieval
        this.diets = Methods.serializeList(allDiets);

        /* ------- Instructions ------- */

        // Get instructions as an array of instructions
        String[] instructionsArray = instructions.split("\\R+");

        if (instructionsArray.length == 0) {
            instructionsArray[0] = "See recipe website for instructions";
        }

        // Parse instructionsArray into a List
        List<String> instructionList = Arrays.asList(instructionsArray);

        // Serialize the List into String for database
        this.instructions = Methods.serializeList(instructionList);

        /* ------- Ingredients ------- */

        // Iterate through every ExtendedIngredient and add its
        // original String to the ingredientsList
        List<String> ingredientsList = new ArrayList<>();
        for (ExtendedIngredient exIng: extendedIngredients) {
            ingredientsList.add(exIng.getOriginal());
        }

        // Serialize the List into String for database
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

    /*
    ================================
    Instance Methods Used Internally
    ================================
     */

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
}
