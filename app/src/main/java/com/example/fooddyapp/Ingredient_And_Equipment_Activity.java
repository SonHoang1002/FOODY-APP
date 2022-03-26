package com.example.fooddyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddyapp.Adapter.IngredientsAdapter;
import com.example.fooddyapp.Adapter.InstructionsAdapter;
import com.example.fooddyapp.Adapter.SimilarRecipeAdapter;
import com.example.fooddyapp.Listeners.IngredientClickListener;
import com.example.fooddyapp.Listeners.InstructionsListener;
import com.example.fooddyapp.Listeners.RecipeClickListener;
import com.example.fooddyapp.Listeners.RecipeDetailsListener;
import com.example.fooddyapp.Listeners.SimilarRecipesListener;
import com.example.fooddyapp.Models.InstructionsResponse;
import com.example.fooddyapp.Models.RecipeDetailsResponse;
import com.example.fooddyapp.Models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;







//                                FILE NÀY CHUẨN BỊ TIẾN HÀNH ACTIVITY 5











public class Ingredient_And_Equipment_Activity extends AppCompatActivity {
    int id;
    TextView textView_meal_name, textView_meal_source, textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recycle_meal_ingredients, recycler_meal_similar, recycler_meal_instructions;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionsAdapter instructionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recycle_meal_ingredients = findViewById(R.id.recycle_meal_ingredients);
        recycler_meal_similar = findViewById(R.id.recycler_meal_similar);
        recycler_meal_instructions = findViewById(R.id.recycler_meal_instructions);


        id = Integer.parseInt(getIntent().getStringExtra("id"));
        Log.d("abc", "Ingredient get: " + id);

        manager = new RequestManager(Ingredient_And_Equipment_Activity.this);
        Log.d("abc", "after manager");
//        manager.getRecipeDetails(recipeDetailsListener, id);
//        Log.d("abc","after getRecipeDetails");

//        manager.getSimilarRecipes(similarRecipesListener,id);
//        Log.d("abc","after getSimilarRecipes");
        manager.getInstructions(instructionsListener, id);
        Log.d("abc", "after getInstructions");


        dialog = new ProgressDialog(this);
        Log.d("abc", "after ProgressDialog");

        dialog.show();
        Log.d("abc", "after show()");

    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {

            dialog.dismiss();
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);
            textView_meal_summary.setText(Html.fromHtml(response.summary));
            Picasso.get().load(response.image).into(imageView_meal_image);
            recycle_meal_ingredients.setHasFixedSize(true);

            recycle_meal_ingredients.setLayoutManager(new LinearLayoutManager(Ingredient_And_Equipment_Activity.this, LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter = new IngredientsAdapter(Ingredient_And_Equipment_Activity.this, response.extendedIngredients);
            recycle_meal_ingredients.setAdapter(ingredientsAdapter);
            Log.d("abc", "recipeDetailsListener used ");
        }

        @Override
        public void didError(String message) {
            Toast.makeText(Ingredient_And_Equipment_Activity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    //    private final SimilarRecipesListener  similarRecipesListener  = new SimilarRecipesListener() {
//        @Override
//        public void didFetch(List<SimilarRecipeResponse> response, String message) {
//            recycler_meal_similar.setHasFixedSize(true);
//            recycler_meal_similar.setLayoutManager(new LinearLayoutManager(Ingredient_And_Equipment_Activity.this, LinearLayoutManager.HORIZONTAL,false));
//
//            similarRecipeAdapter = new SimilarRecipeAdapter(Ingredient_And_Equipment_Activity.this,response,ingredientClickListener);
//
//            recycler_meal_similar.setAdapter(similarRecipeAdapter);
//            Log.d("abc","similarRecipesListener used");
//        }
//
//        @Override
//        public void didError(String message) {
//            Toast.makeText(Ingredient_And_Equipment_Activity.this,message,Toast.LENGTH_SHORT).show();
//        }
//    };
    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void didFetch(List<InstructionsResponse> response, String message) {

            recycler_meal_instructions.setHasFixedSize(true);
            recycler_meal_instructions.setLayoutManager(
                    new LinearLayoutManager(Ingredient_And_Equipment_Activity.this, LinearLayoutManager.VERTICAL, false)
            );
            instructionsAdapter = new InstructionsAdapter(Ingredient_And_Equipment_Activity.this, response);
            recycler_meal_instructions.setAdapter(instructionsAdapter);
            Log.d("abc", "instructionsListener used");
        }

        //
        @Override
        public void didError(String message) {
        }
    };
    private final IngredientClickListener ingredientClickListener = id -> Toast.makeText(Ingredient_And_Equipment_Activity.this, id, Toast.LENGTH_SHORT).show();
}