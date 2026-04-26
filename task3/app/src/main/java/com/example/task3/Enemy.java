package com.example.task3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Enemy extends Unit {
    private Bitmap bird;
    private Rect dist;

    public Enemy(int x, int y, Resources resources) {
        this.x = x;
        this.y = y;
        Bitmap originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.blacktwi);
        bird = Bitmap.createScaledBitmap(originalBitmap, 400, 400, false);
        dist = new Rect(x, y, x + 400, y + 400);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bird, null, dist, new Paint());
    }

    @Override
    public void move() {
        dist.left -= 10;
        dist.right -= 10;
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