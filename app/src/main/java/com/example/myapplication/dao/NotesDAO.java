package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.myapplication.entities.Notes;


import java.util.List;
@Dao
public interface NotesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Notes notes);
    @Update
    void update(Notes notes);
    @Delete
    void delete(Notes notes);
    @Query("SELECT * FROM NOTES ORDER BY notesID ASC")
    List<Notes> getAllNotes();

    @Query("SELECT * FROM NOTES WHERE courseID= :courseID ORDER BY notesID ASC")
    List<Notes> getAllAssociatedNotes(int courseID);
}
