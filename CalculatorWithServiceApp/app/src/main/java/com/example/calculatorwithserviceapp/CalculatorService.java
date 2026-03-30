//this defines the package(folder) your class belongs to
package com.example.calculatorwithserviceapp;
import android.app.Service;//Imports the service class(android component used for background or reusable logic
import android.content.Intent;//Used to pass information between components
import android.os.Binder;//used for communication between activity and service
import android.os.IBinder;//interface for binder(android uses this to connect components internally)
import java.text.DateFormat;
import java.util.Date;
import android.os.Handler;

public class CalculatorService extends Service {

    // ✅ Operation constants
    public static final int ADD = 1;
    public static final int SUB = 2;
    public static final int MUL = 3;
    public static final int DIV = 4;

    // ✅ Binder
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        public CalculatorService getService() {
            return CalculatorService.this;
        }
    }

    // Callback interface
    public interface TimeCallback {
        void onTimeUpdate(String time);
    }

    private TimeCallback callback;
    private final Handler handler = new Handler();

    public void setCallback(TimeCallback callback) {
        this.callback = callback;
        String currentTime = java.text.DateFormat.getTimeInstance().format(new java.util.Date());
        callback.onTimeUpdate(currentTime);
    }

    // Periodic task
    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            if (callback != null) {
                String currentTime = DateFormat.getTimeInstance().format(new Date());
                callback.onTimeUpdate(currentTime);
            }
            handler.postDelayed(this, 60000); // every 1 minute
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        handler.post(timeRunnable); // start timer
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        handler.removeCallbacks(timeRunnable); // stop timer
        return super.onUnbind(intent);
    }

    // ✅ Calculation logic
    public double calculate(double a, double b, int operation) {
        switch (operation) {
            case ADD:
                return a + b;

            case SUB:
                return a - b;

            case MUL:
                return a * b;

            case DIV:
                if (b == 0) {
                    throw new ArithmeticException("Division by zero is not allowed");
                }
                return a / b;

            default:
                throw new IllegalArgumentException("Invalid operation");
        }
    }
}