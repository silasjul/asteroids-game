package com.silas.asteroids.sprite;

public class Parallax extends Sprite {

    private double speed;
    private double offsetY = 0;
    private double offsetX = 0;

    public Parallax(String imagePath, int width, int height, double speed) {
        super(imagePath, width, height);
        this.speed = speed;
    }

    public double getSpeed() {return this.speed;}
    public double getOffsetY() {return this.offsetY;}
    public double getOffsetX() {return this.offsetX;}
    public void move() {
        this.offsetY += this.speed;

    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }
    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }
}
