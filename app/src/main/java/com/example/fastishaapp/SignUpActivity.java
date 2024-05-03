package com.example.fastishaapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    TextView login;
    EditText nameEditText, emailEditText, passwordEditText, phoneEditText, confirmPassword;
    Button signUpButton;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.editTextTextEmail);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        confirmPassword = findViewById(R.id.edtConfirmPassword);
        phoneEditText = findViewById(R.id.editTextPhone);
        signUpButton = findViewById(R.id.signUpButton);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.signupProgressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }
    private void signUpUser() {
        progressBar.setVisibility(View.VISIBLE);
        final String fullName = nameEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();
        final String phoneNumber = phoneEditText.getText().toString().trim();
        final  String confirmPwd = confirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(phoneNumber)) {
            progressBar.setVisibility(View.GONE);
            nameEditText.setError("Detail are required");
            return;
        }

        if(!password.equals(confirmPwd)){
            Toast.makeText(this, "Password don't match", Toast.LENGTH_SHORT).show();
        }



        // Create a new user with email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(SignUpActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();

                            // Get the user ID of the newly created user
                            String userId = mAuth.getCurrentUser().getUid();

                            // Save email, full name, phone number, and user UID to Realtime Database
                            DatabaseReference userRef = mDatabase.child("users").child(userId);
                            userRef.child("email").setValue(email);
                            userRef.child("fullName").setValue(fullName);
                            userRef.child("phoneNumber").setValue(phoneNumber);
                            userRef.child("userUID").setValue(userId);

                            // Navigate to the next activity or perform any other action
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.setVisibility(View.GONE);
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}