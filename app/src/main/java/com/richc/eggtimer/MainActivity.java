package com.richc.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        timerButton = findViewById(R.id.goButton);

        timerSeekBar.setMax(timerMax);
        timerSeekBar.setProgress(initTimerValue);
        timerTextView.setText(String.format("%02d", initTimerValue/60)+":"+String.format("%02d", initTimerValue%60));

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                updateTimer(i);
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

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress()*1000+300, 1000){
                public void onTick(long millisUntilFinished) {

                    updateTimer((int)millisUntilFinished/1000);
                }

                public void onFinish() {
                    resetTimer();

                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();
                }
            }.start();
        }
    }

    public void resetTimer() {
        countDownTimer.cancel();

        timerSeekBar.setMax(timerMax);
        timerSeekBar.setProgress(initTimerValue);
        timerSeekBar.setEnabled(true);

        timerButton.setText("Start");
        timerActive = false;
    }

    public void updateTimer (int secUntilFinish) {
        int mm = secUntilFinish/60;
        int ss = secUntilFinish%60;

        timerTextView.setText(String.format("%02d", mm)+":"+String.format("%02d", ss));
    }
}
