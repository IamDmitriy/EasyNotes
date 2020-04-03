package com.example.easynotes.pin;

import android.content.Context;
import android.content.SharedPreferences;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashedKeystore implements Keystore {
    private static final String SHARED_PREF_NAME = "sharedPref";
    private static final String KEY_HASH_PIN = "hashPin";
    private static final String KEY_SALT = "salt";

    private SharedPreferences sharedPref;

    public HashedKeystore(Context context) {
        sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean hasPin() {
        return (sharedPref.getString(KEY_HASH_PIN, null) != null);
    }

    @Override
    public boolean checkPin(String inputPin) {
        String hashPin = sharedPref.getString(KEY_HASH_PIN, null);
        if (hashPin == null) return false;

        String salt = sharedPref.getString(KEY_SALT, null);
        if (salt == null) return false;

        String inputPinSalt = inputPin + salt;

        String inputHash = toSHA1(inputPinSalt);

        if (inputHash == null) return false;

        return inputHash.equals(hashPin);
    }

    @Override
    public boolean saveNewPin(String pin) {
        String salt = generateRandomString();

        String pinSalt = pin + salt;

        String hashSaltPin = toSHA1(pinSalt);

        if (hashSaltPin != null) {
            sharedPref
                    .edit()
                    .putString(KEY_HASH_PIN, hashSaltPin)
                    .putString(KEY_SALT, salt)
                    .apply();
            return true;
        } else return false;

    }

    private String generateRandomString() {
        byte[] randomArray = new byte[20];

        new SecureRandom().nextBytes(randomArray);

        return new String(randomArray, Charset.forName("UTF-8"));
    }

    private String toSHA1(String input) {
        byte[] inputArray = input.getBytes(StandardCharsets.UTF_8);
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA-1");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        return new String(md.digest(inputArray), StandardCharsets.UTF_8);
    }

}
