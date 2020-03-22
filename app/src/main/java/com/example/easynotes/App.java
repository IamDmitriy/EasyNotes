package com.example.easynotes;

import android.app.Application;

public class App extends Application {
    //TODO private static NoteRepository noteRepository;
    private static Keystore keystore;

    @Override
    public void onCreate() {
        super.onCreate();

        keystore = new SimpleKeystore(this);
    }

    public static Keystore getKeystore() {
        return keystore;
    }
}
