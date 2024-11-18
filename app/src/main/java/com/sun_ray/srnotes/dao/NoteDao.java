package com.sun_ray.srnotes.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.sun_ray.srnotes.model.Note;
import java.util.List;

@Dao
public interface NoteDao {
    @Query("Select * From Notes Order By Date Desc")
    List<Note> getNote();

    @Insert
    void addNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM Notes")
    List<Note> getAllNotes();

}
