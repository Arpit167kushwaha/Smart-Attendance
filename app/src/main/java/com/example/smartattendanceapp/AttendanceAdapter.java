package com.example.smartattendanceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AttendanceAdapter
        extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    ArrayList<AttendanceModel> attendanceList;

    public AttendanceAdapter(ArrayList<AttendanceModel> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        AttendanceModel model = attendanceList.get(position);

        holder.tvStudentId.setText(
                "Student ID : " + model.getStudentId());

        holder.tvSubjectId.setText(
                "Subject : " + model.getSubjectId());

        holder.tvDate.setText(
                "Date : " + model.getDate());

        holder.tvStatus.setText(
                "Status : " + model.getStatus());


        if (model.getStatus().equalsIgnoreCase("Present")) {
            holder.tvStatus.setTextColor(
                    android.graphics.Color.parseColor("#2E7D32"));
        } else {
            holder.tvStatus.setTextColor(
                    android.graphics.Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvStudentId,
                tvSubjectId,
                tvDate,
                tvStatus;

        CardView cardAttendance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStudentId =
                    itemView.findViewById(R.id.tvStudentId);

            tvSubjectId =
                    itemView.findViewById(R.id.tvSubject);

            tvDate =
                    itemView.findViewById(R.id.tvDate);

            tvStatus =
                    itemView.findViewById(R.id.tvStatus);

            cardAttendance =
                    itemView.findViewById(R.id.cardAttendance);
        }
    }
}