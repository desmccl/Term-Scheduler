package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessments {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String assessmentName;
    private int courseID;

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    private String assessmentType;

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public int getCourseID() {
        return courseID;
    }

    public Assessments() {
    }

    public Assessments(int assessmentID, String assessmentName, int courseID, String assessmentType) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.courseID = courseID;
        this.assessmentType = assessmentType;
    }
}
