package com.notmind.easynotes.pin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.notmind.easynotes.App;
import com.notmind.easynotes.R;
import com.notmind.easynotes.SettingsActivity;
import com.notmind.easynotes.notes.NotesListActivity;

import java.util.ArrayList;

public class PinCodeActivity extends AppCompatActivity {
    private static final String KEY_IS_FIRST_LAUNCH = "isFirstLaunch";
    private static final String KEY_PIN_IS_SETTED = "pinIsSetted";
    private static final String SHARED_PREF_NAME = "sharedPref";
    private static final String LOG_TAG = "PinCodeActivityTag";
    private String curPin = "";
    private ArrayList<View> placeholders;
    private TextView errorOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        boolean isFirstLaunch = sharedPref.getBoolean(KEY_IS_FIRST_LAUNCH, true);

        if (isFirstLaunch) {
            sharedPref
                    .edit()
                    .putBoolean(KEY_IS_FIRST_LAUNCH, false)
                    .apply();

            startActivity(new Intent(PinCodeActivity.this, NotesListActivity.class));
            startActivity(new Intent(PinCodeActivity.this, SettingsActivity.class)); //TODO в настройках организовать pinIsSetted

            Toast.makeText(PinCodeActivity.this,
                    R.string.welcome_message, Toast.LENGTH_LONG).show();

            finish();
            return;
        }

        boolean pinIsSetted = sharedPref.getBoolean(KEY_PIN_IS_SETTED, false);

        if (!pinIsSetted) {
            startActivity(new Intent(PinCodeActivity.this, NotesListActivity.class));
            finish();
        } else {
            init();
        }
    }

    private void init() {
        errorOutput = findViewById(R.id.errorOutput);

        placeholders = new ArrayList<>();
        placeholders.add(findViewById(R.id.placeholder1));
        placeholders.add(findViewById(R.id.placeholder2));
        placeholders.add(findViewById(R.id.placeholder3));
        placeholders.add(findViewById(R.id.placeholder4));
    }

    public void onClickNumber(View view) {
        if (!errorOutput.getText().toString().isEmpty()) {
            errorOutput.setText("");
        }

        if (curPin.length() == 4) return;

        Button curBtn = (Button) view;
        String curNumber = curBtn.getText().toString();
        curPin += curNumber;

        placeholders.get(curPin.length() - 1).setBackground(getDrawable(R.drawable.circle_filled));

        if (curPin.length() == 4) {
            Log.d(LOG_TAG, "Делаем проверку ПИН-кода: " + curPin);
            Keystore keystore = App.getKeystore();

            if (keystore.checkPin(curPin)) {
                Intent intent = new Intent(PinCodeActivity.this,
                        NotesListActivity.class);
                startActivity(intent);
                finish();
            } else {
                errorOutput = findViewById(R.id.errorOutput);
                errorOutput.setText(R.string.invalid_pin_code);
                curPin = "";
                for (int i = 0; i < placeholders.size(); i++) {
                    placeholders.get(i).setBackground(getDrawable(R.drawable.circle_empty));
                }
            }
        }

    }

    public void onClickBackspace(View view) {
        if (curPin.length() == 0) return;

        placeholders.get(curPin.length() - 1).setBackground(getDrawable(R.drawable.circle_empty));

        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < curPin.length() - 1; i++) {
            tmp.append(curPin.charAt(i));
        }
        curPin = tmp.toString();
    }

}
