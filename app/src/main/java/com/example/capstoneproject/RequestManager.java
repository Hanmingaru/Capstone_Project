package com.example.capstoneproject;

import android.content.Context;
import android.util.Log;

import com.example.capstoneproject.Listeners.NutritionAPIResponseListener;
import com.example.capstoneproject.Listeners.RandomAPIResponseListener;
import com.example.capstoneproject.Models.RandomRecipeResponse;
import com.example.capstoneproject.Models.RecipeNutritionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public RequestManager(Context context) {
        this.context = context;
    }

    public void GetRandomRecipes(RandomAPIResponseListener listener, List<String> tags){
        CallRandomRecipe callRandomRecipe = retrofit.create(CallRandomRecipe.class);
        Call<RandomRecipeResponse> call = callRandomRecipe.callRandomRecipe(context.getString(R.string.api_key), "1", tags );
        call.enqueue(new Callback<RandomRecipeResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeResponse> call, Response<RandomRecipeResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body().getRecipes(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void GetNutritionByID(NutritionAPIResponseListener listener, int id){
        Log.d("Response", "" + id);
        CallRecipeNutrition callRecipeNutrition = retrofit.create(CallRecipeNutrition.class);
        Call<RecipeNutritionResponse> call = callRecipeNutrition.callRecipeNutrition(context.getString(R.string.api_key), id);
        call.enqueue((new Callback<RecipeNutritionResponse>() {
            @Override
            public void onResponse(Call<RecipeNutritionResponse> call, Response<RecipeNutritionResponse> response) {
                if (!response.isSuccessful()) {
                     listener.didError(response.message());
                     return;
                }
                listener.didFetch(response.body(), response.message());
            }
            @Override
            public void onFailure(Call<RecipeNutritionResponse> call, Throwable t) {
                listener.didError((t.getMessage()));
            }
        }));
    }
    private interface CallRandomRecipe{
        @GET("recipes/random")
        Call<RandomRecipeResponse> callRandomRecipe(
                @Header("X-RapidAPI-Key") String api_key,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }

    private interface CallRecipeNutrition{
        @GET("recipes/{id}/nutritionWidget.json")
        Call<RecipeNutritionResponse> callRecipeNutrition(
            @Header("X-RapidAPI-Key") String api_key,
            @Path("id") int id
        );
    }
}
