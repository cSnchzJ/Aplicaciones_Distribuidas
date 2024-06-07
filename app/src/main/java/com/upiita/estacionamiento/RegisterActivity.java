package com.upiita.estacionamiento;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    private EditText email, phone, vehicleRegistration, password;
    private RadioGroup userTypeGroup;
    private RadioButton student, teacher;
    private Button finishRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth and Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize views
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        vehicleRegistration = findViewById(R.id.vehicleRegistration);
        password = findViewById(R.id.password);
        userTypeGroup = findViewById(R.id.userTypeGroup);
        student = findViewById(R.id.student);
        teacher = findViewById(R.id.teacher);
        finishRegister = findViewById(R.id.finishRegister);

        finishRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String emailText = email.getText().toString().trim();
        String phoneText = phone.getText().toString().trim();
        String vehicleText = vehicleRegistration.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        int selectedId = userTypeGroup.getCheckedRadioButtonId();
        String userType = "";
        if (selectedId == student.getId()) {
            userType = "Alumno";
        } else if (selectedId == teacher.getId()) {
            userType = "Docente";
        }

        if (TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText)) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String finalUserType = userType;
        firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, save user data to Firestore
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            Map<String, Object> user = new HashMap<>();
                            user.put("email", emailText);
                            user.put("phone", phoneText);
                            user.put("vehicleRegistration", vehicleText);
                            user.put("userType", finalUserType);

                            firestore.collection("users").document(userId)
                                    .set(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(RegisterActivity.this, UserProfileActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Error al registrar usuario en Firestore", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Error de autenticación: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }
}
