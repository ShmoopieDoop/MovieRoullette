package com.example.movieroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class CreateGroupPage extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public static final String TAG = "CreateGroupPage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_page);

        findViewById(R.id.create_group_submit_button).setOnClickListener(view -> {
            Map<String, Object> group = new HashMap<>();
            String name = ((EditText) findViewById(R.id.create_group_name_field)).getText().toString();

            group.put("name", name);
            Map<String, Object> members = new HashMap<>();
            members.put(user.getUid(), true);
            group.put("members", members);
            DocumentReference groupRef = db.collection("groups").document();
            groupRef.set(group).addOnSuccessListener(unused -> {
                Log.d(TAG, "Group created successfully with ID: " + groupRef.getId());
                createUserDocumentIfNotExits(groupRef.getId());
            });
        });
    }
    protected void createUserDocumentIfNotExits(String groupId) {
        db.collection("users").document(user.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.exists()) {
                Map<String, Object> userObject = new HashMap<>();
                userObject.put("groups", new HashMap<>());
                db.collection("users")
                        .document(user.getUid())
                        .set(userObject)
                        .addOnSuccessListener(unused -> Log.d(TAG, "User document created successfully"))
                        .addOnFailureListener(e -> Log.w(TAG, "User document creation failed " + e));
            }
            addGroupToUser(groupId);
        }).addOnFailureListener(e -> Log.w(TAG, "User document get failed " + e));
    }
    protected void addGroupToUser(String groupId) {
        db.collection("users")
                .document(user.getUid())
                .update("groups." + groupId, true)
                .addOnSuccessListener(unused -> {
                    Log.d(TAG, "Group added to user successfully");
                    startActivity(new Intent(CreateGroupPage.this, HomePage.class));
                    finish();
                })
                .addOnFailureListener(e -> Log.w(TAG, "Adding group to user failed " + e));
    }
}