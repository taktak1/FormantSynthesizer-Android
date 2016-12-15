package com.formantsynthesizer.View;

/**
 * Created by cuong on 30/11/2016.
 */
public class Point {
    private float x;
    private float y;

    public Point(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
