package com.byteforce.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewInput;
    private StringBuilder input = new StringBuilder();
    private double firstValue = Double.NaN;
    private double secondValue = Double.NaN;
    private char operator = ' ';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewInput = findViewById(R.id.textViewInput);

        // Set click listeners for number buttons (0-9)
        for (int i = 0; i <= 9; i++) {
            int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName());
            Button button = findViewById(buttonId);
            final String buttonText = String.valueOf(i);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    input.append(buttonText);
                    updateInputText();
                }
            });
        }

        // Set click listeners for operator buttons (+, -, *, /)
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonClear = findViewById(R.id.buttonClear);

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOperatorClick('+');
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOperatorClick('-');
            }
        });

        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOperatorClick('*');
            }
        });

        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOperatorClick('/');
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInput();
            }
        });

        // Set click listener for "=" button
        Button buttonEquals = findViewById(R.id.buttonEquals);
        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
            }
        });
    }

    private void updateInputText() {
        textViewInput.setText(input.toString());
    }

    private void handleOperatorClick(char newOperator) {
        if (!Double.isNaN(firstValue)) {
            // If an operator is already set and the secondValue is not NaN, calculate the result
            if (!Double.isNaN(secondValue)) {
                calculateResult();
            }
            operator = newOperator;
            input.setLength(0); // Clear the input for the next number
        } else if (input.length() > 0) {
            firstValue = Double.parseDouble(input.toString());
            operator = newOperator;
            input.setLength(0); // Clear the input for the next number
        }
    }

    private void clearInput() {
        input.setLength(0);
        firstValue = Double.NaN;
        secondValue = Double.NaN;
        operator = ' ';
        textViewInput.setText("");
    }

    private void calculateResult() {
        if (!Double.isNaN(firstValue) && !Double.isNaN(secondValue) && operator != ' ') {
            double result = 0.0;
            double inputValue = Double.parseDouble(input.toString());

            switch (operator) {
                case '+':
                    result = firstValue + inputValue;
                    break;
                case '-':
                    result = firstValue - inputValue;
                    break;
                case '*':
                    result = firstValue * inputValue;
                    break;
                case '/':
                    if (inputValue != 0) {
                        result = firstValue / inputValue;
                    } else {
                        textViewInput.setText("Error");
                        return;
                    }
                    break;
            }

            input.setLength(0);
            input.append(result);
            textViewInput.setText(input.toString());
            firstValue = result;
            secondValue = Double.NaN;
            operator = ' ';
        }
    }

}

