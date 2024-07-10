package com.example.fastishaapp;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Locale;

public class Process extends AppCompatActivity {
    TextView itemTypeTextView, descriptionTextView, fromLocationTextView, toLocationTextView, senderPhoneNo, receiverPhoneNo,dateTextView,timeTextView;
    DatabaseReference databaseReference;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        confirm = findViewById(R.id.finalConfirm);
        itemTypeTextView = findViewById(R.id.itemTypeTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        fromLocationTextView = findViewById(R.id.itemFromLocation);
        toLocationTextView = findViewById(R.id.itemToLocation);
        senderPhoneNo = findViewById(R.id.sendPhoneNo);
        receiverPhoneNo = findViewById(R.id.receivePhoneNo);
        dateTextView = findViewById(R.id.date);
        timeTextView = findViewById(R.id.time);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        String currentTime = timeFormat.format(new Date());

        dateTextView.setText(currentDate);
        timeTextView.setText(currentTime);

        Intent intent = getIntent();
        String itemType = intent.getStringExtra("itemType");
        String description = intent.getStringExtra("description");
        String fromLocation = intent.getStringExtra("fromLocation");
        String toLocation = intent.getStringExtra("toLocation");
        String phoneSender = intent.getStringExtra("sPhoneNo");
        String phoneReceiver = intent.getStringExtra("rPhoneNo");

        itemTypeTextView.setText(itemType);
        descriptionTextView.setText(description);
        fromLocationTextView.setText(fromLocation);
        toLocationTextView.setText(toLocation);
        senderPhoneNo.setText(phoneSender);
        receiverPhoneNo.setText(phoneReceiver);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userUId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference userItemsRef = databaseReference.child("users").child(userUId).child("your packages").push(); // Create a unique key for each item

                userItemsRef.child("item").setValue(itemType, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            userItemsRef.child("description").setValue(description, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError != null) {
                                        Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                    } else {
                                        userItemsRef.child("fromLocation").setValue(fromLocation, new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                if (databaseError != null) {
                                                    Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                                } else {
                                                    userItemsRef.child("toLocation").setValue(toLocation, new DatabaseReference.CompletionListener() {
                                                        @Override
                                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                            if (databaseError != null) {
                                                                Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                                            } else {
                                                                userItemsRef.child("senderPhoneNo").setValue(phoneSender, new DatabaseReference.CompletionListener() {
                                                                    @Override
                                                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                                        if (databaseError != null) {
                                                                            Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                                                        } else {
                                                                            userItemsRef.child("receiverPhoneNo").setValue(phoneReceiver, new DatabaseReference.CompletionListener() {
                                                                                @Override
                                                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                                                    if (databaseError != null) {
                                                                                        Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                                                                    } else {
                                                                                        Toast.makeText(Process.this, "Details have been entered successfully", Toast.LENGTH_LONG).show();
                                                                                    }
                                                                                }
                                                                            });
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
