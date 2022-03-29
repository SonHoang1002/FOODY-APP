package com.example.fooddyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {
    Button btnSignIn,btnSignUp;
    TextView eVEmailSI, eVPassSI;
    CheckBox cbRobot;
    GoogleApiClient googleApiClient;
    String SiteKey = "6LdL9ygfAAAAAGUaQWt_OJjcAx_LDW5C4cPjIkfB";// cần đăng kí tên pj tại đường link sau
    // https://www.google.com/recaptcha/admin/create
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = findViewById(R.id.btnSignIn);
        eVEmailSI = findViewById(R.id.eVEmailSI);
        eVPassSI = findViewById(R.id.eVPassSI);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setVisibility(View.INVISIBLE);

        btnSignUp.setOnClickListener(view->{
            startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
        });

        cbRobot = findViewById(R.id.cbRobot);

        Log.d("abc","cbRobot = findViewById(R.id.cbRobot)");
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(SignInActivity.this)
                .build();

        googleApiClient.connect();
        cbRobot.setOnClickListener(view -> {
            if(cbRobot.isChecked()){
                SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient,SiteKey)
                        .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                            @Override
                            public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                                Status status = recaptchaTokenResult.getStatus();
                                if(status !=null || status.isSuccess()){
                                    Toast.makeText(getApplicationContext(),"Successfully Verified..",Toast.LENGTH_SHORT).show();
                                    cbRobot.setTextColor(Color.GREEN);
                                    btnSignUp.setVisibility(View.VISIBLE);
                                    btnSignIn.setOnClickListener(view -> {
                                        SignIn();
                                    });
                                }
                            }
                        });
            }else{

                cbRobot.setTextColor(Color.RED);
            }
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}