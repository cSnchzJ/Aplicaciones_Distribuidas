package com.upiita.estacionamiento;
//b
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordTextView;
    private Button registerButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Inicialización de los componentes de la interfaz de usuario
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        forgotPasswordTextView = findViewById(R.id.forgotPassword);
        registerButton = findViewById(R.id.register);

        // Listener para el botón de inicio de sesión
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor ingrese email y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Autenticación con Firebase
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Inicio de sesión exitoso
                                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Si el inicio de sesión falla
                                    Toast.makeText(MainActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Listener para el texto de olvidó contraseña
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar la lógica para recuperar contraseña
                Toast.makeText(MainActivity.this, "Recuperar contraseña", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener para el botón de registro
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
