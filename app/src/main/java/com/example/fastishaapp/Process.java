package com.example.fastishaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Process extends AppCompatActivity {

    TextView itemTypeTextView, descriptionTextView, fromLocationTextView, toLocationTextView, senderPhoneNo, receiverPhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        itemTypeTextView = findViewById(R.id.itemTypeTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        fromLocationTextView = findViewById(R.id.itemFromLocation);
        toLocationTextView = findViewById(R.id.itemToLocation);
        senderPhoneNo = findViewById(R.id.sendPhoneNo);
        receiverPhoneNo = findViewById(R.id.receivePhoneNo);

        Intent intent = getIntent();
        String itemType = intent.getStringExtra("itemType");
        String description = intent.getStringExtra("description");
        String fromLocation = intent.getStringExtra("fromLocation");
        String toLocation = intent.getStringExtra("toLocation");
        String phoneSender = intent.getStringExtra("sPhoneNo");
        String phoneReceiver = intent.getStringExtra("rPhoneNo");

        itemTypeTextView.setText(itemType);
        descriptionTextView.setText(description);
        fromLocationTextView.setText(fromLocation);
        toLocationTextView.setText(toLocation);
        senderPhoneNo.setText(phoneSender);
        receiverPhoneNo.setText(phoneReceiver);

    }
}
