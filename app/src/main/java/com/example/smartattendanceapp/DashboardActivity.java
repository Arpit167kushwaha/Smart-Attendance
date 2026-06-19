package com.example.smartattendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    Button teacherBtn,
            studentBtn,
            btnViewAttendance,
            btnLogout;
            TextView tvWelcome;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        teacherBtn = findViewById(R.id.teacherBtn);
        studentBtn = findViewById(R.id.studentBtn);
        btnViewAttendance = findViewById(R.id.btnViewAttendance);
        btnLogout = findViewById(R.id.btnLogout);

        teacherBtn.setOnClickListener(v -> {

            startActivity(new Intent(
                    DashboardActivity.this,
                    QRActivity.class));
        });

        studentBtn.setOnClickListener(v -> {

            startActivity(new Intent(
                    DashboardActivity.this,
                    ScanActivity.class));
        });

        btnViewAttendance.setOnClickListener(v -> {

            startActivity(new Intent(
                    DashboardActivity.this,
                    AttendanceListActivity.class));
        });

        btnLogout.setOnClickListener(v -> {

            auth.signOut();

            Intent intent = new Intent(
                    DashboardActivity.this,
                    LoginActivity.class);

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish();
        });
        loadUserRole();
    }
    private void loadUserRole() {

        String uid =
                auth.getCurrentUser().getUid();

        db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {

                    if (!documentSnapshot.exists())
                        return;

                    String role =
                            documentSnapshot.getString("role");

                    String name =
                            documentSnapshot.getString("name");

                    tvWelcome.setText("Welcome, " + name);

                    if (role == null)
                        return;

                    if (role.equals("teacher")) {

                        teacherBtn.setVisibility(View.VISIBLE);

                        btnViewAttendance.setVisibility(
                                View.VISIBLE);

                        studentBtn.setVisibility(View.GONE);

                    }

                    else if (role.equals("student")) {

                        studentBtn.setVisibility(View.VISIBLE);

                        teacherBtn.setVisibility(View.GONE);

                        btnViewAttendance.setVisibility(
                                View.GONE);
                    }
                });
    }
}