package com.example.fastishaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText emailEditText, passwordEditText;
    Button loginButton;
    FirebaseAuth mAuth;
    DatabaseReference dbRef;
    TextView signup;
    SessionManager sessionManager;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize SessionManager
        sessionManager = new SessionManager(getApplicationContext());

        // Check if user is already logged in
        if (sessionManager.isLoggedIn()) {
            if (sessionManager.isAdmin()) {
                // User is logged in as admin, navigate to Admin activity
                startActivity(new Intent(MainActivity.this, Admin.class));
            } else {
                // User is logged in as customer, navigate to Customer activity
                startActivity(new Intent(MainActivity.this, Customer.class));
            }
            finish();
        }

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signup);

        progressBar = findViewById(R.id.loginProgressBar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else {
                    signIn(email, password);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signIn(final String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success
                            Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                            // Set user as logged in
                            sessionManager.setLoggedIn(true);

                            // Fetch user role from Realtime Database
                            String userId = mAuth.getCurrentUser().getUid();
                            dbRef.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        String role = snapshot.child("role").getValue(String.class);
                                        if ("admin".equals(role)) {
                                            // Set user as admin
                                            sessionManager.setAdmin(true);
                                            // Navigate to Admin activity
                                            startActivity(new Intent(MainActivity.this, Admin.class));
                                        } else if ("agent".equals(role)) {
                                            //TODO Add agent activity
                                        } else {
                                            // Set user as not admin
                                            sessionManager.setAdmin(false);
                                            // Navigate to Customer activity
                                            startActivity(new Intent(MainActivity.this, Customer.class));
                                        }
                                        finish();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Role not found.", Toast.LENGTH_SHORT).show();
                                        Log.e(TAG, "Snapshot does not exist.");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(MainActivity.this, "Failed to retrieve role.", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "DatabaseError: ", error.toException());
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Authentication failed: ", task.getException());
                        }
                    }
                });
    }
}
