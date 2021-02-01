package com.example.stopwatchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Runnable {

    private static Boolean isActive = false;
    private Thread timer;
    private Button playButton;
    private Button rapButton;
    private Button resetButton;
    private int time = 0;
    private int hour;
    private int minute;
    private String minuteFormatted;
    private int second;
    private String secondFormatted;
    private int milliSec;
    private String milliSecFormatted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        playButton = findViewById(R.id.playButton);
        rapButton = findViewById(R.id.rapButton);
        resetButton = findViewById(R.id.resetButton);

        // ラップボタンは灰色、リセットボタンは白色に
        lightenButton(resetButton);
        darkenButton(rapButton);

        timer = new Thread(this);
        timer.start();
    }

    public void startTimer(View view) {
        if(!isActive) {
            isActive = true;
            darkenButton(resetButton);
            lightenButton(rapButton);

            playButton.setBackground(getResources().getDrawable(R.drawable.stop_button));
            playButton.setOnClickListener(this::stopTimer);
        }
    }

    public void stopTimer(View view) {
        if(isActive) {
            isActive = false;

            lightenButton(resetButton);
            darkenButton(rapButton);

            playButton.setBackground(getResources().getDrawable(R.drawable.play_button));
            playButton.setOnClickListener(this::startTimer);
        }
    }

    public void resetTimer(View view) {
        if(!isActive) {
            TextView timer = findViewById(R.id.timer);
            timer.setText("00:00.00");
        }
    }

    public void rapTimer(View view) {
        if(isActive) {
            LinearLayout topContainer = findViewById(R.id.topContainer);
            ViewGroup.MarginLayoutParams mlp =  (ViewGroup.MarginLayoutParams) topContainer.getLayoutParams();
            mlp.setMargins(0,0,0,0);

            LinearLayout rapContainer = findViewById(R.id.rapContainer);
            TextView newRap = new TextView(this);
            newRap.setText(getTimer());
            newRap.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 20.0);
            rapContainer.addView(newRap, 0);
        }
    }

    @Override
    public void run() {

        while(true) {
            if(isActive) {
                time += 1;


                TextView timer = findViewById(R.id.timer);
                timer.setText(getTimer());

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
    public String getTimer() {
        hour = time / 360000;
        minute = (time % 360000) / 6000;
        minuteFormatted = minute < 10 ? "0" + minute : "" + minute;
        second = ((time % 360000) % 6000) / 100;
        secondFormatted = second < 10 ? "0" + second : "" + second;
        milliSec = ((time % 360000) % 6000) % 100;
        milliSecFormatted = milliSec < 10 ? "0" + milliSec : "" + milliSec;

        if(hour > 0) {
            return hour + ":" +
                   minuteFormatted + ":" +
                   secondFormatted + "." +
                   milliSecFormatted;
        } else {
            return minuteFormatted + ":" +
                   secondFormatted + "." +
                   milliSecFormatted;
        }
    }
}