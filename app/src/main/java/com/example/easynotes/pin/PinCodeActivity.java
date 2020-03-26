package com.example.easynotes.pin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easynotes.App;
import com.example.easynotes.R;
import com.example.easynotes.notes.NotesListActivity;

import java.util.ArrayList;

public class PinCodeActivity extends AppCompatActivity {
    private static final String LOG_TAG = "PinCodeActivityTag";

    private String curPin = "";
    private ArrayList<View> placeholders;
    private TextView errorOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        init();
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
            keystore.saveNewPin("0000"); //Для тестирования

            if (keystore.checkPin(curPin)) {
                Intent intent = new Intent(PinCodeActivity.this,
                        NotesListActivity.class);
                startActivity(intent);
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
