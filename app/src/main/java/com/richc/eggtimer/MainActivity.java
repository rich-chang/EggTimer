package com.richc.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button  timerButton;
    CountDownTimer countDownTimer;

    boolean timerActive = false;
    static int timerMax=600;        //sec
    static int initTimerValue=30;   //sec
    int timerValue;                 //sec

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        timerButton = findViewById(R.id.goButton);

        timerSeekBar.setMax(timerMax);
        timerSeekBar.setProgress(initTimerValue);
        timerTextView.setText(String.format("%02d", timerValue/60)+":"+String.format("%02d", timerValue%60));

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                int min=1;

                if (i < min) {
                    timerSeekBar.setProgress(timerValue);
                    timerValue = min;

                } else {
                    timerValue = i;
                }

                String mm = String.format("%02d", timerValue/60);
                String ss = String.format("%02d", timerValue%60);
                timerTextView.setText(mm+":"+ss);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    // Button.onClick
    public void controlTimer (View view) {

        if (timerActive) {
            // Active. Cancel and reset.
            resetTimer();

        } else {
            // Inactive. Start to countdown.

            timerSeekBar.setEnabled(false);
            timerButton.setText("Stop");
            timerActive = true;

            countDownTimer = new CountDownTimer(timerValue*1000, 1000){
                public void onTick(long millisUntilFinished) {

                    int secUntilFinished = (int)millisUntilFinished/1000;
                    int mm = secUntilFinished/60;
                    int ss = secUntilFinished%60;

                    timerTextView.setText(String.format("%02d", mm)+":"+String.format("%02d", ss));
                    //Log.i("Second left", String.valueOf(millisUntilFinished));
                }

                public void onFinish() {
                    resetTimer();
                }
            }.start();
        }
    }

    public void resetTimer() {
        countDownTimer.cancel();

        timerSeekBar.setMax(timerMax);
        timerSeekBar.setProgress(initTimerValue);
        timerSeekBar.setEnabled(true);

        timerTextView.setText(String.format("%02d", timerValue/60)+":"+String.format("%02d", timerValue%60));

        timerButton.setText("Start");
        timerActive = false;
    }
}
