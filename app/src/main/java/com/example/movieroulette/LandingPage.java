package com.example.movieroulette;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LandingPage extends AppCompatActivity {
    public static final String TAG = "LandingPage";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        updateUser();
        if (user != null) {
            Toast.makeText(this, user.getDisplayName() + " is signed in", Toast.LENGTH_SHORT).show();
            goToChoiceIfNoGroup();
        } else {
            // No user is signed in
            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                    new AuthUI.IdpConfig.EmailBuilder().build()
            );

            // Create and launch sign-in intent
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setTheme(R.style.Base_Theme_MovieRoulette)
                    .build();
            signInLauncher.launch(signInIntent);
        }
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            Log.d(TAG, "user signed in");
            updateUser();
            goToChoiceIfNoGroup();

        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            if (response == null) {
                Log.w(TAG, "user cancelled sign in");
            }
        }
    }

    private void goToChoiceIfNoGroup() {
        DocumentReference userRef = db.collection("users")
                .document(user.getUid());
        userRef.get().addOnSuccessListener(
                documentSnapshot -> {
                    //noinspection unchecked
                    Map<String, Object> userGroups = (Map<String, Object>)documentSnapshot.get("groups");
                    if (userGroups == null || userGroups.isEmpty()) {
                        Intent intent = new Intent(this, GroupChoicePage.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(this, HomePage.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
    private void updateUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
}