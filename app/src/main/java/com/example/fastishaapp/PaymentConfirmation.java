package com.example.fastishaapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentConfirmation extends AppCompatActivity {
    TextView productNameText, destinationText, weightText, priceText, currentDate, myCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        // Get references to the TextViews
        productNameText = findViewById(R.id.productNameText);
        destinationText = findViewById(R.id.destinationText);
        weightText = findViewById(R.id.weightText);
        priceText = findViewById(R.id.priceText);
        currentDate = findViewById(R.id.currentDateText);
        myCurrentLocation = findViewById(R.id.myLocationText);

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
    }
}
