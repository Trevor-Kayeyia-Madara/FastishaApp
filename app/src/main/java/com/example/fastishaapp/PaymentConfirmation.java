package com.example.fastishaapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentConfirmation extends AppCompatActivity {
    TextView productNameText, destinationText, weightText, priceText, currentDate, myCurrentLocation, taxTextView; // Renamed variable for clarity
    Button doneButton;

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
        taxTextView = findViewById(R.id.Tax); // Renamed variable for clarity

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
        taxTextView.setText("Tax Fees: Sh. " + calculatedTax); // Use the renamed variable
        doneButton.setText("Payment Sh. " + finalAmount);
    }
}
