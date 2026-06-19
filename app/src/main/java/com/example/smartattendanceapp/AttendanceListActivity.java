package com.example.smartattendanceapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

public class AttendanceListActivity
        extends AppCompatActivity {

    RecyclerView recyclerAttendance;

    TextView tvEmpty;

    ArrayList<AttendanceModel> list;

    AttendanceAdapter adapter;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(
                R.layout.activity_attendance_list);

        tvEmpty = findViewById(R.id.tvEmpty);

        recyclerAttendance =
                findViewById(R.id.recyclerAttendance);

        recyclerAttendance.setLayoutManager(
                new LinearLayoutManager(this));

        list = new ArrayList<>();

        adapter = new AttendanceAdapter(list);

        recyclerAttendance.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadAttendance();
    }

    private void loadAttendance() {

        String[] subjects = {
                "cs601",
                "cs602",
                "cs603"
        };

        list.clear();

        for (String subject : subjects) {

            db.collection("attendance")
                    .document(subject)
                    .collection("records")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {

                        for (QueryDocumentSnapshot doc
                                : queryDocumentSnapshots) {

                            AttendanceModel model =
                                    doc.toObject(
                                            AttendanceModel.class);

                            list.add(model);
                        }

                        adapter.notifyDataSetChanged();

                        if (list.isEmpty()) {

                            tvEmpty.setVisibility(View.VISIBLE);

                        } else {

                            tvEmpty.setVisibility(View.GONE);
                        }
                    });
        }
    }
}