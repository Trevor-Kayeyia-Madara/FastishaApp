package com.example.fastishaapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class Login extends AppCompatActivity {
    Button login, signup;
    EditText email, password;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    ProgressBar progressBar;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.isLoggedIn()) {
            navigateUserBasedOnRole();
            finish();
        }

        login = findViewById(R.id.loginBtn);
        signup = findViewById(R.id.signupBtn);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        progressBar = findViewById(R.id.loginProgressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myEmail = email.getText().toString().trim();
                String myPassword = password.getText().toString().trim();

                if (validateInputs(myEmail, myPassword)) {
                    loginUser(myEmail, myPassword);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInputs(String email, String password) {
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(Login.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            Toast.makeText(Login.this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loginUser(String myEmail, String myPassword) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(myEmail, myPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                    sessionManager.setLoggedIn(true);
                    fetchUserRole();
                } else {
                    Toast.makeText(Login.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchUserRole() {
        String userID = mAuth.getCurrentUser().getUid();
        mRef.child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String role = snapshot.child("role").getValue(String.class);
                    handleUserRole(role);
                } else {
                    Toast.makeText(Login.this, "Role not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Failed to retrieve role", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleUserRole(String role) {
        if ("admin".equals(role)) {
            sessionManager.setAdmin(true);
            // Navigate to Admin activity
        } else if ("agent".equals(role)) {
            // Navigate to Agent activity
        } else {
            sessionManager.setAdmin(false);
            Intent intent = new Intent(Login.this, HomePage.class);
            startActivity(intent);
        }
        finish();
    }

    private void navigateUserBasedOnRole() {
        if (sessionManager.isAdmin()) {
            startActivity(new Intent(Login.this, HomePage.class)); // Navigate to Admin page
        } else {
            startActivity(new Intent(Login.this, HomePage.class)); // Navigate to Customer page
        }
    }
}
