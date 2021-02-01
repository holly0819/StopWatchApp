package com.example.stopwatchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Runnable {

    private static Boolean isActive = false;
    private Thread timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ラップボタンは灰色、リセットボタンは白色に
        Button resetButton = findViewById(R.id.resetButton);
        lightenButton(resetButton);
        Button rapButton = findViewById(R.id.rapButton);
        darkenButton(rapButton);

        timer = new Thread(this);
        timer.start();
    }

    public void startTimer(View view) {
        if(!isActive) {
            Toast toast = Toast.makeText(this, "Started Timer!", Toast.LENGTH_SHORT);
            toast.show();

            isActive = true;
            Button resetButton = findViewById(R.id.resetButton);
            darkenButton(resetButton);
            Button rapButton = findViewById(R.id.rapButton);
            lightenButton(rapButton);

            Button playButton = findViewById(R.id.playButton);
            playButton.setBackground(getResources().getDrawable(R.drawable.stop_button));
            playButton.setOnClickListener(this::stopTimer);
        }
    }

    public void stopTimer(View view) {
        if(isActive) {
            Toast toast = Toast.makeText(this, "Stopped Timer!", Toast.LENGTH_SHORT);
            toast.show();

            isActive = false;

            Button resetButton = findViewById(R.id.resetButton);
            lightenButton(resetButton);
            Button rapButton = findViewById(R.id.rapButton);
            darkenButton(rapButton);

            Button playButton = findViewById(R.id.playButton);
            playButton.setBackground(getResources().getDrawable(R.drawable.play_button));
            playButton.setOnClickListener(this::startTimer);
        }
    }

    public void resetTimer(View view) {
        if(!isActive) {
            Toast toast = Toast.makeText(this, "Reset Timer!", Toast.LENGTH_SHORT);
            toast.show();

            TextView timer = findViewById(R.id.timer);
            timer.setText("00:00.00");
        }
    }

    public void rapTimer(View view) {
        if(isActive) {
            Toast toast = Toast.makeText(this, "Recorded Time!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void run() {
        int time = 0;
        int hour;
        int minute;
        String minuteFormatted;
        int second;
        String secondFormatted;
        int milliSec;
        String milliSecFormatted;
        String formattedTimer;
        while(true) {
            if(isActive) {
                time += 1;

                hour = time / 360000;
                minute = (time % 360000) / 6000;
                minuteFormatted = minute < 10 ? "0" + minute : "" + minute;
                second = ((time % 360000) % 6000) / 100;
                secondFormatted = second < 10 ? "0" + second : "" + second;
                milliSec = ((time % 360000) % 6000) % 100;
                milliSecFormatted = milliSec < 10 ? "0" + milliSec : "" + milliSec;

                if(hour > 0) {
                    formattedTimer =
                            hour + ":" +
                            minuteFormatted + ":" +
                            secondFormatted + "." +
                            milliSecFormatted;
                } else {
                    formattedTimer =
                            minuteFormatted + ":" +
                            secondFormatted + "." +
                            milliSecFormatted;
                }

                TextView timer = findViewById(R.id.timer);
                timer.setText(formattedTimer);


                try {
                    Thread.sleep(10);
                } catch(InterruptedException e) {}
            }
        }
    }

    public void darkenButton(Button btn) {
        btn.setAlpha((float) 0.2);
    }
    public void lightenButton(Button btn) {
        btn.setAlpha(1);
    }
}