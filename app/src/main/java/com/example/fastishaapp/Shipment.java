package com.example.fastishaapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Shipment extends AppCompatActivity {
    EditText myLoaction, destination, productName, productWeight, productDetail;
    DatePicker date;
    Button submit;
    ImageView back;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shipment);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        back = findViewById(R.id.back);
        myLoaction = findViewById(R.id.myLocation);
        destination = findViewById(R.id.destination);
        productName = findViewById(R.id.productName);
        productWeight = findViewById(R.id.productKg);
        date = findViewById(R.id.currentDate);
        productDetail = findViewById(R.id.productDetails);
        submit = findViewById(R.id.submitBtn);

        back.setOnClickListener(view -> {
            Intent intent = new Intent(Shipment.this, Login.class );
            startActivity(intent);
        });

        final String txtMyLocation = myLoaction.getText().toString();
        final String txtDestination = destination.getText().toString();
        final String txtProduct = productName.getText().toString();
        final String txtWeight = productWeight.getText().toString();
        final String txtDetail = productDetail.getText().toString();

        int day = date.getDayOfMonth();
        int month = date.getMonth() + 1;  // Month is 0-based, so add 1
        int year = date.getYear();
        final String txtDate = day + "/" + month + "/" + year;

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Location permission granted, handle location logic here if needed
        }
    }
}