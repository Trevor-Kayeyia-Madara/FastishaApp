package com.example.fastishaapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText name, email, phoneNumber, password, confirmPassword;
    Button signUpButton;
    TextView redirectLogin;
    String gender;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    RadioGroup radioGroupGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        redirectLogin = findViewById(R.id.redirectLogin);

        redirectLogin.setOnClickListener(view -> {
            Intent intent = new Intent(Signup.this, Login.class);
            startActivity(intent);
        });
        name = findViewById(R.id.firstName);
        email = findViewById(R.id.emailSignup);
        phoneNumber = findViewById(R.id.phoneNumber);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        password = findViewById(R.id.passwordSignup);
        confirmPassword = findViewById(R.id.confirmPassword);
        progressBar = findViewById(R.id.signUpProgressBar);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();
        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });

    }

    private void signUpUser() {
        final String username = name.getText().toString();
        final String userEmail = email.getText().toString();
        final String userPassword = password.getText().toString();
        final String userPhoneNumber = phoneNumber.getText().toString();
        final String userConfirmPwd = confirmPassword.getText().toString();

        int selectedRadioButtonId = radioGroupGender.getCheckedRadioButtonId();

        if(selectedRadioButtonId != -1){
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            gender = selectedRadioButton.getText().toString();
        }

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(userPhoneNumber) || TextUtils.isEmpty(userConfirmPwd)) {
            progressBar.setVisibility(View.GONE);
            name.setError("Details are required");
            return;
        }

        if (!userPassword.equals(userConfirmPwd)) {
            progressBar.setVisibility(View.GONE);
            confirmPassword.setError("Passwords do not match");
            return;
        }

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Signup.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                    String userId = mAuth.getCurrentUser().getUid();

                    DatabaseReference userRef = mRef.child("users").child(userId);
                    userRef.child("Name").setValue(username);
                    userRef.child("email").setValue(userEmail);
                    userRef.child("phoneNumber").setValue(userPhoneNumber);
                    userRef.child("gender").setValue(gender);
                    userRef.child("userUID").setValue(userId);

                    Intent intent = new Intent(Signup.this, HomePage.class);
                    startActivity(intent);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Signup.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}