package com.example.graphics;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.Random;

public class GameField extends View {

    protected Bird bird;
    protected Enemy enemy;
    protected Context context;
    private int score = 0;
    private Random random = new Random();
    private GameTimer gameTimer;

    public GameField(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        SharedPreferences sp = context.getSharedPreferences("BIRD", Context.MODE_PRIVATE);
        int birdX = sp.getInt("BirdX", 500);
        int birdY = sp.getInt("BirdY", 800);

        bird = new Bird(birdX, birdY, getResources());
        enemy = null;
        gameTimer = new GameTimer(200);
        gameTimer.start();
    }

    public int getScore() {
        return score;
    }

    class GameTimer extends CountDownTimer {
        public GameTimer(long countDownInterval) {
            super(Long.MAX_VALUE, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (enemy == null) {
                enemy = new Enemy(1000, random.nextInt(800), getResources());
            }

            if (enemy.getCenterX() < -200) {
                enemy = new Enemy(1000, random.nextInt(800), getResources());
            }

            if (bird.intersect(enemy)) {
                enemy = new Enemy(1000, random.nextInt(800), getResources());
                score++;
            }

            bird.move();
            enemy.move();
            invalidate();
        }

        @Override
        public void onFinish() {
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bird.draw(canvas);
        if (enemy != null) {
            enemy.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        int width = getWidth();
        int height = getHeight();

        if (touchX < 0) touchX = 0;
        if (touchX > width - 400) touchX = width - 400;
        if (touchY < 0) touchY = 0;
        if (touchY > height - 400) touchY = height - 400;

        bird.updateTarget((int) touchX, (int) touchY);
        return true;
    }

    public void resume() {
        if (gameTimer != null) {
            gameTimer.start();
        }
    }

    public void pause() {
        SharedPreferences sp = context.getSharedPreferences("BIRD", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("BirdX", bird.getCenterX());
        editor.putInt("BirdY", bird.getCenterY());
        editor.apply();

        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }
}