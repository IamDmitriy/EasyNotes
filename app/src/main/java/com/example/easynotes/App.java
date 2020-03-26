package com.example.easynotes;

import android.app.Application;

import com.example.easynotes.notes.MemoryNoteRepository;
import com.example.easynotes.notes.NoteRepository;
import com.example.easynotes.pin.Keystore;
import com.example.easynotes.pin.SimpleKeystore;

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
        noteRepository = new MemoryNoteRepository();
        keystore = new SimpleKeystore(this);
    }
}
