package com.example.easynotes.pin;

import android.content.Context;
import android.content.SharedPreferences;


public class SimpleKeystore implements Keystore {
    private static final String SHARED_PREF_NAME = "sharedPref";
    private static final String PIN_CODE_KEY = "pinCodeKey";

    private SharedPreferences sharedPref;

    public SimpleKeystore(Context context) {
        sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean hasPin() {
        return (sharedPref.getString(PIN_CODE_KEY, null) != null);
    }

    @Override
    public boolean checkPin(String pin) {
        if (hasPin()) return sharedPref.getString(PIN_CODE_KEY, null).equals(pin);

        return false;
    }

    @Override
    public void saveNewPin(String pin) {
        sharedPref
                .edit()
                .putString(PIN_CODE_KEY, pin)
                .apply();
    }
}
