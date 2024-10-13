package com.example.fastishaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    private TextView userNameTextView, userEmailTextView, userGenderTextView, userPhoneTextView;
    private DatabaseReference userDatabaseRef;
    private FirebaseAuth mAuth;
    Button logoutButton;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize views
        userNameTextView = findViewById(R.id.userNameTextView);
        userEmailTextView = findViewById(R.id.userEmailTextView);
        userGenderTextView = findViewById(R.id.userGenderTextView);
        userPhoneTextView = findViewById(R.id.userPhoneTextView);
        logoutButton = findViewById(R.id.logoutBtn);

        // Initialize Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        // Get current user UID
        String userUID = mAuth.getCurrentUser().getUid();

        // Create a reference to the "users" node in Firebase database
        userDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(userUID);

        // Fetch and display the user data
        fetchUserBioData();

        sessionManager = new SessionManager(this);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.clearSession();
                mAuth.signOut();
                Toast.makeText(UserProfileActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();

                // Redirect the user to the Login activity
                Intent intent = new Intent(UserProfileActivity.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                // Finish the current activity
                finish();
            }
        });
    }

    private void fetchUserBioData() {
        userDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Retrieve user details from snapshot
                    String name = snapshot.child("Name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String gender = snapshot.child("gender").getValue(String.class);
                    String phone = snapshot.child("phoneNumber").getValue(String.class);

                    // Display user details
                    userNameTextView.setText("Name: " + (name != null ? name : "N/A"));
                    userEmailTextView.setText("Email: " + (email != null ? email : "N/A"));
                    userGenderTextView.setText("Gender: " + (gender != null ? gender : "N/A"));
                    userPhoneTextView.setText("Phone: " + (phone != null ? phone : "N/A"));
                } else {
                    Toast.makeText(UserProfileActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
