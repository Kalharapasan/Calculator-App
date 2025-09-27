package com.example.a18calculater;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvDisplay;
    private String currentInput = "0";
    private String operator = "";
    private String firstOperand = "";
    private boolean isNewInput = true;
    private boolean isDecimalAdded = false;
    private double memoryValue = 0.0;
    private DecimalFormat df = new DecimalFormat("#.##########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDisplay = findViewById(R.id.tvDisplay);
        initializeButtons();
        updateDisplay();
    }

    private void initializeButtons() {
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnPlus).setOnClickListener(this);
        findViewById(R.id.btnMinus).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnEquals).setOnClickListener(this);
        findViewById(R.id.btnC).setOnClickListener(this);
        findViewById(R.id.btnCE).setOnClickListener(this);
        findViewById(R.id.btnDot).setOnClickListener(this);
        findViewById(R.id.btnPlusMinus).setOnClickListener(this);
        findViewById(R.id.btnPercent).setOnClickListener(this);
        findViewById(R.id.btnSquare).setOnClickListener(this);
        findViewById(R.id.btnSqrt).setOnClickListener(this);
        findViewById(R.id.btnOneOverX).setOnClickListener(this);
        findViewById(R.id.btnMC).setOnClickListener(this);
        findViewById(R.id.btnMR).setOnClickListener(this);
        findViewById(R.id.btnMPlus).setOnClickListener(this);
        findViewById(R.id.btnMMinus).setOnClickListener(this);
        findViewById(R.id.btnMS).setOnClickListener(this);
        findViewById(R.id.btnMTilde).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn0) numberPressed("0");
        else if (id == R.id.btn1) numberPressed("1");
        else if (id == R.id.btn2) numberPressed("2");
        else if (id == R.id.btn3) numberPressed("3");
        else if (id == R.id.btn4) numberPressed("4");
        else if (id == R.id.btn5) numberPressed("5");
        else if (id == R.id.btn6) numberPressed("6");
        else if (id == R.id.btn7) numberPressed("7");
        else if (id == R.id.btn8) numberPressed("8");
        else if (id == R.id.btn9) numberPressed("9");
        else if (id == R.id.btnDot) dotPressed();
        else if (id == R.id.btnPlus) operatorPressed("+");
        else if (id == R.id.btnMinus) operatorPressed("-");
        else if (id == R.id.btnMultiply) operatorPressed("*");
        else if (id == R.id.btnEquals) equalsPressed();
        else if (id == R.id.btnC) clearAll();
        else if (id == R.id.btnCE) clearEntry();
        else if (id == R.id.btnPlusMinus) plusMinusPressed();
        else if (id == R.id.btnPercent) percentPressed();
        else if (id == R.id.btnSquare) squarePressed();
        else if (id == R.id.btnSqrt) sqrtPressed();
        else if (id == R.id.btnOneOverX) oneOverXPressed();
        else if (id == R.id.btnMC) memoryClear();
        else if (id == R.id.btnMR) memoryRecall();
        else if (id == R.id.btnMPlus) memoryAdd();
        else if (id == R.id.btnMMinus) memorySubtract();
        else if (id == R.id.btnMS) memoryStore();
        else if (id == R.id.btnMTilde) memoryToggle();

        updateDisplay();
    }

    private void numberPressed(String number) {
        if (isNewInput || currentInput.equals("0")) {
            currentInput = number;
            isNewInput = false;
        } else {
            if (currentInput.length() < 15) {
                currentInput += number;
            }
        }
    }

    private void dotPressed() {
        if (isNewInput) {
            currentInput = "0.";
            isNewInput = false;
            isDecimalAdded = true;
        } else if (!isDecimalAdded) {
            currentInput += ".";
            isDecimalAdded = true;
        }
    }

    private void operatorPressed(String op) {
        if (!operator.isEmpty() && !isNewInput) {
            calculate();
        }
        firstOperand = currentInput;
        operator = op;
        isNewInput = true;
        isDecimalAdded = false;
    }

    private void equalsPressed() {
        if (!operator.isEmpty() && !isNewInput) {
            calculate();
            operator = "";
            firstOperand = "";
            isNewInput = true;
            isDecimalAdded = false;
        }
    }

    private void calculate() {
        try {
            double first = Double.parseDouble(firstOperand);
            double second = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+":
                    result = first + second;
                    break;
                case "-":
                    result = first - second;
                    break;
                case "*":
                    result = first * second;
                    break;
                case "/":
                    if (second != 0) {
                        result = first / second;
                    } else {
                        currentInput = "Error";
                        return;
                    }
                    break;
            }

            if (result == (long) result) {
                currentInput = String.valueOf((long) result);
            } else {
                currentInput = df.format(result);
            }
        } catch (Exception e) {
            currentInput = "Error";
        }
    }

    private void clearAll() {
        currentInput = "0";
        firstOperand = "";
        operator = "";
        isNewInput = true;
        isDecimalAdded = false;
    }

    private void clearEntry() {
        currentInput = "0";
        isNewInput = true;
        isDecimalAdded = false;
    }

    private void plusMinusPressed() {
        try {
            if (!currentInput.equals("0") && !currentInput.equals("Error")) {
                if (currentInput.startsWith("-")) {
                    currentInput = currentInput.substring(1);
                } else {
                    currentInput = "-" + currentInput;
                }
            }
        } catch (Exception e) {
            currentInput = "Error";
        }
    }

    private void percentPressed() {
        try {
            double value = Double.parseDouble(currentInput);
            value = value / 100;
            if (value == (long) value) {
                currentInput = String.valueOf((long) value);
            } else {
                currentInput = df.format(value);
            }
            isNewInput = true;
            isDecimalAdded = false;
        } catch (Exception e) {
            currentInput = "Error";
        }
    }

    private void squarePressed() {
        try {
            double value = Double.parseDouble(currentInput);
            double result = value * value;
            if (result == (long) result) {
                currentInput = String.valueOf((long) result);
            } else {
                currentInput = df.format(result);
            }
            isNewInput = true;
            isDecimalAdded = false;
        } catch (Exception e) {
            currentInput = "Error";
        }
    }

    private void sqrtPressed() {
        try {
            double value = Double.parseDouble(currentInput);
            if (value >= 0) {
                double result = Math.sqrt(value);
                if (result == (long) result) {
                    currentInput = String.valueOf((long) result);
                } else {
                    currentInput = df.format(result);
                }
            } else {
                currentInput = "Error";
            }
            isNewInput = true;
            isDecimalAdded = false;
        } catch (Exception e) {
            currentInput = "Error";
        }
    }

    private void oneOverXPressed() {
        try {
            double value = Double.parseDouble(currentInput);
            if (value != 0) {
                double result = 1 / value;
                if (result == (long) result) {
                    currentInput = String.valueOf((long) result);
                } else {
                    currentInput = df.format(result);
                }
            } else {
                currentInput = "Error";
            }
            isNewInput = true;
            isDecimalAdded = false;
        } catch (Exception e) {
            currentInput = "Error";
        }
    }
    private void memoryClear() {
        memoryValue = 0.0;
    }

    private void memoryRecall() {
        if (memoryValue == (long) memoryValue) {
            currentInput = String.valueOf((long) memoryValue);
        } else {
            currentInput = df.format(memoryValue);
        }
        isNewInput = true;
        isDecimalAdded = false;
    }

    private void memoryStore() {
        try {
            memoryValue = Double.parseDouble(currentInput);
        } catch (Exception e) {
        }
    }

    private void memoryAdd() {
        try {
            memoryValue += Double.parseDouble(currentInput);
        } catch (Exception e) {
        }
    }

    private void memorySubtract() {
        try {
            memoryValue -= Double.parseDouble(currentInput);
        } catch (Exception e) {
        }
    }

    private void memoryToggle() {
        memoryValue = -memoryValue;
        if (memoryValue == (long) memoryValue) {
            currentInput = String.valueOf((long) memoryValue);
        } else {
            currentInput = df.format(memoryValue);
        }
        isNewInput = true;
        isDecimalAdded = false;
    }

    private void updateDisplay() {
        String displayText = currentInput;
        if (displayText.length() > 12) {
            displayText = displayText.substring(0, 12);
        }
        tvDisplay.setText(displayText);
    }
}