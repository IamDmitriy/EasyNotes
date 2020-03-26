package com.example.easynotes.pin;

public interface Keystore {
    boolean hasPin();

    boolean checkPin(String pin);

    void saveNewPin(String pin);
}
