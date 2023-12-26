package com.example.movieroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class GroupChoicePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_choice_page);

        findViewById(R.id.choice_join_button).setOnClickListener(
                view -> {
                    Intent intent = new Intent(this, JoinGroupPage.class);
                    startActivity(intent);
                    finish();
                }
        );
        findViewById(R.id.choice_create_button).setOnClickListener(
                view -> {
                    Intent intent = new Intent(this, CreateGroupPage.class);
                    startActivity(intent);
                    finish();
                }
        );
    }
}