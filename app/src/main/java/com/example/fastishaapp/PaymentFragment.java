package com.example.fastishaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log; // Importing Log for error logging
import android.widget.ProgressBar;

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

public class PaymentFragment extends Fragment {

    private RecyclerView recyclerView; // RecyclerView to display items
    private ItemAdapter itemAdapter; // Adapter for the RecyclerView
    private List<MainModel> itemList; // List to hold item data
    private ProgressBar progressBar; // ProgressBar to show loading state

    public PaymentFragment() {
        // Required empty public constructor
    }

    // Factory method to create a new instance of PaymentFragment
    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components
        recyclerView = view.findViewById(R.id.rv); // RecyclerView
        progressBar = view.findViewById(R.id.progressBar); // ProgressBar

        // Set up RecyclerView with LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the list and adapter
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemList); // Create adapter with itemList
        recyclerView.setAdapter(itemAdapter); // Set the adapter to RecyclerView

        // Fetch data from Firebase
        fetchItemsFromFirebase();
    }

    private void fetchItemsFromFirebase() {
        FirebaseAuth auth = FirebaseAuth.getInstance(); // Get FirebaseAuth instance

        // Check if user is logged in
        if (auth.getCurrentUser() == null) {
            Log.e("PaymentFragment", "User not logged in.");
            return; // Exit if no user is logged in
        }


        String userUId = auth.getCurrentUser().getUid(); // Get current user's UID
        // Reference to the Firebase database for the user's packages
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child("your_packages");

        // Show ProgressBar while loading data
        progressBar.setVisibility(View.VISIBLE);

        // Add a ValueEventListener to listen for data changes
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //itemList.clear(); // Clear previous data

                // Loop through each child in the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MainModel item = snapshot.getValue(MainModel.class); // Convert snapshot to MainModel
                    if (item != null) {
                        itemList.add(item); // Add item to the list only if it's not null
                    } else {
                        Log.e("PaymentFragment", "Snapshot is null for: " + snapshot.getKey()); // Log error if item is null
                    }
                }

                // Notify adapter of data change and update UI
                itemAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE); // Hide ProgressBar
                //recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView when data is ready
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("PaymentFragment", "Database error: " + databaseError.getMessage()); // Log any database error
                progressBar.setVisibility(View.GONE); // Hide progress bar if there's an error
            }
        });
    }
}
