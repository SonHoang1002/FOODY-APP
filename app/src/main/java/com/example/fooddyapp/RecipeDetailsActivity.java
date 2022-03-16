package com.example.fooddyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddyapp.Adapter.IngredientsAdapter;
import com.example.fooddyapp.Adapter.SimilarRecipeAdapter;
import com.example.fooddyapp.Listeners.RecipeClickListener;
import com.example.fooddyapp.Listeners.RecipeDetailsListener;
import com.example.fooddyapp.Listeners.SimilarRecipesListener;
import com.example.fooddyapp.Models.RecipeDetailsResponse;
import com.example.fooddyapp.Models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
   int id;
   TextView textView_meal_name, textView_meal_source, textView_meal_summary;
   ImageView  imageView_meal_image;
   RecyclerView recycle_meal_ingredients,recycler_meal_similar;
   RequestManager manager;
   ProgressDialog dialog;
   IngredientsAdapter ingredientsAdapter;
   SimilarRecipeAdapter similarRecipeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        textView_meal_name =findViewById(R.id.textView_meal_name);
        textView_meal_source =findViewById(R.id.textView_meal_source);
        textView_meal_summary =findViewById(R.id.textView_meal_summary);
        imageView_meal_image =findViewById(R.id.imageView_meal_image);
        recycle_meal_ingredients =findViewById(R.id.recycle_meal_ingredients);
        recycler_meal_similar = findViewById(R.id.recycler_meal_similar);

        id = Integer.parseInt(getIntent().getStringExtra("id"));

        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getSimilarRecipes(similarRecipesListener,id);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details...");
        dialog.show();
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {

            dialog.dismiss();
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);
            textView_meal_summary.setText(response.summary);
            Picasso.get().load(response.image).into(imageView_meal_image);
            recycle_meal_ingredients.setHasFixedSize(true);
            recycle_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,LinearLayoutManager.HORIZONTAL,false));

            ingredientsAdapter = new IngredientsAdapter(RecipeDetailsActivity.this,response.extendedIngredients);
            recycle_meal_ingredients.setAdapter(ingredientsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    private final SimilarRecipesListener  similarRecipesListener  = new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            recycler_meal_similar.setHasFixedSize(true);
            recycler_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL,false));

            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetailsActivity.this,response,recipeClickListener);

            recycler_meal_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this,message,Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener =new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {

            Toast.makeText(RecipeDetailsActivity.this,id,Toast.LENGTH_SHORT).show();
        }
    };
}