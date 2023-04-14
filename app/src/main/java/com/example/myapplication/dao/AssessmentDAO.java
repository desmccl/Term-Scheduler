package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entities.Assessments;
import com.example.myapplication.entities.Courses;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Assessments assessments);
    @Update
    void update (Assessments assessments);
    @Delete
    void delete (Assessments assessments);
    @Query("SELECT * FROM ASSESSMENTS ORDER BY assessmentID ASC")
    List<Assessments> getAllAssessments();
    @Query("SELECT * FROM ASSESSMENTS WHERE courseID= :courseID ORDER BY assessmentID ASC")
    List<Assessments> getAllAssociatedAssessments(int courseID);
}
