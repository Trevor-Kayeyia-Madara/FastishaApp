package com.example.fastishaapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DeliveryAdapter deliveryAdapter;
    private List<Delivery> deliveryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        deliveryList = new ArrayList<>();
        deliveryAdapter = new DeliveryAdapter(deliveryList);
        recyclerView.setAdapter(deliveryAdapter);

        fetchDeliveryData();
    }

    private void fetchDeliveryData() {
        String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userUID).child("your_packages");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    deliveryList.clear(); // Clear the list before adding new items
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Delivery delivery = dataSnapshot.getValue(Delivery.class);
                        deliveryList.add(delivery);
                    }
                    deliveryAdapter.notifyDataSetChanged(); // Notify the adapter that data has changed
                } else {
                    Toast.makeText(History.this, "No delivery data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("History", "Database Error: " + error.getMessage());
                Toast.makeText(History.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
