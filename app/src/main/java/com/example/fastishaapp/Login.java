package com.example.fastishaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

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
                String myEmail = email.getText().toString();
                String myPassword = password.getText().toString();

                if(myEmail.isEmpty() || myPassword.isEmpty()){
                    Toast.makeText(Login.this, "Please input your email and password", Toast.LENGTH_SHORT).show();
                }else {
                    signup(myEmail, myPassword);
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

    private void signup(String myEmail, String myPassword) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(myEmail,myPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                    String userID = mAuth.getCurrentUser().getUid();
                    mRef.child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String role = snapshot.child("role").getValue(String.class);
                                if("admin".equals(role)){
                                    
                                } else if ("agent".equals(role)) {
                                    
                                }else {
                                    Intent intent = new Intent(Login.this, HomePage.class);
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(Login.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}