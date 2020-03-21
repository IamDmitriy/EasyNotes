package com.example.easynotes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PinCodeActivity extends AppCompatActivity {
    private String curPinCode = "";
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

        Button curBtn = (Button) view;
        String curNumber = curBtn.getText().toString();
        curPinCode += curNumber;

        placeholders.get(curPinCode.length() - 1).setBackground(getDrawable(R.drawable.circle_filled));

        if (curPinCode.length() == 4) {
            showToast("Делаем проверку ПИН-кода: " + curPinCode);
            //Keystore keystore = App.getKeystore();
            //if (keystore.checkPinCode(curPinCode)) {
            if (false) {
                //startActivity
            } else {
                errorOutput = findViewById(R.id.errorOutput);
                errorOutput.setText(R.string.invalid_pin_code);
                curPinCode = "";
                for (int i = 0; i < placeholders.size(); i++) {
                    placeholders.get(i).setBackground(getDrawable(R.drawable.circle_empty));
                }
            }
        }

    }

    public void onClickBackspace(View view) {
        if (curPinCode.length() == 0) return;

        placeholders.get(curPinCode.length() - 1).setBackground(getDrawable(R.drawable.circle_empty));

        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < curPinCode.length() - 1; i++) {
            tmp.append(curPinCode.charAt(i));
        }
        curPinCode = tmp.toString();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
