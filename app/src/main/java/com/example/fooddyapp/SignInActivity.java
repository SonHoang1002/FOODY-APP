package com.example.fooddyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    Button btnSignIn,btnSignUp;
    TextView eVEmailSI, eVPassSI;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = findViewById(R.id.btnSignIn);
        eVEmailSI = findViewById(R.id.eVEmailSI);
        eVPassSI = findViewById(R.id.eVPassSI);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn.setOnClickListener(view -> {
            SignIn();
        });
        btnSignUp.setOnClickListener(view->{
            startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
        });
    }
    private void SignIn() {
        String email = eVEmailSI.getText().toString();
        String pass = eVPassSI.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Email Is Empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Password Is Empty", Toast.LENGTH_SHORT).show();
        }

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Sign In Is Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Sign In Is Not Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this, SignInActivity.class));

                }
            }
        });

    }
}