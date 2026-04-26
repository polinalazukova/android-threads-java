package com.example.task3;

import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private TextView tvScore;
    private Handler scoreHandler = new Handler();
    private int topPanelHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        topPanelHeight = (int) (80 * getResources().getDisplayMetrics().density);
        gameView = new GameView(this, screenWidth, screenHeight, topPanelHeight);

        LinearLayout overlayLayout = new LinearLayout(this);
        overlayLayout.setOrientation(LinearLayout.VERTICAL);
        overlayLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout titlePanel = new LinearLayout(this);
        titlePanel.setOrientation(LinearLayout.VERTICAL);
        titlePanel.setGravity(Gravity.CENTER);
        titlePanel.setBackgroundColor(0xFFAAFFAA);
        titlePanel.setPadding(10, 40, 10, 5);

        TextView tvTitle = new TextView(this);
        tvTitle.setText("BIRDS");
        tvTitle.setTextSize(28);
        tvTitle.setTextColor(0xFF000000);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setPadding(0, 30, 0, 0);
        titlePanel.addView(tvTitle);

        Button btnBack = new Button(this);
        btnBack.setText("Back");
        btnBack.setTextSize(14);

        LinearLayout backContainer = new LinearLayout(this);
        backContainer.setOrientation(LinearLayout.HORIZONTAL);
        backContainer.setGravity(Gravity.START);
        backContainer.addView(btnBack);
        titlePanel.addView(backContainer);

        overlayLayout.addView(titlePanel);

        tvScore = new TextView(this);
        tvScore.setText("0");
        tvScore.setTextSize(32);
        tvScore.setTextColor(0xFF888888);
        tvScore.setGravity(Gravity.CENTER);
        tvScore.setPadding(0, 10, 0, 10);
        overlayLayout.addView(tvScore);

        View spacer = new View(this);
        spacer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));
        overlayLayout.addView(spacer);
        setContentView(gameView);
        addContentView(overlayLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        btnBack.setOnClickListener(v -> finish());
        startScoreUpdater();
    }
    private void startScoreUpdater() {
        scoreHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (gameView != null) {
                    tvScore.setText(String.valueOf(gameView.getScore()));
                }
                scoreHandler.postDelayed(this, 200);
            }
        }, 200);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scoreHandler.removeCallbacksAndMessages(null);
    }
}