package com.example.task3;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;
    private Bird bird;
    private Enemy enemy;
    private int score = 0;
    private Random random = new Random();
    private Context context;
    private int screenWidth, screenHeight;

    public GameView(Context context, int screenWidth, int screenHeight, int topPanelHeight) {
        super(context);
        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        getHolder().addCallback(this);
    }

    public int getScore() {
        return score;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        SharedPreferences sp = context.getSharedPreferences("BIRD", Context.MODE_PRIVATE);
        int birdX = sp.getInt("BirdX", screenWidth / 2);
        int birdY = sp.getInt("BirdY", screenHeight / 2);

        bird = new Bird(birdX, birdY, getResources());
        enemy = new Enemy(1000, random.nextInt(screenHeight - 200), getResources());

        drawThread = new DrawThread(getHolder(), bird, enemy);
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        SharedPreferences sp = context.getSharedPreferences("BIRD", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (bird != null) {
            editor.putInt("BirdX", bird.getCenterX());
            editor.putInt("BirdY", bird.getCenterY());
        }
        editor.apply();

        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        // БЕЗ ОГРАНИЧЕНИЙ
        bird.updateTarget((int) touchX, (int) touchY);
        return true;
    }

    class DrawThread extends Thread {
        private boolean runFlag = false;
        private SurfaceHolder surfaceHolder;
        private Bird bird;
        private Enemy enemy;
        private long prevTime;

        public DrawThread(SurfaceHolder surfaceHolder, Bird bird, Enemy enemy) {
            this.surfaceHolder = surfaceHolder;
            this.bird = bird;
            this.enemy = enemy;
            this.prevTime = System.currentTimeMillis();
        }

        public void setRunning(boolean run) {
            runFlag = run;
        }

        @Override
        public void run() {
            Canvas canvas;
            while (runFlag) {
                long now = System.currentTimeMillis();
                long elapsedTime = now - prevTime;

                if (elapsedTime > 30) {
                    prevTime = now;
                    updateGame();
                }

                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        drawGame(canvas);
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

        private void updateGame() {
            if (enemy == null) {
                enemy = new Enemy(1000, random.nextInt(screenHeight - 200), getResources());
            }

            if (enemy.getCenterX() < -200) {
                enemy = new Enemy(1000, random.nextInt(screenHeight - 200), getResources());
            }

            if (bird.intersect(enemy)) {
                enemy = new Enemy(1000, random.nextInt(screenHeight - 200), getResources());
                score++;
            }

            bird.move();
            enemy.move();
        }

        private void drawGame(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            bird.draw(canvas);
            if (enemy != null) {
                enemy.draw(canvas);
            }
        }
    }
}