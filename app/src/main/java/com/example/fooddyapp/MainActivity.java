package com.example.fooddyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fooddyapp.Adapter.RandomRecipeAdapter;
import com.example.fooddyapp.Listeners.RandomRecipeResponseListener;
import com.example.fooddyapp.Models.RandomRecipeApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    List<String> tags = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");

        spinner = findViewById(R.id.spinner_tabs);
        ArrayAdapter arrayAdapter= ArrayAdapter.createFromResource(
                this,R.array.tags, R.layout.spinner_text) ;

        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        Log.d("abc","before setAdapter");
        spinner.setAdapter(arrayAdapter);
        Log.d("abc","before setOnItemSelectedListener");
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        manager = new RequestManager(this);


//        manager.getRandomRecipes(randomRecipeResponseListener);
//        dialog.show();
    }
    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFerch(RandomRecipeApiResponse response, String message) {
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this,response.recipes);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("abc","onItemSelected Function");
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());
            if(tags ==null ){
                Log.d("abc","No Context");
            }
            String joined = TextUtils.join(", ", tags);
            Log.d("abc","This is tags context: "+ joined);
            manager.getRandomRecipes(randomRecipeResponseListener,tags);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

            Log.d("abc","onNothingSelected Function");
        }
    };
}