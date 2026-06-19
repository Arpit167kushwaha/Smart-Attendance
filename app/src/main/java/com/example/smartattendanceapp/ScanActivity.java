package com.example.smartattendanceapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.HashMap;
import java.util.Map;

public class ScanActivity extends AppCompatActivity {

    TextView tvScanStatus;
    Button btnScanQR;

    FirebaseFirestore db;
    FirebaseAuth auth;

    ActivityResultLauncher<ScanOptions> qrLauncher =
            registerForActivityResult(
                    new ScanContract(),
                    result -> {

                        if (result.getContents() == null) {

                            Toast.makeText(
                                    this,
                                    "Scan Cancelled",
                                    Toast.LENGTH_SHORT).show();

                            return;
                        }

                        saveAttendance(result.getContents());
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        tvScanStatus =
                findViewById(R.id.tvScanStatus);

        btnScanQR =
                findViewById(R.id.btnScanQR);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        btnScanQR.setOnClickListener(v -> startScan());
    }

    private void startScan() {

        ScanOptions options = new ScanOptions();

        options.setPrompt("Scan Teacher QR");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);

        qrLauncher.launch(options);
    }

    private void saveAttendance(String qrData) {

        String[] parts = qrData.split("\\|");

        if (parts.length != 3) {

            Toast.makeText(
                    this,
                    "Invalid QR",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        String subjectId = parts[0];
        String date = parts[1];
        String teacherId = parts[2];

        String studentId =
                auth.getCurrentUser().getUid();

        String documentId =
                studentId + "_" + date;

        db.collection("attendance")
                .document(subjectId)
                .collection("records")
                .document(documentId)
                .get()
                .addOnSuccessListener(snapshot -> {

                    if (snapshot.exists()) {

                        tvScanStatus.setText(
                                "Attendance Already Marked Today");

                        return;
                    }

                    Map<String, Object> data =
                            new HashMap<>();

                    data.put("studentId", studentId);
                    data.put("teacherId", teacherId);
                    data.put("subjectId", subjectId);
                    data.put("date", date);
                    data.put("status", "Present");
                    data.put("timestamp",
                            System.currentTimeMillis());

                    db.collection("attendance")
                            .document(subjectId)
                            .collection("records")
                            .document(documentId)
                            .set(data)
                            .addOnSuccessListener(unused -> {

                                tvScanStatus.setText(
                                        "Attendance Marked Successfully ✔");
                            });
                });
    }
}