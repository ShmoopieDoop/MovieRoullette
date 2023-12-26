package com.example.movieroulette;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomePage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        disableBackButton();

//        // sign out button
//        findViewById(R.id.landing_sign_out_button).setOnClickListener(
//                view -> {
//                    AuthUI.getInstance().
//                            signOut(HomePage.this)
//                            .addOnSuccessListener(task -> {
//                                Toast.makeText(HomePage.this, "Signed out", Toast.LENGTH_SHORT).show();
//                            });
//                }
//        );

        drawerLayout = findViewById(R.id.home_main_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.nav_open,
                R.string.nav_close
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        FloatingActionButton fab = findViewById(R.id.home_drawer_button);
        fab.setOnClickListener(view -> {
            if (drawerLayout.isOpen()) {
                drawerLayout.close();
            } else {
                drawerLayout.open();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void disableBackButton() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Do nothing
            }
        });
    }


}