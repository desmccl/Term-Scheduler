package com.example.myapplication.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.dao.AssessmentDAO;
import com.example.myapplication.dao.CourseDAO;
import com.example.myapplication.dao.NotesDAO;
import com.example.myapplication.dao.TermDAO;
import com.example.myapplication.entities.Assessments;
import com.example.myapplication.entities.Courses;
import com.example.myapplication.entities.Notes;
import com.example.myapplication.entities.Term;

@Database(entities = {Term.class, Courses.class, Assessments.class, Notes.class}, version=8, exportSchema = false)
public abstract class SchedulerDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract NotesDAO notesDAO();

    private static volatile SchedulerDatabaseBuilder INSTANCE;

    static SchedulerDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (SchedulerDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),SchedulerDatabaseBuilder.class, "MySchedulerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
