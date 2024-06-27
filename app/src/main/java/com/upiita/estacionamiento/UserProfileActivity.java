package com.upiita.estacionamiento;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    private TextView userEmail, emailTextView, phoneTextView, vehicleTextView, userTypeTextView;
    private LinearLayout userDetailsLayout;
    private Button slotA3, slot6c, slotL8, modifyFieldsButton;
    private TextView accountSettings, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize Firebase Auth and Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize views
        userEmail = findViewById(R.id.user_email);
        emailTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        vehicleTextView = findViewById(R.id.vehicleTextView);
        userTypeTextView = findViewById(R.id.userTypeTextView);
        userDetailsLayout = findViewById(R.id.user_details_layout);
        slotA3 = findViewById(R.id.slot_a3);
        slot6c = findViewById(R.id.slot_6c);
        slotL8 = findViewById(R.id.slot_l8);
        modifyFieldsButton = findViewById(R.id.modify_fields_button);
        accountSettings = findViewById(R.id.account_settings);
        logout = findViewById(R.id.logout);

        // Load user data
        loadUserEmail();
        loadAvailableSlots();

        accountSettings.setOnClickListener(v -> toggleUserDetailsVisibility());
        logout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        modifyFieldsButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, ModifyUserActivity.class);
            startActivity(intent);
        });
    }

    private void loadUserEmail() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            userEmail.setText(user.getEmail());
        }
    }

    private void toggleUserDetailsVisibility() {
        if (userDetailsLayout.getVisibility() == View.GONE) {
            userDetailsLayout.setVisibility(View.VISIBLE);
            loadUserData();
        } else {
            userDetailsLayout.setVisibility(View.GONE);
        }
    }

    private void loadUserData() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DocumentReference docRef = firestore.collection("users").document(userId);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            emailTextView.setText("Email: " + document.getString("email"));
                            phoneTextView.setText("Phone: " + document.getString("phone"));
                            vehicleTextView.setText("Vehicle Registration: " + document.getString("vehicleRegistration"));
                            userTypeTextView.setText("User Type: " + document.getString("userType"));
                        } else {
                            Toast.makeText(UserProfileActivity.this, "No such document", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UserProfileActivity.this, "Error fetching document: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void loadAvailableSlots() {
        firestore.collection("parkingSlots").whereEqualTo("occupied", false).limit(3).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int availableSlotCount = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String slotId = document.getId();
                                availableSlotCount++;
                                if (availableSlotCount == 1) {
                                    slotA3.setText(slotId);
                                    slotA3.setVisibility(View.VISIBLE);
                                    slotA3.setOnClickListener(v -> selectParkingSlot(slotA3));
                                } else if (availableSlotCount == 2) {
                                    slot6c.setText(slotId);
                                    slot6c.setVisibility(View.VISIBLE);
                                    slot6c.setOnClickListener(v -> selectParkingSlot(slot6c));
                                } else if (availableSlotCount == 3) {
                                    slotL8.setText(slotId);
                                    slotL8.setVisibility(View.VISIBLE);
                                    slotL8.setOnClickListener(v -> selectParkingSlot(slotL8));
                                }
                            }
                            // Hide any remaining slots that are not available
                            if (availableSlotCount < 3) {
                                if (availableSlotCount < 1) {
                                    slotA3.setVisibility(View.GONE);
                                }
                                if (availableSlotCount < 2) {
                                    slot6c.setVisibility(View.GONE);
                                }
                                if (availableSlotCount < 3) {
                                    slotL8.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            Toast.makeText(UserProfileActivity.this, "Error fetching slots: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void selectParkingSlot(Button slotButton) {
        String slotId = slotButton.getText().toString();
        slotButton.setBackgroundColor(Color.YELLOW);
        new Handler().postDelayed(() -> slotButton.setBackgroundColor(Color.RED), 180000); // 3 minutes in milliseconds
        showToast("Has seleccionado la casilla " + slotId);

        // Update Firestore
        firestore.collection("parkingSlots").document(slotId)
                .update("occupied", true)
                .addOnSuccessListener(aVoid -> showToast("Casilla actualizada"))
                .addOnFailureListener(e -> showToast("Error al actualizar la casilla"));

        // Pass selected slot information to ParkingLotActivity
        Intent intent = new Intent(UserProfileActivity.this, ParkingLotActivity.class);
        intent.putExtra("selectedSlot", slotId);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}