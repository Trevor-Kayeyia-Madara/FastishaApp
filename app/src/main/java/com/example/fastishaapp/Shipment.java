package com.example.fastishaapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Shipment extends AppCompatActivity {

    // Declare views and variables
    EditText myLocation, destination, productName, productWeight, productDetail;
    DatePicker date;
    Button submit;
    ImageView back;
    FusedLocationProviderClient fusedLocationClient;
    TextView distance;
    MapView mapView;
    IMapController mapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment);

        // Initialize views
        back = findViewById(R.id.back);
        myLocation = findViewById(R.id.myLocation);
        destination = findViewById(R.id.destination);
        productName = findViewById(R.id.productName);
        productWeight = findViewById(R.id.productKg);
        date = findViewById(R.id.currentDate);
        productDetail = findViewById(R.id.productDetails);
        submit = findViewById(R.id.submitBtn);
        distance = findViewById(R.id.tv_distance);
        mapView = findViewById(R.id.map);

        // Initialize fused location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Back button functionality
        back.setOnClickListener(v -> finish());

        // Submit button functionality
        submit.setOnClickListener(v -> submitShipment());

        // Request location permission if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getCurrentLocation();  // Get location if permission is already granted
        }

        // Set the user agent to prevent getting banned from the OSM servers
        Configuration.getInstance().setUserAgentValue(getPackageName());

        // Set cache storage for OSM tiles
        File osmdroidCache = new File(Environment.getExternalStorageDirectory(), "osmdroid");
        Configuration.getInstance().setOsmdroidBasePath(osmdroidCache);
        Configuration.getInstance().setOsmdroidTileCache(osmdroidCache);

        // Configure map settings
        mapView.setTileSource(TileSourceFactory.MAPNIK);  // Use MAPNIK as the tile source
        mapView.setMultiTouchControls(true);
        mapController = mapView.getController();
        mapController.setZoom(15.0);  // Set default zoom level
    }

    // Get current location using FusedLocationProviderClient
    private void getCurrentLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        try {
                            // Use Geocoder to convert latitude and longitude into an address
                            Geocoder geocoder = new Geocoder(Shipment.this, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1);

                            // Display address in the myLocation field
                            if (!addresses.isEmpty()) {
                                myLocation.setText(addresses.get(0).getAddressLine(0));
                            }

                            // Add a marker at the current location on the map
                            GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                            Marker startMarker = new Marker(mapView);
                            startMarker.setPosition(startPoint);
                            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                            startMarker.setTitle("My Location");

                            mapView.getOverlays().add(startMarker);
                            mapController.setCenter(startPoint);  // Center map to current location

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    // Method to handle submission of shipment details
    private void submitShipment() {
        String locationText = myLocation.getText().toString();
        String destinationText = destination.getText().toString();
        String productNameText = productName.getText().toString();
        String productWeightText = productWeight.getText().toString();
        String productDetailText = productDetail.getText().toString();

        // Validate that all fields are filled
        if (locationText.isEmpty() || destinationText.isEmpty() ||
                productNameText.isEmpty() || productWeightText.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the selected date from DatePicker
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth(), date.getDayOfMonth());

        // Calculate price based on weight
        double weight = Double.parseDouble(productWeightText);
        double price = calculatePrice(weight); // Calculate the price

        // Prepare to send data to PaymentConfirmation activity
        Intent intent = new Intent(Shipment.this, PaymentConfirmation.class);
        intent.putExtra("myLocation", locationText); // Pass the location
        intent.putExtra("productName", productNameText);
        intent.putExtra("destination", destinationText);
        intent.putExtra("productWeight", productWeightText);
        intent.putExtra("detail", productDetailText);
        intent.putExtra("totalPrice", price); // Pass the calculated price
        intent.putExtra("date", calendar.getTimeInMillis()); // Pass the selected date as a timestamp
        startActivity(intent);

        // Show success message after submission
        Toast.makeText(this, "Shipment details submitted", Toast.LENGTH_SHORT).show();

        // Go back to the previous screen after successful submission
        finish();
    }

    // Function to calculate price based on weight (decimal)
    private double calculatePrice(double weight) {
        double pricePerKg = 100.00; // 1 Kg = 100 shillings
        return weight * pricePerKg;
    }

    // Handle the result of location permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            // If permission is granted, get the current location
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                // If permission is denied, show a message
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
