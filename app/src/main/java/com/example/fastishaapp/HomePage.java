package com.example.fastishaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomePage extends AppCompatActivity {
    LinearLayout shipmentLayer, historyLayer, accountLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        if (!sessionManager.isLoggedIn()) {
            // Redirect to Login if not logged in
            Intent intent = new Intent(HomePage.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        shipmentLayer = findViewById(R.id.shipmentLayer);
        historyLayer = findViewById(R.id.historyLayer);
        accountLayer = findViewById(R.id.accountLayer);

        shipmentLayer.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, Shipment.class);
            startActivity(intent);
        });

        historyLayer.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, History.class);
            startActivity(intent);
        });

        accountLayer.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, UserProfileActivity.class);
            startActivity(intent);
        });
    }
}