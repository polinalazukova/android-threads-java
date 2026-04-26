package com.example.task3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Bird extends Unit {
    private Bitmap bird;
    private Rect dist;
    private int targetX, targetY;

    public Bird(int x, int y, Resources resources) {
        Bitmap originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.twitter);
        bird = Bitmap.createScaledBitmap(originalBitmap, 400, 400, false);
        dist = new Rect(x, y, x + 400, y + 400);
        this.targetX = x;
        this.targetY = y;
    }

    public void updateTarget(int touchX, int touchY) {
        this.targetX = touchX;
        this.targetY = touchY;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawBitmap(bird, null, dist, paint);
    }

    @Override
    public void move() {
        if (Math.abs(dist.centerX() - targetX) < 6) {
        } else if (dist.centerX() > targetX) {
            dist.left -= 10;
            dist.right -= 10;
        } else {
            dist.left += 10;
            dist.right += 10;
        }

        if (Math.abs(dist.centerY() - targetY) < 6) {
        } else if (dist.centerY() > targetY) {
            dist.top -= 10;
            dist.bottom -= 10;
        } else {
            dist.top += 10;
            dist.bottom += 10;
        }
    }

    @Override
    public int getCenterX() {
        return dist.centerX();
    }

    @Override
    public int getCenterY() {
        return dist.centerY();
    }

    @Override
    public int getRadius() {
        return bird.getHeight() / 2;
    }
}