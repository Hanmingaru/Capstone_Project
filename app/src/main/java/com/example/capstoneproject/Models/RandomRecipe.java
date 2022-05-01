package com.example.capstoneproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RandomRecipe implements Parcelable {
    public boolean vegetarian;
    public boolean vegan;
    public boolean glutenFree;
    public boolean dairyFree;
    public boolean veryHealthy;
    public boolean cheap;
    public boolean veryPopular;
    public boolean sustainable;
    public int weightWatcherSmartPoints;
    public String gaps;
    public boolean lowFodmap;
    public int aggregateLikes;
    public int spoonacularScore;
    public int healthScore;
    public String creditsText;
    public String license;
    public String sourceName;
    public double pricePerServing;
    public List<ExtendedIngredient> extendedIngredients = new ArrayList<>();
    public int id;
    public String title;
    public int readyInMinutes;
    public int servings;
    public String sourceUrl;
    public String image;
    public String imageType;
    public String summary;
    public List<String> cuisines;
    public List<String> dishTypes;
    public List<String> diets;
    public List<String> occasions;
    public String instructions;
    public List<AnalyzedInstruction> analyzedInstructions = new ArrayList<>();
    public Object originalId;
    public String spoonacularSourceUrl;

    protected RandomRecipe(Parcel in) {
        vegetarian = in.readByte() != 0;
        vegan = in.readByte() != 0;
        glutenFree = in.readByte() != 0;
        dairyFree = in.readByte() != 0;
        veryHealthy = in.readByte() != 0;
        cheap = in.readByte() != 0;
        veryPopular = in.readByte() != 0;
        sustainable = in.readByte() != 0;
        weightWatcherSmartPoints = in.readInt();
        gaps = in.readString();
        lowFodmap = in.readByte() != 0;
        aggregateLikes = in.readInt();
        spoonacularScore = in.readInt();
        healthScore = in.readInt();
        creditsText = in.readString();
        license = in.readString();
        sourceName = in.readString();
        pricePerServing = in.readDouble();
        id = in.readInt();
        title = in.readString();
        readyInMinutes = in.readInt();
        servings = in.readInt();
        sourceUrl = in.readString();
        image = in.readString();
        imageType = in.readString();
        summary = in.readString();
        cuisines = in.createStringArrayList();
        dishTypes = in.createStringArrayList();
        diets = in.createStringArrayList();
        occasions = in.createStringArrayList();
        instructions = in.readString();
        spoonacularSourceUrl = in.readString();
        in.readTypedList(extendedIngredients, ExtendedIngredient.CREATOR);
        in.readTypedList(analyzedInstructions, AnalyzedInstruction.CREATOR);
    }

    public static final Creator<RandomRecipe> CREATOR = new Creator<RandomRecipe>() {
        @Override
        public RandomRecipe createFromParcel(Parcel in) {
            return new RandomRecipe(in);
        }

        @Override
        public RandomRecipe[] newArray(int size) {
            return new RandomRecipe[size];
        }
    };

    public boolean isVegetarian() {
        return vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public boolean isVeryHealthy() {
        return veryHealthy;
    }

    public boolean isCheap() {
        return cheap;
    }

    public boolean isVeryPopular() {
        return veryPopular;
    }

    public boolean isSustainable() {
        return sustainable;
    }

    public int getWeightWatcherSmartPoints() {
        return weightWatcherSmartPoints;
    }

    public String getGaps() {
        return gaps;
    }

    public boolean isLowFodmap() {
        return lowFodmap;
    }

    public int getAggregateLikes() {
        return aggregateLikes;
    }

    public int getSpoonacularScore() {
        return spoonacularScore;
    }

    public int getHealthScore() {
        return healthScore;
    }

    public String getCreditsText() {
        return creditsText;
    }

    public String getLicense() {
        return license;
    }

    public String getSourceName() {
        return sourceName;
    }

    public double getPricePerServing() {
        return pricePerServing;
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public String getSummary() {
        return summary;
    }

    public List<String> getCuisines() {
        return cuisines;
    }

    public List<String> getDishTypes() {
        return dishTypes;
    }

    public List<String> getDiets() {
        return diets;
    }

    public List<String> getOccasions() {
        return occasions;
    }

    public String getInstructions() {
        return instructions;
    }

    public List<AnalyzedInstruction> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public Object getOriginalId() {
        return originalId;
    }

    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    public String ingredientsToString() {
        StringBuilder item = new StringBuilder();
        for (int i = 0; i < extendedIngredients.size(); i++ ) {
            ExtendedIngredient ingredient = extendedIngredients.get(i);
            item.append(String.format("%d) %s %.2f %s\n", i, ingredient.getName(), ingredient.getAmount(), ingredient.getUnit()));
        }
        return item.toString();
    }

    public String instructionsToString() {
        StringBuilder item = new StringBuilder();
        for (int i = 0; i < analyzedInstructions.size(); i++ ) {
            AnalyzedInstruction instruction = analyzedInstructions.get(i);
            List<Step> steps = instruction.getSteps();
            item.append(instruction.getName() +"\n");
            for (int j = 0; j < steps.size(); j++ ) {
                item.append(steps.get(j).number + ") " + steps.get(j).getStep() + "\n");
            }
        }
        return item.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (vegetarian ? 1 : 0));
        parcel.writeByte((byte) (vegan ? 1 : 0));
        parcel.writeByte((byte) (glutenFree ? 1 : 0));
        parcel.writeByte((byte) (dairyFree ? 1 : 0));
        parcel.writeByte((byte) (veryHealthy ? 1 : 0));
        parcel.writeByte((byte) (cheap ? 1 : 0));
        parcel.writeByte((byte) (veryPopular ? 1 : 0));
        parcel.writeByte((byte) (sustainable ? 1 : 0));
        parcel.writeInt(weightWatcherSmartPoints);
        parcel.writeString(gaps);
        parcel.writeByte((byte) (lowFodmap ? 1 : 0));
        parcel.writeInt(aggregateLikes);
        parcel.writeInt(spoonacularScore);
        parcel.writeInt(healthScore);
        parcel.writeString(creditsText);
        parcel.writeString(license);
        parcel.writeString(sourceName);
        parcel.writeDouble(pricePerServing);
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeInt(readyInMinutes);
        parcel.writeInt(servings);
        parcel.writeString(sourceUrl);
        parcel.writeString(image);
        parcel.writeString(imageType);
        parcel.writeString(summary);
        parcel.writeStringList(cuisines);
        parcel.writeStringList(dishTypes);
        parcel.writeStringList(diets);
        parcel.writeStringList(occasions);
        parcel.writeString(instructions);
        parcel.writeString(spoonacularSourceUrl);
        parcel.writeTypedList(extendedIngredients);
        parcel.writeTypedList(analyzedInstructions);
    }
}
