package com.example.fastishaapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PaymentFragment extends Fragment {

    RecyclerView recyclerView;
    ItemAdapter itemAdapter;
    List<MainModel> itemList; // To hold the data

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.myRecyleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the list and adapter
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);

        // Assuming you have a method to fetch data from Firebase
        fetchItemsFromFirebase();
    }

    private void fetchItemsFromFirebase() {
        // Replace with your Firebase Database reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("your_node_here");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemList.clear(); // Clear previous data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MainModel item = snapshot.getValue(MainModel.class);
                    itemList.add(item); // Add the item to the list
                }
                itemAdapter.notifyDataSetChanged(); // Refresh RecyclerView
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle possible errors
            }
        });
    }
}
