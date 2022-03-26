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

public class SignUpActivity extends AppCompatActivity {
    Button btnSU;
    TextView eVRPassSU, eVPassSU,eVEmailSU;
    private FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        eVRPassSU = findViewById(R.id.eVRPassSU);
        btnSU = findViewById(R.id.btnSU);
        eVPassSU = findViewById(R.id.eVPassSU);
        eVEmailSU = findViewById(R.id.eVEmailSU);

        btnSU.setOnClickListener(view -> {
            SignUp();
        });
    }
    private void SignUp() {
        String email = eVEmailSU.getText().toString();
        String pass = eVPassSU.getText().toString();
        String repass = eVRPassSU.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Email Is Empty", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Password Is Empty", Toast.LENGTH_SHORT).show();
        }else if(pass.equals(repass)){
            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Sign Up Is Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                        Toast.makeText(getApplicationContext(), "Please Sign In Before Discover My App!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Sign Up Is Not Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, SignUpActivity.class));

                    }
                }
            });
        }else{

        }


    }
}