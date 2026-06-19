package com.example.smartattendanceapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class QRActivity extends AppCompatActivity {

    Spinner spinnerSubjects;
    Button btnGenerate;
    ImageView imgQR;

    String[] subjects = {
            "cs601",
            "cs602",
            "cs603"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        spinnerSubjects =
                findViewById(R.id.spinnerSubjects);

        btnGenerate =
                findViewById(R.id.btnGenerate);

        imgQR =
                findViewById(R.id.imgQR);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        subjects);

        spinnerSubjects.setAdapter(adapter);

        btnGenerate.setOnClickListener(v -> generateQR());
    }

    private void generateQR() {

        String subject =
                spinnerSubjects.getSelectedItem().toString();

        String date =
                new SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault())
                        .format(new Date());

        String teacherId =
                FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getUid();

        String qrData =
                subject + "|" + date + "|" + teacherId;

        QRCodeWriter writer = new QRCodeWriter();

        try {

            BitMatrix bitMatrix =
                    writer.encode(
                            qrData,
                            BarcodeFormat.QR_CODE,
                            600,
                            600);

            Bitmap bitmap =
                    Bitmap.createBitmap(
                            600,
                            600,
                            Bitmap.Config.RGB_565);

            for (int x = 0; x < 600; x++) {

                for (int y = 0; y < 600; y++) {

                    bitmap.setPixel(
                            x,
                            y,
                            bitMatrix.get(x, y)
                                    ? android.graphics.Color.BLACK
                                    : android.graphics.Color.WHITE);
                }
            }

            imgQR.setImageBitmap(bitmap);

        } catch (WriterException e) {

            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}