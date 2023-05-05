package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Courses {
    @PrimaryKey(autoGenerate = true)
    private int courseID;

    public int getNotesID() {
        return notesID;
    }

    public void setNotesID(int notesID) {
        this.notesID = notesID;
    }

    private int notesID;

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    private String courseStatus;
    private String courseName;
    private int termID;

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    private String mentorName;
    private String phoneNumber;
    private  String email;
    private int mentorID;

    public Courses(int courseID, String courseName, int termID, String courseStatus,int mentorID, String mentorName, String phoneNumber, String email) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.termID = termID;
        this.courseStatus = courseStatus;
        this.mentorID = mentorID;
        this.mentorName = mentorName;
        this.phoneNumber = phoneNumber;
        this.email = email;

    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getTermID() {
        return termID;
    }

    public Courses() {
    }
}
