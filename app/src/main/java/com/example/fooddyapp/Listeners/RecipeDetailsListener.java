package com.example.fooddyapp.Listeners;

import com.example.fooddyapp.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {


    void didFetch(RecipeDetailsResponse response, String message);

    void didError (String message);
}
