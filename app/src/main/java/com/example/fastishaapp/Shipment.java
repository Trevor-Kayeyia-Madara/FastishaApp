package com.example.fastishaapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
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

        back = findViewById(R.id.back);
        myLocation = findViewById(R.id.myLocation);
        destination = findViewById(R.id.destination);
        productName = findViewById(R.id.productName);
        productWeight = findViewById(R.id.productKg);
        date = findViewById(R.id.currentDate);
        productDetail = findViewById(R.id.productDetails);
        submit = findViewById(R.id.submitBtn);

        // Initialize the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
    }

    private void fetchLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations, this can be null.
                        if (location != null) {
                            getAddressFromLocation(location);
                        } else {
                            myLocation.setText("Location not available");
                        }
                    }
                });
    }

    private void getAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            // Get address from latitude and longitude
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                // Get the exact area (street address, village, etc.)
                String preciseAddress = "";
                if (address.getThoroughfare() != null) {
                    preciseAddress += address.getThoroughfare(); // Street name
                }
                if (address.getSubLocality() != null) {
                    preciseAddress += ", " + address.getSubLocality(); // Neighborhood or Village
                }
                if (address.getLocality() != null) {
                    preciseAddress += ", " + address.getLocality(); // City/Town
                }
                if (address.getSubAdminArea() != null) {
                    preciseAddress += ", " + address.getSubAdminArea(); // Village or Sub-area (for smaller areas)
                }
                if (address.getPostalCode() != null) {
                    preciseAddress += ", " + address.getPostalCode(); // Postal Code
                }

                // Set the precise address to the myLocation EditText
                myLocation.setText(preciseAddress);
            } else {
                myLocation.setText("Address not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            myLocation.setText("Unable to get address");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Location permission granted, fetch the location
            fetchLocation();
        } else {
            // Permission denied
            myLocation.setText("Permission denied");
        }
    }
}
