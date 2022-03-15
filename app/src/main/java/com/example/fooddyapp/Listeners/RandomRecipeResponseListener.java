package com.example.fooddyapp.Listeners;

import com.example.fooddyapp.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFerch(RandomRecipeApiResponse response,String message);
    void didError(String message);
}
