package com.example.fooddyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RecommendActivity extends AppCompatActivity {
    Button btn_GoTo;
    TextView tv_Welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        findViewById(R.id.btn_GoTo).setOnClickListener(view->{
            startActivity(new Intent(RecommendActivity.this, SignInActivity.class));
        });
    }
}