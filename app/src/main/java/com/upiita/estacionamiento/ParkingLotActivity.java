package com.upiita.estacionamiento;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ParkingLotActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    private TextView userEmail;
    private TableLayout parkingTable;
    private TextView logout;

    private Map<String, Button> slotButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lot);

        // Initialize Firebase Auth and Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize views
        userEmail = findViewById(R.id.user_email);
        parkingTable = findViewById(R.id.parking_table);
        logout = findViewById(R.id.logout);

        // Initialize map for slot buttons
        slotButtons = new HashMap<>();
        initializeSlotButtons();

        // Load user email and parking slots
        loadUserEmail();
        loadParkingSlots();

        // Logout button click listener
        logout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            Intent intent = new Intent(ParkingLotActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Initialize slot buttons from XML
    private void initializeSlotButtons() {
        for (int i = 0; i < parkingTable.getChildCount(); i++) {
            View row = parkingTable.getChildAt(i);
            if (row instanceof TableRow) {
                TableRow tableRow = (TableRow) row;
                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    View cell = tableRow.getChildAt(j);
                    if (cell instanceof Button) {
                        Button slotButton = (Button) cell;
                        slotButtons.put(slotButton.getText().toString(), slotButton);
                        slotButton.setOnClickListener(v -> selectParkingSlot(slotButton));
                    }
                }
            }
        }
    }

    // Load current user's email
    private void loadUserEmail() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            userEmail.setText(user.getEmail());
        }
    }

    // Load parking slots from Firestore
    private void loadParkingSlots() {
        firestore.collection("parkingSlots").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String slotId = document.getId();
                                boolean occupied = document.getBoolean("occupied");
                                Button slotButton = slotButtons.get(slotId);
                                if (slotButton != null) {
                                    if (occupied) {
                                        slotButton.setBackgroundColor(Color.RED);
                                        slotButton.setEnabled(false);
                                    } else {
                                        slotButton.setBackgroundColor(Color.GREEN);
                                        slotButton.setEnabled(true);
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(ParkingLotActivity.this, "Error fetching slots: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Handle slot button click
    private void selectParkingSlot(Button slotButton) {
        String slotId = slotButton.getText().toString();
        slotButton.setBackgroundColor(Color.YELLOW);
        new Handler().postDelayed(() -> slotButton.setBackgroundColor(Color.RED), 180000); // 3 minutes in milliseconds
        showToast("Has seleccionado la casilla " + slotId);

        // Update Firestore to mark slot as occupied
        firestore.collection("parkingSlots").document(slotId)
                .update("occupied", true)
                .addOnSuccessListener(aVoid -> showToast("Casilla actualizada"))
                .addOnFailureListener(e -> showToast("Error al actualizar la casilla"));

        // Update button state
        slotButton.setEnabled(false);
    }

    // Show a short toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}