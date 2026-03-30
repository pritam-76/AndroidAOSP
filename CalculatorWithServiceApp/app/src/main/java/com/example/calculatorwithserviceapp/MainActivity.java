package com.example.calculatorwithserviceapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements CalculatorService.TimeCallback {

    // UI
    private EditText num1, num2;
    private TextView result;
    private Button add, sub, mul, div;
    private TextView timeView;
    // Service
    private CalculatorService calculatorService;
    private boolean isBound = false;

    // ✅ Service connection
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CalculatorService.LocalBinder binder = (CalculatorService.LocalBinder) service;
            calculatorService = binder.getService();
            calculatorService.setCallback(MainActivity.this);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);

            // Bind service
            Intent intent = new Intent(this, CalculatorService.class);
            bindService(intent, connection, BIND_AUTO_CREATE);

            // UI mapping
            num1 = findViewById(R.id.num1);
            num2 = findViewById(R.id.num2);
            result = findViewById(R.id.result);
            timeView = findViewById(R.id.timeView);
            add = findViewById(R.id.add);
            sub = findViewById(R.id.sub);
            mul = findViewById(R.id.mul);
            div = findViewById(R.id.div);

            // Button actions (use service constants directly)
            add.setOnClickListener(v -> calculate(CalculatorService.ADD));
            sub.setOnClickListener(v -> calculate(CalculatorService.SUB));
            mul.setOnClickListener(v -> calculate(CalculatorService.MUL));
            div.setOnClickListener(v -> calculate(CalculatorService.DIV));
    }

    private void calculate(int operation) {

        if (!isBound) {
            Toast.makeText(this, "Service not connected", Toast.LENGTH_SHORT).show();
            return;
        }

        String val1 = num1.getText().toString();
        String val2 = num2.getText().toString();

        if (val1.isEmpty() || val2.isEmpty()) {
            Toast.makeText(this, "Enter both numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        double a = Double.parseDouble(val1);
        double b = Double.parseDouble(val2);

        try {
            double res = calculatorService.calculate(a, b, operation);
            result.setText("Result: " + res);
        } catch (ArithmeticException e) {
            result.setText(e.getMessage());
        }
    }

    // Receive time from service
    @Override
    public void onTimeUpdate(String time) {
        runOnUiThread(() -> timeView.setText("Time: " + time));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }
}