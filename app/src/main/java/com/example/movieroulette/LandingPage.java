package com.example.movieroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        
        // login button
        findViewById(R.id.login_button).setOnClickListener(
                view -> {
                    Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                    startActivity(intent);
                }
        );
        // sign up button
        findViewById(R.id.sign_up_button).setOnClickListener(
                view -> {
                    Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
                    startActivity(intent);
                }
        );
        
    }
}