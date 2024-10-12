package com.example.fastishaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentConfirmation extends AppCompatActivity {
    TextView productNameText, destinationText, weightText, priceText, currentDate, myCurrentLocation, taxTextView;
    Button doneButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        // Get references to the TextViews and Button
        productNameText = findViewById(R.id.productNameText);
        destinationText = findViewById(R.id.destinationText);
        weightText = findViewById(R.id.weightText);
        priceText = findViewById(R.id.priceText);
        currentDate = findViewById(R.id.currentDateText);
        myCurrentLocation = findViewById(R.id.myLocationText);
        doneButton = findViewById(R.id.DoneBtn);
        taxTextView = findViewById(R.id.Tax);
        progressBar = findViewById(R.id.confirmationProgress);

        // Get the data passed from the Shipment activity
        String productName = getIntent().getStringExtra("productName");
        String destination = getIntent().getStringExtra("destination");
        String weight = getIntent().getStringExtra("productWeight");
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);
        String date = getIntent().getStringExtra("date");
        String location = getIntent().getStringExtra("myLocation");

        // Set the data to the TextViews
        productNameText.setText("Product: " + productName);
        destinationText.setText("Destination: " + destination);
        weightText.setText("Weight: " + weight + " kg");
        priceText.setText("Total Price: Sh. " + totalPrice);
        currentDate.setText("Current Date: " + date);
        myCurrentLocation.setText("My Current Location: " + location);

        // Calculate tax (18% of price)
        double calculatedTax = totalPrice * 0.18;

        // Calculate final amount (price + tax)
        double finalAmount = totalPrice + calculatedTax;

        // Display tax and final amount
        taxTextView.setText("Tax Fees: Sh. " + calculatedTax);
        doneButton.setText("Payment Sh. " + finalAmount);

        // Push data to Firebase
        doneButton.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Get the current user's UID
            pushDataToFirebase(userUID, productName, destination, weight, totalPrice, calculatedTax, finalAmount, date, location);
        });
    }

    private void pushDataToFirebase(String userUID, String productName, String destination, String weight, double totalPrice, double calculatedTax, double finalAmount, String date, String location) {
        // Ensure userUID is not null
        if (userUID == null) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(PaymentConfirmation.this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a reference to the Firebase Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userUID).child("your_packages");

        // Create a Delivery object
        Delivery delivery = new Delivery(productName, destination, weight, totalPrice, calculatedTax, finalAmount, date, location);

        // Push the data to Firebase
        String deliveryId = databaseReference.push().getKey(); // Generate a unique ID
        if (deliveryId != null) {
            databaseReference.child(deliveryId).setValue(delivery).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(PaymentConfirmation.this, "Data pushed successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentConfirmation.this, HomePage.class);
                    startActivity(intent);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(PaymentConfirmation.this, "Failed to push data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
