package com.upiita.estacionamiento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, phone, vehicleRegistration, institution, password;
    private RadioGroup userTypeGroup;
    private RadioButton student, teacher, admin;
    private Button finishRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        vehicleRegistration = findViewById(R.id.vehicleRegistration);
        //institution = findViewById(R.id.institution);
        password = findViewById(R.id.password);
        userTypeGroup = findViewById(R.id.userTypeGroup);
        student = findViewById(R.id.student);
        teacher = findViewById(R.id.teacher);
        //admin = findViewById(R.id.admin);
        finishRegister = findViewById(R.id.finishRegister);

        finishRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String emailText = email.getText().toString();
        String phoneText = phone.getText().toString();
        String vehicleText = vehicleRegistration.getText().toString();
        String institutionText = institution.getText().toString();
        String passwordText = password.getText().toString();

        int selectedId = userTypeGroup.getCheckedRadioButtonId();
        String userType = "";
        if (selectedId == student.getId()) {
            userType = "Alumno";
        } else if (selectedId == teacher.getId()) {
            userType = "Docente";
        } else if (selectedId == admin.getId()) {
            userType = "Admin";
        }

        // Here, add your logic to handle the registration process.
        // For example, you could send this data to a server or save it locally.

        // For demonstration, we show a toast message
        Toast.makeText(this, "Usuario registrado: " + emailText, Toast.LENGTH_SHORT).show();

        // Navigate back to MainActivity after registration
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
