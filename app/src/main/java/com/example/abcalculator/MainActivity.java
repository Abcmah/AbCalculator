package com.example.abcalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;
    private String currentNumber = "";
    private String previousNumber = "";
    private String operator = null;
    private boolean isOperatorClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);

        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide,
                R.id.buttonEquals, R.id.buttonClear, R.id.buttonBackspace, R.id.buttonDot
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(v -> onButtonClick((Button) v));
        }
    }

    private void onButtonClick(Button button) {
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "C":
                clear();
                break;
            case "←":
                backspace();
                break;
            case "=":
                calculate();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                operatorClicked(buttonText);
                break;
            case ".":
                appendDot();
                break;
            default:
                numberClicked(buttonText);
                break;
        }
    }
    private void clear() {
        currentNumber = "";
        previousNumber = "";
        operator = null;
        isOperatorClicked = false;
        resultTextView.setText("0");
    }

    private void backspace() {
        if (!currentNumber.isEmpty()) {
            currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
            resultTextView.setText(currentNumber.isEmpty() ? "0" : currentNumber);
        }
    }

    private void calculate() {
        if (operator != null && !currentNumber.isEmpty()) {
            double result = 0;
            switch (operator) {
                case "+":
                    result = Double.parseDouble(previousNumber) + Double.parseDouble(currentNumber);
                    break;
                case "-":
                    result = Double.parseDouble(previousNumber) - Double.parseDouble(currentNumber);
                    break;
                case "*":
                    result = Double.parseDouble(previousNumber) * Double.parseDouble(currentNumber);
                    break;
                case "/":
                    result = Double.parseDouble(previousNumber) / Double.parseDouble(currentNumber);
                    break;
            }
            resultTextView.setText(String.valueOf(result));
            previousNumber = String.valueOf(result);
            currentNumber = "";
            operator = null;
            isOperatorClicked = false;
        }
    }
    private void operatorClicked(String op) {
        if (!currentNumber.isEmpty()) {
            if (operator != null) {
                calculate();
            }
            operator = op;
            previousNumber = currentNumber;
            currentNumber = "";
            isOperatorClicked = true;
        }
    }

    private void appendDot() {
        if (!currentNumber.contains(".")) {
            currentNumber += ".";
            resultTextView.setText(currentNumber);
        }
    }

    private void numberClicked(String number) {
        if (isOperatorClicked) {
            currentNumber = "";
            isOperatorClicked = false;
        }
        currentNumber += number;
        resultTextView.setText(currentNumber);
    }


}