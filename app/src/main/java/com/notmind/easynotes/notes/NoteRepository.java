package com.notmind.easynotes.notes;

import java.util.List;

public interface NoteRepository {
    List<Note> getNotes();

    int size();

    Note getNoteByPos(int position);

    long getNoteIdByPos(int position);

    Note getNoteById(long id);

    void saveNote(Note note);

    void setOnDataChangedListener(onDataChangedListener onDataChangedListener);

    void deleteById(long id);

    interface onDataChangedListener {
        void onDataChanged();
    }

}
