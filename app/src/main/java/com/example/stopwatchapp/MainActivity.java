package com.example.stopwatchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startTimer(View view) {
        Toast toast = Toast.makeText(this, "clicked!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void stopTimer(View view) {

    }

    public void resetTimer(View view) {

    }

    public void rapTimer(View view) {
        
    }
}