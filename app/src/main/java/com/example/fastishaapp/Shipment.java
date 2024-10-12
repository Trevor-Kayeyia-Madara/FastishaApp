package com.example.fastishaapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Shipment extends AppCompatActivity {
    EditText myLocation, destination, productName, productWeight, productDetail;
    DatePicker date;
    Button submit;
    ImageView back;
    FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment);

        // Initialize views
        back = findViewById(R.id.back);
        myLocation = findViewById(R.id.myLocation); //todo make shure the current location is added
        destination = findViewById(R.id.destination);
        productName = findViewById(R.id.productName);
        productWeight = findViewById(R.id.productKg);
        date = findViewById(R.id.currentDate);
        productDetail = findViewById(R.id.productDetails);
        submit = findViewById(R.id.submitBtn);

        // Initialize the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Back button event handler
        back.setOnClickListener(view -> {
            Intent intent = new Intent(Shipment.this, Login.class);
            startActivity(intent);
        });

        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        } else {
            // Permission is already granted, fetch the location
            fetchLocation();
        }

        // Set OnClickListener for the submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate user inputs
                if (productName.getText().toString().isEmpty()) {
                    Toast.makeText(Shipment.this, "Please enter the product name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (destination.getText().toString().isEmpty()) {
                    Toast.makeText(Shipment.this, "Please enter a destination", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productWeight.getText().toString().isEmpty()) {
                    Toast.makeText(Shipment.this, "Please enter product weight", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convert weight to double and calculate price
                double weight = Double.parseDouble(productWeight.getText().toString());
                double price = calculatePrice(weight); // Use the price calculation

                // Get the selected date from DatePicker
                int day = date.getDayOfMonth();
                int month = date.getMonth();
                int year = date.getYear();

                // Create a formatted date string
                String selectedDate = day + "/" + (month + 1) + "/" + year;

                // Prepare to send data to PaymentConfirmation activity
                Intent intent = new Intent(Shipment.this, PaymentConfirmation.class);
                intent.putExtra("myLocation", myLocation.getText().toString()); // Pass the location
                intent.putExtra("productName", productName.getText().toString());
                intent.putExtra("destination", destination.getText().toString());
                intent.putExtra("productWeight", productWeight.getText().toString());
                intent.putExtra("totalPrice", price); // Pass the calculated price
                intent.putExtra("date", selectedDate); // Pass the selected date
                startActivity(intent);
            }
        });
    }

    // Function to calculate price based on weight (decimal)
    private double calculatePrice(double weight) {
        double pricePerKg = 100.00; // 1 Kg = 100 shillings
        return weight * pricePerKg;
    }

    // Fetches the current location of the user
    private void fetchLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            getAddressFromLocation(location);
                        } else {
                            myLocation.setText("Location not available");
                        }
                    }
                });
    }

    // Converts the location into an address string
    private void getAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String preciseAddress = "";
                if (address.getThoroughfare() != null) {
                    preciseAddress += address.getThoroughfare();
                }
                if (address.getSubLocality() != null) {
                    preciseAddress += ", " + address.getSubLocality();
                }
                if (address.getLocality() != null) {
                    preciseAddress += ", " + address.getLocality();
                }
                if (address.getSubAdminArea() != null) {
                    preciseAddress += ", " + address.getSubAdminArea();
                }
                if (address.getPostalCode() != null) {
                    preciseAddress += ", " + address.getPostalCode();
                }
                myLocation.setText(preciseAddress);
            } else {
                myLocation.setText("Address not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            myLocation.setText("Unable to get address");
        }
    }

    // Handles location permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchLocation();
        } else {
            myLocation.setText("Permission denied");
        }
    }
}
