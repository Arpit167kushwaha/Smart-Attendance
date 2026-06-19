package com.example.smartattendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;

    Button btnLogin;
    ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {

            startActivity(new Intent(
                    LoginActivity.this,
                    DashboardActivity.class));

            finish();
        }

        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {

        String email =
                etEmail.getText().toString().trim();

        String password =
                etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {

            etEmail.setError("Enter Email");
            return;
        }

        if (TextUtils.isEmpty(password)) {

            etPassword.setError("Enter Password");
            return;
        }

        btnLogin.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(
                        email,
                        password)

                .addOnSuccessListener(authResult -> {

                    btnLogin.setEnabled(true);
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(
                            LoginActivity.this,
                            "Login Successful",
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(
                            LoginActivity.this,
                            DashboardActivity.class));

                    finish();
                })

                .addOnFailureListener(e -> {

                    btnLogin.setEnabled(true);
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(
                            LoginActivity.this,
                            e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }
}