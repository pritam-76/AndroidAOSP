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
    EditText num1, num2;
    TextView result;
   //buttons
    Button add, sub, mul, div;

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

        // button click listeners

        //addition
        add.setOnClickListener(v -> calculate("+"));
        //subtraction
        sub.setOnClickListener(v -> calculate("-"));
        //multiplication
        mul.setOnClickListener(v -> calculate("*"));
        //Division
        div.setOnClickListener(v -> calculate("/"));

    }

    //methods main logic
    private void calculate(String operation) {
        //reading input
        String val1 = num1.getText().toString();
        String val2 = num2.getText().toString();

        Log.d("Calculator", "Input1: " + val1);
        Log.d("Calculator", "Input2: " + val2);
        //Validation

        if (val1.isEmpty() || val2.isEmpty()) {
            Toast.makeText(this, "Enter both number", Toast.LENGTH_SHORT).show();
            return;
        }
        double a = Double.parseDouble(val1);
        double b = Double.parseDouble(val2);
        double res = 0;

        // Operations

        switch (operation) {
            case "+":
                res = a + b;
                break;
            case "-":
                res = a - b;
                break;
            case "*":
                res = a * b;
                break;
            case "/":
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
