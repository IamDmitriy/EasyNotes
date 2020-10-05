package com.notmind.easynotes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private static final String KEY_PIN_IS_SETTED = "pinIsSetted";
    private static final String SHARED_PREF_NAME = "sharedPref";

    private boolean visibilityPin = false;
    private ImageView btnVisibility;
    private EditText edtEnterNewPin;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();
    }

    private void init() {
        sharedPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        edtEnterNewPin = findViewById(R.id.edtEnterNewPin);

        btnVisibility = findViewById(R.id.btnVisibility);
        btnVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visibilityPin = !visibilityPin;

                if (visibilityPin) {
                    btnVisibility.setImageDrawable(getDrawable(R.drawable.ic_visibility_off_black_24dp));
                    edtEnterNewPin.setTransformationMethod(null);

                } else {
                    btnVisibility.setImageDrawable(getDrawable(R.drawable.ic_visibility_black_24dp));
                    edtEnterNewPin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        Button btnChangePin = findViewById(R.id.btnChangePin);
        btnChangePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curPin = edtEnterNewPin.getText().toString();

                if (curPin.length() != 4) {
                    Toast.makeText(SettingsActivity.this,
                            getString(R.string.pin_code_must_contain_4_digits), Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                App.getKeystore().saveNewPin(curPin);


                sharedPref
                        .edit()
                        .putBoolean(KEY_PIN_IS_SETTED, true)
                        .apply();

                Toast.makeText(SettingsActivity.this,
                        getString(R.string.new_pin_successfully_saved), Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        Button btnDeletePin = findViewById(R.id.btnDeletePin);
        if (sharedPref.getBoolean(KEY_PIN_IS_SETTED, false)) {
            btnDeletePin.setVisibility(View.VISIBLE);
        }

        btnDeletePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref
                        .edit()
                        .putBoolean(KEY_PIN_IS_SETTED, false)
                        .apply();

                Toast.makeText(SettingsActivity.this, R.string.pin_code_successfully_deleted,
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        });


    }
}
