package com.example.stopwatchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private static Boolean isActive = false;
    private Button playButton, rapButton, resetButton;
    private LinearLayout rapContainer, topContainer;
    private TextView timerText;
    private int time = 0;
    private final int interval = 10;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:SS");

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time ++;
            timerText.setText(dateFormat.format(time * interval));
            handler.postDelayed(this, interval);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.playButton);
        rapButton = findViewById(R.id.rapButton);
        resetButton = findViewById(R.id.resetButton);
        rapContainer = findViewById(R.id.rapContainer);
        topContainer = findViewById(R.id.topContainer);
        timerText = findViewById(R.id.timer);

        // ラップボタンは灰色、リセットボタンは白色に
        lightenButton(resetButton);
        darkenButton(rapButton);
    }

    public void startTimer(View view) {
        if(!isActive) {
            isActive = true;
            handler.post(runnable);
            darkenButton(resetButton);
            lightenButton(rapButton);

            // backgroundとonclickを停止ボタンのものにする
            playButton.setBackground(getResources().getDrawable(R.drawable.stop_button));
            playButton.setOnClickListener(this::stopTimer);
        }
    }

    public void stopTimer(View view) {
        if(isActive) {
            isActive = false;
            handler.removeCallbacks(runnable);

            lightenButton(resetButton);
            darkenButton(rapButton);

            // backgroundとonclickをスタートボタンのものにする
            playButton.setBackground(getResources().getDrawable(R.drawable.play_button));
            playButton.setOnClickListener(this::startTimer);
        }
    }

    public void resetTimer(View view) {
        if(!isActive) {

            timerText.setText(dateFormat.format(0));
            // timeを初期化する
            time = 0;

            // TODO ここよくわからん
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) topContainer.getLayoutParams();

            // getResources()はcontextクラスのインスタンスメソッドだけど、Activityはcontextクラスのサブクラスなので、
            // thisを省略して記述できる
            // TODO R.dimen.~ と getDimension(R.dimen.~)は異なるらしい じゃあR.dimen~は何なの？
            // getDimension(R.dimen.~)で得られる値は dpiをpixelに変換した値です！！！！！！ 　それを画面のピクセル密度（？）で割るとdpiが算出

            // dpiをpxに変換して、マージンを元に戻す
            // applyDimension("変換前の単位", "変換前の数値", "画面の解像度")
            // floatで返されるので、intでキャストする
            int px = (int) getResources().getDimension(R.dimen.top_container_top_margin);
            mlp.setMargins(0, px,0,0);

            // 記録したラップを初期化する
            rapContainer.removeViews(0, rapContainer.getChildCount());

        }
    }

    public void rapTimer(View view) {
        if(isActive) {
            ViewGroup.MarginLayoutParams mlp =  (ViewGroup.MarginLayoutParams) topContainer.getLayoutParams();
            mlp.setMargins(0,0,0,0);

            TextView newRap = new TextView(this);
            newRap.setText(dateFormat.format(time * interval));
            newRap.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 30.0);
            newRap.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            rapContainer.addView(newRap, 0);
        }
    }

    public void darkenButton(Button btn) {
        btn.setAlpha((float) 0.2);
    }
    public void lightenButton(Button btn) {
        btn.setAlpha(1);
    }
}