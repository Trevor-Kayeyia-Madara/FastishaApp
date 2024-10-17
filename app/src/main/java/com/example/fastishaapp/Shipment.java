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

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.events.MapEventsReceiver;

import java.io.File;
import java.io.IOException;
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
    Marker startMarker;  // Marker for current location
    Marker destinationMarker;  // Marker for tapped location

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

        // Add tap listener to the map to place a marker on tap
        addTapListener();
    }

    // Method to add tap listener to the map
    private void addTapListener() {
        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                // If there's already a destination marker, remove it
                if (destinationMarker != null) {
                    mapView.getOverlays().remove(destinationMarker);
                }

                // Reverse geocode to get the address of the tapped location
                Geocoder geocoder = new Geocoder(Shipment.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(p.getLatitude(), p.getLongitude(), 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        String address = addresses.get(0).getAddressLine(0);

                        // Create and add a new marker at the tapped location
                        destinationMarker = new Marker(mapView);
                        destinationMarker.setPosition(p);
                        destinationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                        destinationMarker.setTitle(address);  // Set the address as the marker's title

                        // Set the blue icon for the destination marker
                        destinationMarker.setIcon(getResources().getDrawable(R.drawable.ic_marker_blue));

                        mapView.getOverlays().add(destinationMarker);
                        mapController.setCenter(p);  // Center map to tapped location
                        mapView.invalidate();  // Refresh the map to show the marker

                        // Update destination field with the address
                        destination.setText(address);
                    } else {
                        // Handle case where no address was found
                        Toast.makeText(Shipment.this, "Address not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Shipment.this, "Error fetching address", Toast.LENGTH_SHORT).show();
                }

                return true;  // Return true to indicate event is handled
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };

        // Add MapEventsOverlay to the map
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(mapEventsReceiver);
        mapView.getOverlays().add(mapEventsOverlay);
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
                            startMarker = new Marker(mapView);
                            startMarker.setPosition(startPoint);
                            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                            startMarker.setTitle("My Location");

                            // Set the red icon for the current location marker
                            startMarker.setIcon(getResources().getDrawable(R.drawable.ic_marker_red));

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
        if (myLocation.getText().toString().isEmpty()) {
            myLocation.setError("Location is required");
            return;
        }

        if (destination.getText().toString().isEmpty()) {
            destination.setError("Destination is required");
            return;
        }

        if (productName.getText().toString().isEmpty()) {
            productName.setError("Product name is required");
            return;
        }

        if (productWeight.getText().toString().isEmpty()) {
            productWeight.setError("Product weight is required");
            return;
        }

        if (productDetail.getText().toString().isEmpty()) {
            productDetail.setError("Product details are required");
            return;
        }

        // Calculate price based on weight
        double weight = Double.parseDouble(productWeightText);
        double price = calculatePrice(weight);

        // Get the selected date from the DatePicker
        String selectedDate = date.getDayOfMonth() + "/" + (date.getMonth() + 1) + "/" + date.getYear();

        // Start the next activity and pass the data
        Intent intent = new Intent(Shipment.this, PaymentConfirmation.class);
        intent.putExtra("location", locationText);
        intent.putExtra("destination", destinationText);
        intent.putExtra("productName", productNameText);
        intent.putExtra("productWeight", productWeightText);
        intent.putExtra("productDetail", productDetailText);
        intent.putExtra("price", String.valueOf(price));
        intent.putExtra("date", selectedDate);

        startActivity(intent);
    }

    // Method to calculate price based on weight
    private double calculatePrice(double weight) {
        return weight * 100.0; // Example price calculation: 100 per kg
    }

    // Handle permission result for location access
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                // Permission denied
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
