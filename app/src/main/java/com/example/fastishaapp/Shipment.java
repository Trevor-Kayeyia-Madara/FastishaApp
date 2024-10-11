package com.example.fastishaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Shipment extends AppCompatActivity {
    EditText myLoaction, destination, productName, productWeight, productDetail;
    DatePicker date;
    Button submit;
    ImageView back;

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
        submit = findViewById(R.id.signUpButton);

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

    }
}