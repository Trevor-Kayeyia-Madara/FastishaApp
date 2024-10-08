package com.example.fastishaapp;

import android.os.Bundle;
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

public class PackageListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PackageAdapter packageAdapter; // Remove generic type here
    private List<PackageData> packageDataList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Correct method signature
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        packageDataList = new ArrayList<>();
        packageAdapter = new PackageAdapter(packageDataList);
        recyclerView.setAdapter(packageAdapter);

        String userUId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userUId).child("your packages");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                packageDataList.clear(); // Clear the old list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PackageData packageData = snapshot.getValue(PackageData.class);
                    if (packageData != null) {
                        packageDataList.add(packageData); // Ensure packageData is not null
                    }
                }
                packageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
            }
        });
    }
}
