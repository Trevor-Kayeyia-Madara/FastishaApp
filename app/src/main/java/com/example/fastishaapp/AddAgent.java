package com.example.fastishaapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddAgent extends AppCompatActivity {

    Button selectImage, addAgent;
    ImageView imageView;
    private static final int GALLERY_REQUEST_CODE = 1;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;
    EditText firstName, lastName, nationalID, email, phoneNumber;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_agent);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        selectImage = findViewById(R.id.select_image_button);
        imageView = findViewById(R.id.agentPicture);
        selectImage.setOnClickListener(v -> openGallery());

        addAgent = findViewById(R.id.add_agent_button);
        db = FirebaseFirestore.getInstance();
        firstName = findViewById(R.id.agentFirstName);
        lastName = findViewById(R.id.agentLastName);
        nationalID = findViewById(R.id.agentNationalID);
        email = findViewById(R.id.agentEmail);
        phoneNumber = findViewById(R.id.agentPhoneNo);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        addAgent.setOnClickListener(v -> {
            String first_name = firstName.getText().toString();
            String last_name = lastName.getText().toString();
            String national_id = nationalID.getText().toString();
            String email_address = email.getText().toString();
            String phone_number = phoneNumber.getText().toString();

            if (isValidInput(first_name, last_name, national_id, email_address, phone_number)) {
                if (imageUri != null) {
                    StorageReference imageRef = storageRef.child("images/" + System.currentTimeMillis() + ".jpg");
                    UploadTask uploadTask = imageRef.putFile(imageUri);
                    uploadTask.continueWithTask(task -> {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return imageRef.getDownloadUrl();
                    }).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String imageUrl = task.getResult().toString();
                            Agent newAgent = new Agent(first_name, last_name, national_id, email_address, phone_number, imageUrl);
                            db.collection("agents")
                                    .add(newAgent)
                                    .addOnSuccessListener(documentReference -> {
                                        Toast.makeText(AddAgent.this, "Agent added successfully", Toast.LENGTH_SHORT).show();
                                        clearFields();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(AddAgent.this, "Failed to add agent", Toast.LENGTH_SHORT).show());
                        } else {
                            Toast.makeText(AddAgent.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(AddAgent.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddAgent.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private boolean isValidInput(String firstName, String lastName, String nationalId, String email, String phone) {
        // Implement your validation logic here
        return !firstName.isEmpty() && !lastName.isEmpty() && !nationalId.isEmpty() && !email.isEmpty() && !phone.isEmpty();
    }

    private void clearFields() {
        firstName.setText("");
        lastName.setText("");
        nationalID.setText("");
        email.setText("");
        phoneNumber.setText("");
        imageView.setImageURI(null);
        imageUri = null;
    }
}
