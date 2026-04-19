package com.example.graphics;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private GameField gameField;
    private TextView tvScore;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameField = findViewById(R.id.gameField);
        tvScore = findViewById(R.id.tvScore);
        btnBack = findViewById(R.id.btnBack);

        new CountDownTimer(Long.MAX_VALUE, 200) {
            @Override
            public void onTick(long millisecondsUntilFinished) {
                if (gameField != null) {
                    tvScore.setText(String.valueOf(gameField.getScore()));
                }
            }
            @Override
            public void onFinish() {
            }
        }.start();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gameField != null) {
            gameField.onTouchEvent(event);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameField != null) {
            gameField.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameField != null) {
            gameField.pause();
        }
    }
}