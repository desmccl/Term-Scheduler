package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String termName;

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public int getTermID() {
        return termID;
    }

    public String getTermName() {
        return termName;
    }

    public Term() {
    }

    public Term(int termID, String termName) {
        this.termID = termID;
        this.termName = termName;
    }


}
