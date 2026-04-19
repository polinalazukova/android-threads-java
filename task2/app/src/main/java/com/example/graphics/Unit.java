package com.example.graphics;

import android.graphics.Canvas;

public abstract class Unit {
    protected int x, y;

    public abstract void draw(Canvas canvas);
    public abstract void move();

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract int getCenterX();
    public abstract int getCenterY();
    public abstract int getRadius();

    public boolean intersect(Unit unit) {
        int dx = Math.abs(this.getCenterX() - unit.getCenterX());
        int dy = Math.abs(this.getCenterY() - unit.getCenterY());
        int l = (int) Math.sqrt(dx * dx + dy * dy);
        return l < this.getRadius() + unit.getRadius();
    }
}