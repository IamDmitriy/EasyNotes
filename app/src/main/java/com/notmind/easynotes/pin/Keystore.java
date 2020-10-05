package com.notmind.easynotes.pin;

public interface Keystore {
    boolean hasPin();

    boolean checkPin(String pin);

    boolean saveNewPin(String pin);
}
