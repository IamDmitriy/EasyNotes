package com.example.easynotes.notes;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Note... notes);

    @Query("DELETE FROM note WHERE id LIKE :id")
    void deleteById(long id);

    @Query("SELECT * FROM note ORDER BY hasDeadline DESC, deadline, lastChanges DESC")
    Flowable<List<Note>> getAllNotes();

    @Query("SELECT * FROM note WHERE id LIKE :id")
    Single<Note> getNoteById(long id);

}
