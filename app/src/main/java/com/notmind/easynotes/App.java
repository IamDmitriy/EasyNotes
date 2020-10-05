package com.notmind.easynotes;

import android.app.Application;

import com.notmind.easynotes.notes.DatabaseNoteRepository;
import com.notmind.easynotes.notes.NoteRepository;
import com.notmind.easynotes.pin.HashedKeystore;
import com.notmind.easynotes.pin.Keystore;

public class App extends Application {
    private static NoteRepository noteRepository;
    private static Keystore keystore;

    public static Keystore getKeystore() {
        return keystore;
    }

    public static NoteRepository getNoteRepository() {
        return noteRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        noteRepository = new DatabaseNoteRepository(getApplicationContext());
        keystore = new HashedKeystore(this);
    }
}
