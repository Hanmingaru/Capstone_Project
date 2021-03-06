package com.example.capstoneproject;

import android.content.Context;
import android.text.TextUtils;

import com.example.capstoneproject.Listeners.NutritionAPIResponseListener;
import com.example.capstoneproject.Listeners.RandomAPIResponseListener;
import com.example.capstoneproject.Listeners.RecipeByIdListener;
import com.example.capstoneproject.Listeners.RecipeSearchResponseListener;
import com.example.capstoneproject.Models.RandomRecipe;
import com.example.capstoneproject.Models.RandomRecipeResponse;
import com.example.capstoneproject.Models.RecipeNutritionResponse;
import com.example.capstoneproject.Models.RecipeSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
        String tagQuery = TextUtils.join(",", tags);
        CallRandomRecipe callRandomRecipe = retrofit.create(CallRandomRecipe.class);
        Call<RandomRecipeResponse> call = callRandomRecipe.callRandomRecipe(context.getString(R.string.api_key), "1", tagQuery );
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

    public void GetNutritionByID(NutritionAPIResponseListener listener, int id, int index){
        CallRecipeNutrition callRecipeNutrition = retrofit.create(CallRecipeNutrition.class);
        Call<RecipeNutritionResponse> call = callRecipeNutrition.callRecipeNutrition(context.getString(R.string.api_key), id);
        call.enqueue((new Callback<RecipeNutritionResponse>() {
            @Override
            public void onResponse(Call<RecipeNutritionResponse> call, Response<RecipeNutritionResponse> response) {
                if (!response.isSuccessful()) {
                     listener.didError(response.message());
                     return;
                }
                listener.didFetch(response.body(), response.message(), index);
            }
            @Override
            public void onFailure(Call<RecipeNutritionResponse> call, Throwable t) {
                listener.didError((t.getMessage()));
            }
        }));
    }

    public void SearchRecipe(RecipeSearchResponseListener listener, String query) {
        CallRecipeSearch callRecipeSearch = retrofit.create(CallRecipeSearch.class);
        Call<RecipeSearchResponse> call = callRecipeSearch.callRecipeSearch(context.getString(R.string.api_key), query);
        call.enqueue((new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body().getRecipes(), response.message());
            }
            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                listener.didError((t.getMessage()));
            }
        }));
    }

    public void GetRecipeByID(RecipeByIdListener listener, int id, int index){
        CallRecipeByID callRecipeByID = retrofit.create(CallRecipeByID.class);
        Call<RandomRecipe> call = callRecipeByID.callRecipeByID(context.getString(R.string.api_key),  id );
        call.enqueue(new Callback<RandomRecipe>() {
            @Override
            public void onResponse(Call<RandomRecipe> call, Response<RandomRecipe> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message(), index);
            }

            @Override
            public void onFailure(Call<RandomRecipe> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallRandomRecipe{
        @GET("recipes/random")
        Call<RandomRecipeResponse> callRandomRecipe(
                @Header("X-RapidAPI-Key") String api_key,
                @Query("number") String number,
                @Query("tags") String tags
        );
    }

    private interface CallRecipeNutrition{
        @GET("recipes/{id}/nutritionWidget.json")
        Call<RecipeNutritionResponse> callRecipeNutrition(
            @Header("X-RapidAPI-Key") String api_key,
            @Path("id") int id
        );
    }

    private interface CallRecipeSearch {
        @GET("recipes/complexSearch")
        Call<RecipeSearchResponse> callRecipeSearch(
            @Header("X-RapidAPI-Key") String api_key,
            @Query("query") String query
        );
    }

    private interface CallRecipeByID{
        @GET("recipes/{id}/information")
        Call<RandomRecipe> callRecipeByID(
            @Header("X-RapidAPI-Key") String api_key,
            @Path("id") int id
        );
    }
}
