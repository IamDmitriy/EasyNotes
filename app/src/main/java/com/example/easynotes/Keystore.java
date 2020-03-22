package com.example.easynotes;

public interface Keystore {
    boolean hasPin();

    boolean checkPin(String pin);

    void saveNewPin(String pin);
}
