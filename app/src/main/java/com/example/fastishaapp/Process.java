package com.example.fastishaapp;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Process extends AppCompatActivity {
    TextView itemTypeTextView, descriptionTextView, fromLocationTextView, toLocationTextView, senderPhoneNo, receiverPhoneNo, dateTextView, timeTextView,transaction;
    DatabaseReference databaseReference;
    Button confirm;
    ProgressBar progressBar;

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
        transaction = findViewById(R.id.transaction);
        progressBar = findViewById(R.id.processing_progress_bar);

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

        String transactionCode = generateTransactionCode(currentDate);
        transaction.setText(transactionCode);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String userUId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference userItemsRef = databaseReference.child("users").child(userUId).child("your packages").push(); // Create a unique key for each item

                userItemsRef.child("item").setValue(itemType, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            userItemsRef.child("description").setValue(description, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError != null) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                    } else {
                                        userItemsRef.child("fromLocation").setValue(fromLocation, new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                if (databaseError != null) {
                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                                } else {
                                                    userItemsRef.child("toLocation").setValue(toLocation, new DatabaseReference.CompletionListener() {
                                                        @Override
                                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                            if (databaseError != null) {
                                                                progressBar.setVisibility(View.GONE);
                                                                Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                                            } else {
                                                                userItemsRef.child("senderPhoneNo").setValue(phoneSender, new DatabaseReference.CompletionListener() {
                                                                    @Override
                                                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                                        if (databaseError != null) {
                                                                            progressBar.setVisibility(View.GONE);
                                                                            Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                                                        } else {
                                                                            userItemsRef.child("receiverPhoneNo").setValue(phoneReceiver, new DatabaseReference.CompletionListener() {
                                                                                @Override
                                                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                                                    if (databaseError != null) {
                                                                                        progressBar.setVisibility(View.GONE);
                                                                                        Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                                                                    } else {
                                                                                        userItemsRef.child("date").setValue(currentDate, new DatabaseReference.CompletionListener() {
                                                                                            @Override
                                                                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                                                                if (databaseError != null) {
                                                                                                    progressBar.setVisibility(View.GONE);
                                                                                                    Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                                                                                } else {
                                                                                                    userItemsRef.child("time").setValue(currentTime, new DatabaseReference.CompletionListener() {
                                                                                                        @Override
                                                                                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                                                                            if (databaseError != null) {
                                                                                                                progressBar.setVisibility(View.GONE);
                                                                                                                Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                                                                                            } else {
                                                                                                                userItemsRef.child("transactionCode").setValue(transactionCode, new DatabaseReference.CompletionListener() {
                                                                                                                    @Override
                                                                                                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                                                                                        if (databaseError != null) {
                                                                                                                            progressBar.setVisibility(View.GONE);
                                                                                                                            Toast.makeText(Process.this, "Failed to enter details: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                                                                                                        } else {
                                                                                                                            progressBar.setVisibility(View.GONE);
                                                                                                                            Toast.makeText(Process.this, "Details have been entered successfully", Toast.LENGTH_LONG).show();
                                                                                                                            startActivity(new Intent(Process.this, Customer.class));
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

    private String generateTransactionCode(String currentDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = dateFormat.parse(currentDate);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String yearCode = String.valueOf(year).substring(2); // Last two digits of the year

        char monthCode = (char) ('A' + month); // A for January, B for February, etc.

        char dayCode;
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                dayCode = 'M';
                break;
            case Calendar.TUESDAY:
                dayCode = 'N';
                break;
            case Calendar.WEDNESDAY:
                dayCode = 'O';
                break;
            case Calendar.THURSDAY:
                dayCode = 'P';
                break;
            case Calendar.FRIDAY:
                dayCode = 'Q';
                break;
            case Calendar.SATURDAY:
                dayCode = 'R';
                break;
            case Calendar.SUNDAY:
                dayCode = 'S';
                break;
            default:
                dayCode = 'X'; // Unknown day
                break;
        }

        return monthCode + yearCode + dayCode;
    }
}