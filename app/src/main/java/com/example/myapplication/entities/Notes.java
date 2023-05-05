package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")

public class Notes {
    @PrimaryKey(autoGenerate = true)

    private int notesID;
    private String noteName;

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    private int courseID;

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getNotesID() {
        return notesID;
    }

    public void setNotesID(int notesID) {
        this.notesID = notesID;
    }


    public Notes() {
    }

    public Notes (int notesID, int courseID, String noteName) {
        this.notesID = notesID;
        this.courseID = courseID;
        this.noteName = noteName;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "notesID=" + notesID +
                '}';
    }
}