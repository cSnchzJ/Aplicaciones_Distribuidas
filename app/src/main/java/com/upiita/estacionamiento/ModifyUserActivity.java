package com.upiita.estacionamiento;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class ModifyUserActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    private EditText editPhone, editVehicleRegistration;
    private Spinner spinnerUserType;
    private Button saveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);

        // Initialize Firebase Auth and Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize views
        editPhone = findViewById(R.id.edit_phone);
        editVehicleRegistration = findViewById(R.id.edit_vehicle_registration);
        spinnerUserType = findViewById(R.id.spinner_user_type);
        saveButton = findViewById(R.id.save_button);
        cancelButton = findViewById(R.id.cancel_button);

        // Set up spinner for user type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(adapter);

        // Load existing user data
        loadUserData();

        // Set listeners for buttons
        saveButton.setOnClickListener(v -> saveUserData());
        cancelButton.setOnClickListener(v -> finish());
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
                        if (document.exists()) {
                            editPhone.setText(document.getString("phone"));
                            editVehicleRegistration.setText(document.getString("vehicleRegistration"));

                            // Set user type spinner selection
                            String userType = document.getString("userType");
                            if (userType != null) {
                                int position = getUserTypePosition(userType);
                                spinnerUserType.setSelection(position);
                            }
                        } else {
                            Toast.makeText(ModifyUserActivity.this, "No such document", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ModifyUserActivity.this, "get failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void saveUserData() {
        String phone = editPhone.getText().toString().trim();
        String vehicleRegistration = editVehicleRegistration.getText().toString().trim();
        String userType = spinnerUserType.getSelectedItem().toString().trim().toLowerCase(); // Convert to lowercase for case insensitivity

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(vehicleRegistration)) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!userType.equals("alumno") && !userType.equals("docente")) {
            Toast.makeText(this, "El tipo de usuario debe ser 'alumno' o 'docente'", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DocumentReference docRef = firestore.collection("users").document(userId);

            Map<String, Object> userData = new HashMap<>();
            userData.put("phone", phone);
            userData.put("vehicleRegistration", vehicleRegistration);
            userData.put("userType", userType);

            docRef.update(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ModifyUserActivity.this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ModifyUserActivity.this, "Error al guardar datos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // Helper method to get spinner position for user type
    private int getUserTypePosition(String userType) {
        switch (userType) {
            case "alumno":
                return 0;
            case "docente":
                return 1;
            default:
                return 0; // Default to alumno if userType is not recognized
        }
    }
}