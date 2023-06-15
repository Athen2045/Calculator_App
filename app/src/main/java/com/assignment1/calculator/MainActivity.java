package com.assignment1.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView displayTextView;
    private TextView historyTextView;
    private ArrayList<String> expression;
    private boolean isAdvancedMode;
    private StringBuilder history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        displayTextView = findViewById(R.id.displayTextView);
        historyTextView = findViewById(R.id.historyTextView);

        // Set initial mode to Standard-No History
        isAdvancedMode = false;
        history = new StringBuilder();

        // Initialize expression list
        expression = new ArrayList<>();

        // Attach click listeners to buttons
        attachClickListeners();
    }

    private void attachClickListeners() {
        // Buttons for digits 0-9 and arithmetic operations
        int[] digitButtonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        };
        int[] operatorButtonIds = {
                R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide
        };

        for (int id : digitButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(this);
        }

        for (int id : operatorButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(this);
        }

        // Equal and Clear buttons
        Button btnEqual = findViewById(R.id.buttonEqual);
        btnEqual.setOnClickListener(this);

        Button btnClear = findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(this);

        // Mode change button
        Button btnMode = findViewById(R.id.buttonMode);
        btnMode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // Handle button clicks here
        if (id == R.id.buttonMode) {
            changeMode(); // Change between Standard and Advanced mode
        } else if (id == R.id.buttonEqual) {
            evaluateExpression(); // Evaluate the entered expression
        } else if (id == R.id.buttonClear) {
            clearDisplay(); // Clear the display
        } else {
            addToExpression(((Button) v).getText().toString()); // Append button value to expression
        }
    }

    private void changeMode() {
        isAdvancedMode = !isAdvancedMode; // Toggle between Standard and Advanced mode
        clearDisplay(); // Clear the display
        clearExpression(); // Clear the expression
        updateHistoryTextView(); // Update the history display
    }

    private void addToExpression(String value) {
        expression.add(value); // Add the button value to the expression
        updateDisplayTextView(); // Update the display with the new expression
    }

    private void evaluateExpression() {
        if (isValidExpression()) {
            float result = Calculation.evaluateExpression(expression); // Evaluate the expression
            displayTextView.setText(String.valueOf(result)); // Display the result

            if (isAdvancedMode) {
                String expressionStr = String.join("", expression);
                String historyEntry = expressionStr + " = " + result;
                history.append(historyEntry).append("\n");
                historyTextView.setText(history.toString()); // Update the history display with the new entry
            }

            clearExpression(); // Clear the expression for the next calculation
        } else {
            displayTextView.setText("Invalid Expression"); // Display error message for an invalid expression
        }
    }

    private boolean isValidExpression() {
        return Calculation.isValidExpression(expression); // Check if the expression is valid
    }

    private void clearDisplay() {
        displayTextView.setText(""); // Clear the display
    }

    private void clearExpression() {
        expression.clear(); // Clear the expression
    }

    private void updateDisplayTextView() {
        String displayText = String.join("", expression);
        displayTextView.setText(displayText); // Update the display with the current expression
    }

    private void updateHistoryTextView() {
        if (isAdvancedMode) {
            historyTextView.setVisibility(View.VISIBLE); // Show the history display
        } else {
            historyTextView.setVisibility(View.GONE); // Hide the history display
            historyTextView.setText(""); // Clear the history text
        }
    }
}
