package com.example.basiccalculatorapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
//This is the main screen(Activity)
public class MainActivity extends AppCompatActivity {
    //Declaring UI components
    //input fields
    private EditText num1, num2;
    private TextView result;
   //buttons
    private Button add, sub, mul, div;
    private static final String ADD = "+";
    private static final String SUB = "-";
    private static final String MUL = "*";
    private static final String DIV = "/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); //this connects XML UI TO Java Code

        //Connecting XML views to java//without this app will crash
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        result = findViewById(R.id.result);

        add = findViewById(R.id.add);
        sub = findViewById(R.id.sub);
        mul = findViewById(R.id.mul);
        div = findViewById(R.id.div);
        //addition
        add.setOnClickListener(v -> calculate(ADD));
        //subtraction
        sub.setOnClickListener(v -> calculate(SUB));
        //multiplication
        mul.setOnClickListener(v -> calculate(MUL));
        //Division
        div.setOnClickListener(v -> calculate(DIV));

    }

    //methods main logic
    private void calculate(String operation) {
        //reading input
        String valOfNum1 = num1.getText().toString();
        String valOfNum2 = num2.getText().toString();

        Log.d("Calculator", "Input1: " + valOfNum1);
        Log.d("Calculator", "Input2: " + valOfNum2);
        //Validation

        if (valOfNum1.isEmpty() || valOfNum2.isEmpty()) {
            Toast.makeText(this, "Enter both number", Toast.LENGTH_SHORT).show();
            return;
        }
        double a = Double.parseDouble(valOfNum1);
        double b = Double.parseDouble(valOfNum2);
        double res = 0;

        // Operations

        switch (operation) {
            case ADD:
                res = a + b;
                break;
            case SUB:
                res = a - b;
                break;
            case MUL:
                res = a * b;
                break;
            case DIV:
                if (b == 0) {
                    result.setText("Cannot divide by zero");
                    return;
                }
                res = a / b;
                break;
        }
        result.setText("Result: " + res);
    }
}
