package com.example.smartattendanceapp;

public class AttendanceModel {

    String studentId;
    String subjectId;
    String date;
    String status;

    public AttendanceModel() {
    }

    public AttendanceModel(String studentId,
                           String subjectId,
                           String date,
                           String status) {

        this.studentId = studentId;
        this.subjectId = subjectId;
        this.date = date;
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}