package com.example.easynotes.pin;

import android.content.Context;
import android.content.SharedPreferences;


public class SimpleKeystore implements Keystore {
    private static final String SHARED_PREF_NAME = "sharedPref";
    private static final String KEY_PIN_CODE = "pinCode";

    private SharedPreferences sharedPref;

    public SimpleKeystore(Context context) {
        sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //TODO saveNewPin("0000"); //Для тестирования
    }

    @Override
    public boolean hasPin() {
        return (sharedPref.getString(KEY_PIN_CODE, null) != null);
    }

    @Override
    public boolean checkPin(String pin) {
        if (hasPin()) return sharedPref.getString(KEY_PIN_CODE, null).equals(pin);

        return false;
    }

    @Override
    public void saveNewPin(String pin) {
        sharedPref
                .edit()
                .putString(KEY_PIN_CODE, pin)
                .apply();
    }
}
